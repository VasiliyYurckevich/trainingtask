package com.qulix.yurkevichvv.trainingtask.servlets.service.validation;

import com.qulix.yurkevichvv.trainingtask.model.FieldsValidation;

import java.util.HashMap;
import java.util.Map;

public class EmployeeValidation implements ValidationService {

    /**
     * Обозначение фамилии сотрудника.
     */
    private static final String SURNAME = "surname";

    /**
     * Обозначение имени сотрудника.
     */
    private static final String FIRST_NAME = "firstName";

    /**
     * Обозначение отчества сотрудника.
     */
    private static final String PATRONYMIC = "patronymic";

    /**
     * Обозначение должности сотрудника.
     */
    private static final String POST = "post";

    /**
     * Хранит константу длинны короткой строки.
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
