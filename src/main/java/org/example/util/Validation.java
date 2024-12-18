package org.example.util;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Set;
import java.util.stream.Collectors;


public class Validation<T> {
    public Set<String> valid(T entity) {
        try (ValidatorFactory factory = jakarta.validation.Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()) {
            Validator validator = factory.getValidator();
            return validator.validate(entity).stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        }

    }
}
