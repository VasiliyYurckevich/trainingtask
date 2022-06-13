package com.qulix.yurkevichvv.trainingtask.main.validation;

import java.util.ArrayList;
import java.util.List;

import com.qulix.yurkevichvv.trainingtask.main.utils.Nums;

/**
 * Сервис для валидации данных на соотвественных страницах.
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
    public static List<String> checkingEmployeeData(List<String> paramsList) {
        List<String> errorList = new ArrayList<>(Nums.FOUR.getValue());
        errorList.add(FieldsValidation.stringValidityCheck(paramsList.get(Nums.ZERO.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.stringValidityCheck(paramsList.get(Nums.ONE.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.stringValidityCheck(paramsList.get(Nums.TWO.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.stringValidityCheck(paramsList.get(Nums.THREE.getValue()), SHORT_LENGTH));
        return errorList;
    }

    /**
     *Валидация для вводимых данных о проекте.
     *
     * @param paramsList Список параметров для валидации.
     * @return Список ошибок.
     */
    public static List<String> checkingProjectData(List<String> paramsList) {
        List<String> errorList = new ArrayList<>(Nums.TWO.getValue());
        errorList.add(FieldsValidation.stringValidityCheck(paramsList.get(Nums.ZERO.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.stringValidityCheck(paramsList.get(Nums.ONE.getValue()), LONG_LENGTH));
        return errorList;
    }

    /**
     * Валидация для вводимых данных о задаче.
     *
     * @param paramsList Список параметров для валидации.
     * @return Список ошибок.
     */
    public static List<String> checkingTaskData(List<String> paramsList) {
        List<String> errorList = new ArrayList<>(Nums.FIVE.getValue());
        errorList.add(FieldsValidation.stringValidityCheck(paramsList.get(Nums.ZERO.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.stringValidityCheck(paramsList.get(Nums.ONE.getValue()), SHORT_LENGTH));
        errorList.add(FieldsValidation.numberValidityCheck(paramsList.get(Nums.TWO.getValue())));
        List<String> dateErrorsList = FieldsValidation.dateValidityCheck(paramsList.get(Nums.THREE.getValue()),
            paramsList.get(Nums.FOUR.getValue()));
        errorList.addAll(dateErrorsList);
        return errorList;
    }

}
