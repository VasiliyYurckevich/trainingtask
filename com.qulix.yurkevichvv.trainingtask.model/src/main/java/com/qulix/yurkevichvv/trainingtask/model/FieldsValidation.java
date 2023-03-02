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
public class FieldsValidation implements Serializable {

    /**
     * Идентификатор даты начала работы.
     */
    private static final String BEGIN_DATE = "beginDate";

    /**
     * Идентификатор даты начала работы.
     */
    private static final String END_DATE = "endDate";

    /**
     * Сообщение о пустой строке.
     */
    private static final String EMPTY_FIELD_MASSAGE = "Поле для ввода не должно быть пустым";

    /**
     * Проверяет на валидность вводимую строку.
     *
     * @param string Строка для валидации
     * @param length максимальная длина строки
     * @return строка-сообщение об ошибке либо null
     */
    public static String checkString(String string, int length) {

        if (string.isBlank()) {
            return EMPTY_FIELD_MASSAGE;
        }

        if (string.trim().length() > length) {
            return String.format("Максимальная длинна ввода: %d символов", length);
        }

        return null;
    }

    /**
     * Проверяет численное поле.
     *
     * @param string Строка для валидации
     * @return строка-сообщение об ошибке либо null
     */
    public static String checkNumber(String string) {

        if (string.isBlank()) {
            return EMPTY_FIELD_MASSAGE;
        }

        try {
            Integer.parseInt(string.trim());
            return null;
        }
        catch (NumberFormatException e) {
            return "Значение ввода должно быть числом в промежутке от 0 до 2147483647";
        }
    }

    /**
     * Проверяет валидность дат.
     *
     * @param beginDate Дата начала
     * @param endDate Дата окончания
     * @return map с ошибками или null
     */
    public static Map<String, String> checkDate(String beginDate, String endDate) {

        Map<String, String> listErrors = new HashMap<>();

        String beginDateError = checkDateFormatValid(beginDate);
        String endDateError = checkDateFormatValid(endDate);

        listErrors.put(BEGIN_DATE, beginDateError);
        listErrors.put(END_DATE, endDateError);

        if (beginDateError == null && endDateError == null) {
            listErrors.put(END_DATE, checkDateRangeCorrectness(beginDate, endDate));
        }

        return listErrors;
    }

    /**
     * Проверяет вводимые даты на логичность последовательности даты начала и даты окончания.
     *
     * @param beginDate Дата начала
     * @param endDate Дата окончания
     * @return строка-сообщение об ошибке либо null
     */
    private static String checkDateRangeCorrectness(String beginDate, String endDate) {

        if (LocalDate.parse(beginDate).isAfter(LocalDate.parse(endDate))) {
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
            LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
            return null;
        }
        catch (DateTimeParseException e) {
            return "Введите существующую дату в формате ГГГГ-ММ-ДД";
        }
    }
}
