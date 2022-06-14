package com.qulix.yurkevichvv.trainingtask.main.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис для валидации данных на соответственных страницах.
 *
 * @author Q-YVV
 * @see FieldsValidation
 */
public class ValidationService {

    private static final int SHORT_LENGTH = 50;

    private static final int LONG_LENGTH = 250;

    /**
     * Валидация для вводимых данных о сотруднике.
     *
     * @param paramsList Список параметров для валидации.
     * @return Список ошибок.
     */
    public static Map<String,String> checkingEmployeeData(Map<String,String> paramsList) {
        Map<String,String> errorList = new HashMap<>(paramsList.size());
        errorList.put("surname", FieldsValidation.stringValidityCheck(paramsList.get("surname"), SHORT_LENGTH));
        errorList.put("firstName", FieldsValidation.stringValidityCheck(paramsList.get("firstName"), SHORT_LENGTH));
        errorList.put("patronymic", FieldsValidation.stringValidityCheck(paramsList.get("patronymic"), SHORT_LENGTH));
        errorList.put("post", FieldsValidation.stringValidityCheck(paramsList.get("post"), SHORT_LENGTH));
        return errorList;
    }

    /**
     *Валидация для вводимых данных о проекте.
     *
     * @param paramsList Список параметров для валидации.
     * @return Список ошибок.
     */
    public static Map<String,String> checkingProjectData(Map<String,String> paramsList) {
        Map<String,String> errorList = new HashMap<>(paramsList.size());
        errorList.put("titleProject", FieldsValidation.stringValidityCheck(paramsList.get("titleProject"), SHORT_LENGTH));
        errorList.put("description", FieldsValidation.stringValidityCheck(paramsList.get("description"), LONG_LENGTH));
        return errorList;
    }

    /**
     * Валидация для вводимых данных о задаче.
     *
     * @param paramsList Список параметров для валидации.
     * @return Список ошибок.
     */
    public static Map<String,String> checkingTaskData(Map<String,String> paramsList) {
        Map<String,String> errorList = new HashMap<>();
        errorList.put("status", FieldsValidation.stringValidityCheck(paramsList.get("status"), SHORT_LENGTH));
        errorList.put("title", FieldsValidation.stringValidityCheck(paramsList.get("title"), SHORT_LENGTH));
        errorList.put("workTime", FieldsValidation.numberValidityCheck(paramsList.get("workTime")));
        errorList.putAll(FieldsValidation.dateValidityCheck(paramsList.get("beginDate"),
                paramsList.get("endDate")));
        return errorList;
    }

}
