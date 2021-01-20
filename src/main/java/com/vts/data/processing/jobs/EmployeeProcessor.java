package com.vts.data.processing.jobs;

import com.vts.data.processing.domain.*;
import com.vts.data.processing.repository.EligibilityProcessErrorRepository;
import com.vts.data.processing.repository.EligibilityRepository;
import com.vts.data.processing.security.SecurityUtils;
import com.vts.data.processing.sender.DataProcessProducerService;
import com.vts.data.processing.sender.KafkaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.SpringValidator;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
//@Scope(value = "step")
public class EmployeeProcessor implements ItemProcessor<EmployeeRecord, EmployeeEntity> {

    private static final Logger logger =
        LoggerFactory.getLogger(EmployeeProcessor.class);

    @Autowired
    private DataProcessProducerService producerService;

    @Autowired
    private BeanValidationValidator validationValidator;

    @Override
    public EmployeeEntity process(EmployeeRecord employee) throws Exception {
        logger.info("process employee '{}'", employee);

        try {
            validationValidator.validate(employee);
        } catch (ValidationException e) {
            logger.warn("Skipping item because exception occurred in input validation at the {} th item. [message:{}]",
                employee.getCount(), e.getMessage());

            producerService.sendAlertError(AppError.builder()
                .code(AppErrorCode.VALIDATE.getValue())
                .errorMessage(e.getMessage())
                .title("employee-process-err")
                .build());

            return null;
        }

        EmployeeEntity rs = new EmployeeEntity();
        rs.setSourceId(employee.getSourceId());
        rs.setBirthDate( LocalDate.parse(employee.getBirthDate(), DateTimeFormatter.ofPattern("M/dd/yyyy")));
        rs.setCity(employee.getCity());
        rs.setEmailAddress(employee.getEmailAddress());
        rs.setFirstName(employee.getFirstName());
        rs.setLastName(employee.getLastName());
        rs.setMiddleInitial(employee.getMiddleInitial());
        rs.setPhoneNumber(employee.getPhoneNumber());
        rs.setSocialSecurityNumber(employee.getSsn());
        rs.setState(employee.getState());
        rs.setCity(employee.getCity());
        rs.setStreet(employee.getStreet());
        rs.setZipCode(employee.getZip());
        return rs;
    }

    @Bean
    public Validator validatorBean() {
        return new LocalValidatorFactoryBean();
    }
}
