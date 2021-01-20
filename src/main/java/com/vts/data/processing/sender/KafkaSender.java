package com.vts.data.processing.sender;

import com.vts.data.processing.domain.EmployeeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {

    public static final String EMPLOYEE = "EMPLOYEE";
    private final Logger log = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, EmployeeEntity> kafkaTemplate;

    public void sendEmployee(EmployeeEntity employee) {

        kafkaTemplate.send(EMPLOYEE, employee);

        log.info("sent data to kafka => {}", employee);
    }

    public void sendErrorAlert(EmployeeEntity employee, ValidationException e) {

        kafkaTemplate.send("ERROR_EMPLOYEE_PROCESS", employee);

        log.info("sent error message to kafka => {}", employee);
    }


}
