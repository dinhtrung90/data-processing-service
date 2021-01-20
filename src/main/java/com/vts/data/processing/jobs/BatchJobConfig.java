package com.vts.data.processing.jobs;

import com.vts.data.processing.domain.EmployeeEntity;
import com.vts.data.processing.domain.EmployeeRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import javax.sql.DataSource;
import java.net.MalformedURLException;

@Configuration
@EnableBatchProcessing
//@EnableAutoConfiguration(exclude={BatchAutoConfiguration.class})
public class BatchJobConfig {

    public static final String PROCESS_EMPLOYEE_IMPORT = "process-employee-import";
    public static final String STEP_PROCESS_EMPLOYEE_FILE = "step-process-employee-file";
    @Autowired
    private JobBuilderFactory jobBuilder;

    @Autowired
    private StepBuilderFactory stepBuilder;

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job readCSVFile(EmployeeListener listener, Step step) {
        return jobBuilder
            .get(PROCESS_EMPLOYEE_IMPORT)
            .incrementer(new RunIdIncrementer())
            .validator(validator())
            .listener(listener)
            .start(step)
            .build();
    }

    @Bean
    public JobParametersValidator validator() {
        return jobParameters -> {
            String fileUrl = jobParameters.getString("path-file");
            if (StringUtils.isBlank(fileUrl)) {
                throw new JobParametersInvalidException("the path file is required.");
            }
        };
    }

    @Bean
    @JobScope
    public Step step(FlatFileItemReader<EmployeeRecord> reader) {
        return stepBuilder
            .get(STEP_PROCESS_EMPLOYEE_FILE)
            .<EmployeeRecord, EmployeeEntity>chunk(5)
            .reader(reader)
            .processor(processor())
            .writer(writer())
            .build();
    }

    @Bean
    public ItemProcessor<EmployeeRecord, EmployeeEntity> processor() {
        return new EmployeeProcessor();
    }

    // reading from csv file
    @Bean
    @StepScope
    public FlatFileItemReader<EmployeeRecord> reader(@Value("#{jobParameters['path-file']}") String pathFile) throws MalformedURLException {
        Resource resource = new UrlResource(pathFile);

        FlatFileItemReader<EmployeeRecord> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(resource);
        return itemReader;
    }

    //convert csv rows to beans
    @Bean
    public LineMapper<EmployeeRecord> lineMapper() {
        DefaultLineMapper<EmployeeRecord> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("source_id", "first_name", "middle_initial","last_name","email_address","phone_number","street","city","state","zip","birth_date","action","ssn");
        lineTokenizer.setIncludedFields(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        BeanWrapperFieldSetMapper<EmployeeRecord> fieldSetMapper = new BeanWrapperFieldSetMapper<EmployeeRecord>();
        fieldSetMapper.setTargetType(EmployeeRecord.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }


    @Bean
    public EmployeeKafkaSender writer(){
        return new EmployeeKafkaSender();
    }

}
