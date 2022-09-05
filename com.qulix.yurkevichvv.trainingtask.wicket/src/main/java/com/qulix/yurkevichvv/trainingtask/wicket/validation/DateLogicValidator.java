package com.qulix.yurkevichvv.trainingtask.wicket.validation;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.validation.IValidationError;

import com.qulix.yurkevichvv.trainingtask.api.validation.FieldsValidation;

import java.util.Map;

/**
 * Валидатор полей на логичность последовательности даты начала и даты окончания.
 *
 * @author Q-YVV
 */
public class DateLogicValidator extends AbstractFormValidator {

    private static final long serialVersionUID = 1L;

    private final FormComponent[] components;

    /**
     * Конструктор.
     *
     * @param beginDate поле даты начала
     * @param endDate поле даты окончания
     */
    public DateLogicValidator(FormComponent beginDate, FormComponent endDate) {
        components = new FormComponent[] {beginDate, endDate};
    }


    @Override
    public FormComponent[] getDependentFormComponents() {
        return components;
    }

    @Override
    public void validate(Form form) {

        final FormComponent beginDate = components[0];
        final FormComponent endDate = components[1];
        final Map<String, String> errorMessage = FieldsValidation.checkDate(beginDate.getInput(), endDate.getInput());
        if (!errorMessage.get("beginDate").isEmpty()) {
            beginDate.error((IValidationError) messageSource -> errorMessage.get("beginDate"));
        }
        if (!errorMessage.get("endDate").isEmpty()) {
            endDate.error((IValidationError) messageSource -> errorMessage.get("endDate"));
        }
    }
}
