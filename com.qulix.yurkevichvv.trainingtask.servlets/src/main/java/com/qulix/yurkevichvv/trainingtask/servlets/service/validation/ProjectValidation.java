package com.qulix.yurkevichvv.trainingtask.servlets.service.validation;

import com.qulix.yurkevichvv.trainingtask.model.FieldsValidation;

import java.util.HashMap;
import java.util.Map;

public class ProjectValidation implements ValidationService {

    /**
     * Обозначение название проекта.
     */
    private static final String TITLE_OF_PROJECT = "titleProject";

    /**
     * Обозначение описание проекта.
     */
    private static final String DESCRIPTION = "description";

    /**
     * Хранит константу длинны короткой строки.
     */
    private static final int SHORT_LENGTH = 50;

    /**
     * Хранит константу длинны короткой строки.
     */
    private static final int LONG_LENGTH = 250;


    @Override
    public Map<String, String> validate(Map<String, String> paramsMap) {
        Map<String, String> errorList = new HashMap<>(paramsMap.size());
        errorList.put(TITLE_OF_PROJECT, FieldsValidation.checkString(paramsMap.get(TITLE_OF_PROJECT), SHORT_LENGTH));
        errorList.put(DESCRIPTION, FieldsValidation.checkString(paramsMap.get(DESCRIPTION), LONG_LENGTH));
        return errorList;
    }
}
