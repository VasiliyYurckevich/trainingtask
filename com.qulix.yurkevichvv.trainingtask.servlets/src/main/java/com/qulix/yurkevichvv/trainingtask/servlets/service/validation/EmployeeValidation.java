package com.qulix.yurkevichvv.trainingtask.servlets.service.validation;

import java.util.HashMap;
import java.util.Map;

import com.qulix.yurkevichvv.trainingtask.model.FieldsValidation;
import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;

/**
 * Валидация данных {@link Employee}.
 *
 * @author Q-YVV
 */
public class EmployeeValidation implements ValidationService {

    /**
     * Фамилия {@link Employee}.
     */
    private static final String SURNAME = "surname";

    /**
     * Имя {@link Employee}.
     */
    private static final String FIRST_NAME = "firstName";

    /**
     * Отчество {@link Employee}.
     */
    private static final String PATRONYMIC = "patronymic";

    /**
     * Должность {@link Employee}.
     */
    private static final String POST = "post";

    /**
     * Длинна короткой строки.
     */
    private static final int SHORT_LENGTH = 50;

    @Override
    public Map<String, String> validate(Map<String, String> paramsMap) {
        Map<String, String> errorList = new HashMap<>(paramsMap.size());
        errorList.put(SURNAME, FieldsValidation.checkString(paramsMap.get(SURNAME), SHORT_LENGTH));
        errorList.put(FIRST_NAME, FieldsValidation.checkString(paramsMap.get(FIRST_NAME), SHORT_LENGTH));
        errorList.put(PATRONYMIC, FieldsValidation.checkString(paramsMap.get(PATRONYMIC), SHORT_LENGTH));
        errorList.put(POST, FieldsValidation.checkString(paramsMap.get(POST), SHORT_LENGTH));
        return errorList;
    }
}
