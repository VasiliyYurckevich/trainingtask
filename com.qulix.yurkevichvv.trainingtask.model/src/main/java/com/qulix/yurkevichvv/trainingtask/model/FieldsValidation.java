package com.qulix.yurkevichvv.trainingtask.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.Map;

/**
 * Валидация для полей вводимых данных.
 *
 * @author Q-YVV
 */
public class
FieldsValidation implements Serializable {

    /**
     * Проверяет на валидность вводимую строку.
     *
     * @param s Строка для валидации
     * @param length максимальная длина строки
     * @return строка-сообщение об ошибке либо null
     */
    public static String checkString(String s, int length) {
        String error = checkForEmptiness(s);

        if (error == null) {
            if (s.trim().length() > length) {
                return String.format("Максимальная длинна ввода: %d символов", length);
            }
        }
        return error;
    }

    /**
     * Проверяет численное поле.
     *
     * @param s Строка для валидации
     * @return строка-сообщение об ошибке либо null
     */
    public static String checkNumber(String s) {
        String error = checkForEmptiness(s);

        if (error == null) {
            try {
                Integer.parseInt(s.trim());
            }
            catch (NumberFormatException e) {
                return "Значение ввода должно быть числом в промежутке от 0 до 2147483647";
            }
        }
        return error;
    }

    /**
     * Проверяет поле на пустоту.
     *
     * @param s Строка для валидации
     * @return строка-сообщение об ошибке либо null
     */
    private static String checkForEmptiness(String s) {
        if (s.isEmpty() || s.trim().length() == 0) {
            return "Поле для ввода не должно быть пустым";
        }
        else {
            return null;
        }
    }

    /**
     * Проверяет валидность дат.
     *
     * @param beginDate Дата начала
     * @param endDate Дата окончания
     * @return Строка с ошибкой или пустой строкой
     */
    public static Map<String, String> checkDate(String beginDate, String endDate) {
        Map<String, String> listErrors = new HashMap<>();

        String beginDateError = checkDateFormatValid(beginDate);
        String endDateError = checkDateFormatValid(endDate);

        if (beginDateError == null && endDateError == null) {
            endDateError = checkDateRangeCorrectness(beginDate, endDate);
        }
        String finalEndDateError = endDateError;

        listErrors.put("beginDate", beginDateError);
        listErrors.put("endDate", finalEndDateError);
        return listErrors;
    }

    /**
     * Проверяет вводимые даты на логичность последовательности даты начала и даты окончания.
     *
     * @param beginDate Дата начала
     * @param endDate Дата окончания
     * @return строка-сообщение об ошибке либо null
     */
    public static String checkDateRangeCorrectness(String beginDate, String endDate) {
        LocalDate parsedBeginDate = LocalDate.parse(beginDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);
        if (parsedBeginDate.isAfter(parsedEndDate)) {
            return "Дата начала задачи не может быть больше даты окончания задачи";
        }
        return null;
    }

    /**
     * Проверят формат и существование даты.
     *
     * @param date Дата для проверки
     * @return строка-сообщение об ошибке либо null
     */
    private static String checkDateFormatValid(String date) {
        try {
            LocalDate.parse(date , DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
        }
        catch (DateTimeParseException e) {
            return "Введите существующую дату в формате ГГГГ-ММ-ДД";

        }
        return null;
    }
}
