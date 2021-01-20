package com.vts.data.processing.jobs;

import com.vts.data.processing.domain.EmployeeRecord;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

public class EmployeeValidator implements Validator<EmployeeRecord> {

    @Override
    public void validate(EmployeeRecord employeeRecord) throws ValidationException {

    }
}
