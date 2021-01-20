package com.vts.data.processing.jobs;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class BeanValidationValidator<T> implements Validator<T> {

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private javax.validation.Validator validator = factory.getValidator();

    public void validate(T value) throws ValidationException {
        Set<ConstraintViolation<T>> violations = validator.validate(value);
        if(!violations.isEmpty()) {
            throw new ValidationException("Validation failed for " + value + ": " + violationsToString(violations));
        }
    }

    private String violationsToString(Set<ConstraintViolation<T>> violations) {
        String glue = ", ";
        StringBuilder builder = new StringBuilder();
        for(ConstraintViolation<T> violation : violations) {
            builder.append(violation.getPropertyPath())
                .append(" ")
                .append(violation.getMessage())
                .append(glue);
        }
        return StringUtils.removeEnd(builder.toString(), glue);
    }

}
