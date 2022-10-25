package com.qulix.yurkevichvv.trainingtask.wicket.validation;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import com.qulix.yurkevichvv.trainingtask.model.FieldsValidation;

/**
 * Валидатор текстового ввода.
 *
 * @author Q-YVV
 */
public class CustomStringValidator implements IValidator<String> {

    /**
     * Максимальная длинна вводимой строки.
     */
    private final Integer maxlength;

    /**
     * Конструктор.
     *
     * @param maxlength максимальная длинна вводимой строки
     */
    public CustomStringValidator(Integer maxlength) {
        this.maxlength = maxlength;
    }

    @Override
    public void validate(IValidatable<String> validatable) {
        final String string = validatable.getValue();
        final String errorMessage = FieldsValidation.checkString(string, maxlength);
        if (errorMessage != null) {
            newError(validatable, errorMessage);
        }
    }

    /**
     * Создает новую ошибку.
     *
     * @param validatable объект, который проверяется
     * @param errorMessage сообщение ошибки
     */
    private void newError(IValidatable<String> validatable, String errorMessage) {
        ValidationError error = new ValidationError();
        error.addKey(getClass().getSimpleName() + "." + "key").setMessage(errorMessage);
        validatable.error(error);
    }
}
