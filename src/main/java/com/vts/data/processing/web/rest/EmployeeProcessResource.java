package com.vts.data.processing.web.rest;

import com.vts.data.processing.config.Constants;
import com.vts.data.processing.domain.Eligibility;
import com.vts.data.processing.repository.EligibilityRepository;
import com.vts.data.processing.security.SecurityUtils;
import com.vts.data.processing.service.FirebaseService;
import com.vts.data.processing.service.dto.TResult;
import com.vts.data.processing.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class EmployeeProcessResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeProcessResource.class);
    private final JobLauncher jobLauncher;
    private final Job job;

    private final FirebaseService firebaseService;

    private final EligibilityRepository eligibilityRepository;

    public EmployeeProcessResource(JobLauncher jobLauncher, Job job, FirebaseService firebaseService, EligibilityRepository eligibilityRepository) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.firebaseService = firebaseService;
        this.eligibilityRepository = eligibilityRepository;
    }

    /**
     * POST /job/:fileName : post to execute a job using the file name given
     *
     * @param fileName
     *            the fileName of the job file to run
     * @return the ResponseEntity with status 200 (OK) or status 500 (Job Failure)
     */
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<String> processEmployeeCsv(@PathVariable String fileName) {
        Map<String, JobParameter> parameterMap = new HashMap<>();
        parameterMap.put(Constants.JOB_PARAM_FILE_NAME, new JobParameter(fileName));
        try {
            jobLauncher.run(job, new JobParameters(parameterMap));
        } catch (Exception e) {
            return new ResponseEntity<String>("Failure: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value="employees/import-file/upload", method= RequestMethod.POST)
    public ResponseEntity uploadEmployeeCsv(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        // store file to s3
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().orElseGet(() -> "anonymous");
        String name = firebaseService.save(multipartFile);

        if (name == null|| StringUtils.isEmpty(name)) {
            throw  new BadRequestAlertException("Error upload file", "FileName", multipartFile.getName());
        }

        String url = firebaseService.downloadFile(name);

        String extensionRemoved = name.split("\\.")[0];

        // store metadata db
        Eligibility eligibility =  new Eligibility()
            .createdBy(currentUserLogin)
            .createdDate(Instant.now())
            .fileName(name)
            .fileUrl(url)
            .lastModifiedBy(currentUserLogin)
            .lastModifiedDate(Instant.now())
            .refId(extensionRemoved);
        eligibilityRepository.save(eligibility);

        //process import
        JobParameters params = new JobParametersBuilder()
            .addString("JobID", String.valueOf(System.currentTimeMillis()))
            .addString("path-file", url)
            .addString(Constants.FILE_REF_ID, eligibility.getRefId())
            .toJobParameters();
        try {
            jobLauncher.run(job, params);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.debug("error process employees: {}", e.getMessage());
        }


        // create response
        TResult result = TResult.builder()
            .result("Uploading")
            .isSuccess(true)
            .build();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
