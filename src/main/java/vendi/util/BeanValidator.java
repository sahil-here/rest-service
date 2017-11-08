package vendi.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import vendi.exception.VendiException;

public class BeanValidator {
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = factory.getValidator();

    public static void validate(Object object) throws VendiException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if(constraintViolations.size() !=0 ) {
            String errorMessage = "";
            for (ConstraintViolation<Object> constraintViolation : constraintViolations)
                errorMessage += constraintViolation.getPropertyPath() +" "+ constraintViolation.getMessage() + " & ";
            errorMessage = errorMessage.substring(0, errorMessage.length() - 3);
            throw new VendiException(400,errorMessage);
        }
    }
}
