package com.qulix.yurkevichvv.trainingtask.wicket.validation;

import java.util.Map;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.validation.IValidationError;

import com.qulix.yurkevichvv.trainingtask.model.FieldsValidation;

/**
 * Валидатор полей на логичность последовательности даты начала и даты окончания.
 *
 * @author Q-YVV
 */
public class DateValidator extends AbstractFormValidator {

    /**
     * Поле даты начала.
     */
    private static final String BEGIN_DATE = "beginDate";

    /**
     * Поле даты окончания.
     */
    private static final String END_DATE = "endDate";

    /**
     * Массив компонентов формы.
     */
    private final FormComponent[] components;

    /**
     * Конструктор.
     *
     * @param beginDate поле даты начала
     * @param endDate поле даты окончания
     */
    public DateValidator(FormComponent beginDate, FormComponent endDate) {
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
        if (errorMessage.containsKey(BEGIN_DATE)) {
            beginDate.error((IValidationError) messageSource -> errorMessage.get(BEGIN_DATE));
        }
        if (errorMessage.containsKey(END_DATE)) {
            endDate.error((IValidationError) messageSource -> errorMessage.get(END_DATE));
        }
    }
}
