package com.fredsonchaves.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        if (number != null) {
            var valorDecimal = BigDecimal.valueOf(number.doubleValue());
            var multiploDecimal = BigDecimal.valueOf(numeroMultiplo);
            var resto = valorDecimal.remainder(multiploDecimal);
            valid = BigDecimal.ZERO.compareTo(resto) == 0;
        }
        return valid;
    }
}
