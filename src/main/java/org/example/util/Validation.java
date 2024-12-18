package org.example.util;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Set;


public class Validation<T> {
    public Set<ConstraintViolation<T>> valid(T entity) {
        try (ValidatorFactory factory = jakarta.validation.Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()) {
            Validator validator = factory.getValidator();
            return validator.validate(entity);
        }

    }
}
