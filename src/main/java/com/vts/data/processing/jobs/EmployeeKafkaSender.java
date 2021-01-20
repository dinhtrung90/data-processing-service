package com.vts.data.processing.jobs;

import com.vts.data.processing.domain.EmployeeEntity;
import com.vts.data.processing.sender.DataProcessProducerService;
import com.vts.data.processing.sender.KafkaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeKafkaSender implements ItemWriter<EmployeeEntity> {

    private final Logger log = LoggerFactory.getLogger(EmployeeKafkaSender.class);

    @Autowired
    private DataProcessProducerService kafkaSender;

    @Override
    public void write(List<? extends EmployeeEntity> employees) throws Exception {

        for (EmployeeEntity employee : employees) {
            kafkaSender.sendEmployee(employee);
            log.debug("Message sent to kafka.");
        }

    }
}
