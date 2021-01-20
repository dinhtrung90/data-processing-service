package com.vts.data.processing.jobs;

import com.vts.data.processing.config.Constants;
import com.vts.data.processing.domain.Eligibility;
import com.vts.data.processing.domain.EmployeeEntity;
import com.vts.data.processing.repository.EligibilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeListener implements JobExecutionListener {

    private final Logger log = LoggerFactory.getLogger(EmployeeListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private EligibilityRepository eligibilityRepository;

    public EmployeeListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("!!! JOB START! Time to process employee");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            // saving metadata jobs
//            JobParameters jobParameters = jobExecution.getJobParameters();
//            String refId = jobParameters.getString(Constants.FILE_REF_ID);
//            if (!refId.isEmpty()) {
//
//                Optional<Eligibility> eligibilityOptional = eligibilityRepository.findByRefId(refId);
//                if (eligibilityOptional.isPresent()) {
//
//                }
//            }
        }
    }
}
