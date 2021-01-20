package com.vts.data.processing.config;

import com.vts.data.processing.domain.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfiguration {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, EmployeeEntity> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.getProducerProps());
    }

    @Bean
    public KafkaTemplate<String, EmployeeEntity> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
