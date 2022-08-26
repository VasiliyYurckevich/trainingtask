package com.qulix.yurkevichvv.trainingtask.wicket.validation;

import com.qulix.yurkevichvv.trainingtask.servlets.validation.FieldsValidation;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class CustomStringValidator implements IValidator<String> {

    private Integer maxlength;

    public CustomStringValidator(Integer maxlength) {
        this.maxlength = maxlength;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        final String string = validatable.getValue();
        final String errorMessage = FieldsValidation.checkString(string, maxlength);
        if (errorMessage != null) {
            error(validatable, "", errorMessage);
        }

    }

    private void error(IValidatable<String> validatable, String errorKey,String errorMessage) {
        ValidationError error = new ValidationError();
        error.addKey(getClass().getSimpleName() + "." + errorKey).setMessage(errorMessage);
        validatable.error(error);
    }
}
