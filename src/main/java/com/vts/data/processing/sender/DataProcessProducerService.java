package com.vts.data.processing.sender;

import com.vts.data.processing.config.KafkaProperties;
import com.vts.data.processing.domain.AppError;
import com.vts.data.processing.domain.EligibilityProcessError;
import com.vts.data.processing.domain.EmployeeEntity;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class DataProcessProducerService {

    private final Logger log = LoggerFactory.getLogger(DataProcessProducerService.class);
    private static final String TOPIC = "employee_data_processing_group";
    private static final String EMPLOYEE_TOPIC_ERROR = "employee_data_processing_group_failure";
    private final KafkaProperties kafkaProperties;
    private KafkaProducer<String, EmployeeEntity> producer;
    private KafkaProducer<String, AppError> producerError;

    public DataProcessProducerService(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @PostConstruct
    public void initialize(){
        log.info("Kafka producer initializing...");
        this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        this.producerError = new KafkaProducer<>(kafkaProperties.getProducerProps());

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }

    public void sendEmployee(EmployeeEntity employee) {
        ProducerRecord<String, EmployeeEntity> employeeRecord = new ProducerRecord<>(TOPIC, employee);
        producer.send(employeeRecord);
    }

    public void sendAlertError(AppError e) {
        ProducerRecord<String, AppError> errorRecord = new ProducerRecord<>(EMPLOYEE_TOPIC_ERROR, e);
        producerError.send(errorRecord);
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutdown Kafka producer");
        producer.close();
    }


}
