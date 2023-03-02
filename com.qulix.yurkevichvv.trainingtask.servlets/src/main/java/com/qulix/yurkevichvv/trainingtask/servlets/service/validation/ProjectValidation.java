package com.qulix.yurkevichvv.trainingtask.servlets.service.validation;

import java.util.HashMap;
import java.util.Map;

import com.qulix.yurkevichvv.trainingtask.model.FieldValidation;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;

/**
 * Валидация данных {@link Project}.
 *
 * @author Q-YVV
 */
public final class ProjectValidation implements ValidationService {

    /**
     * Обозначение название проекта.
     */
    private static final String TITLE_OF_PROJECT = "titleProject";

    /**
     * Обозначение описание проекта.
     */
    private static final String DESCRIPTION = "description";

    /**
     * Длинна короткой строки.
     */
    private static final int SHORT_LENGTH = 50;

    /**
     * Длинна короткой строки.
     */
    private static final int LONG_LENGTH = 250;

    @Override
    public Map<String, String> validate(Map<String, String> paramsMap) {
        Map<String, String> errorList = new HashMap<>(paramsMap.size());
        errorList.put(TITLE_OF_PROJECT, FieldValidation.checkString(paramsMap.get(TITLE_OF_PROJECT), SHORT_LENGTH));
        errorList.put(DESCRIPTION, FieldValidation.checkString(paramsMap.get(DESCRIPTION), LONG_LENGTH));
        return errorList;
    }
}
