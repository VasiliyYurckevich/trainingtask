package com.qulix.yurkevichvv.trainingtask.servlets.service.validation;

import com.qulix.yurkevichvv.trainingtask.model.FieldsValidation;

import java.util.HashMap;
import java.util.Map;

public class TaskValidation implements ValidationService {

    /**
     * Обозначение статуса задачи.
     */
    private static final String STATUS = "status";

    /**
     * Обозначение названия задачи.
     */
    private static final String TITLE = "title";

    /**
     * Обозначение времени на выполнение задачи.
     */
    private static final String WORK_TIME = "workTime";

    /**
     * Хранит константу длинны короткой строки.
     */
    private static final int SHORT_LENGTH = 50;

    /**
     * Обозначение даты начала выполнения задачи.
     */
    private static final String BEGIN_DATE = "beginDate";

    /**
     * Обозначение даты окончания выполнения задачи.
     */
    private static final String END_DATE = "endDate";

    @Override
    public Map<String, String> validate(Map<String, String> paramsMap) {

        Map<String, String> errorList = new HashMap<>(paramsMap.size());

        errorList.put(STATUS, FieldsValidation.checkString(paramsMap.get(STATUS), SHORT_LENGTH));
        errorList.put(TITLE, FieldsValidation.checkString(paramsMap.get(TITLE), SHORT_LENGTH));
        errorList.put(WORK_TIME, FieldsValidation.checkNumber(paramsMap.get(WORK_TIME)));
        errorList.putAll(FieldsValidation.checkDate(paramsMap.get(BEGIN_DATE), paramsMap.get(END_DATE)));
        return errorList;
    }
}
