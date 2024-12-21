package app.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {
    private static final String PATTERN = "[A-Z0-9._%+-]+@+[A-Z0-9.-]+\\\\.[A-Z]{2,6}";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email.matches(PATTERN);
    }
}
