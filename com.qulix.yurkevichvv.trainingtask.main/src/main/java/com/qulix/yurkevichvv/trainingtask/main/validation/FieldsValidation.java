package com.qulix.yurkevichvv.trainingtask.main.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

/**
 * Валидация для полей вводимых данных.
 *
 * @author Q-YVV
 */
public class FieldsValidation {

    /**
     * Проверка на валидность вводимой строки.
     *
     * @param s Строка для валидации.
     * @param length максимальная длина строки.
     * @return Строка с ошибкой или пустой строкой.
     */
    public static String isStringValidation(String s, int length) {
        StringBuffer error = new StringBuffer();
        isEmptyFieldValidation(s, error);
        if (error.length() == 0) {
            if (s.trim().length() > length) {
                error.append("Максимальная длинна ввода: ");
                error.append(length);
                error.append(" символов");
            }
        }
        return String.valueOf(error);
    }

    /**
     * Проверка численного поля.
     *
     * @param s Строка для валидации.
     * @return Строка с ошибкой или пустой строкой.
     */
    public static String isNumberValidation(String s) {
        StringBuffer error = new StringBuffer();
        isEmptyFieldValidation(s, error);
        if (error.length() == 0) {
            if (!s.trim().matches("^[0-9]+$")) {
                error.append("Поле принимает только цифры");
            }
            else {
                try {
                    Integer.parseInt(s.trim());
                }
                catch (NumberFormatException e) {
                    error.append("Значение ввода должно быть в промежутке от 0 до 2147483647");
                }
            }
        }
        return String.valueOf(error);
    }

    /**
     * Проверка на пустое поле.
     *
     * @param s Строка для валидации.
     * @param error Строка с ошибкой.
     */
    private static void isEmptyFieldValidation(String s, StringBuffer error) {
        if (s.isEmpty() || s.trim().length() == 0) {
            error.append("Поле для ввода не должно быть пустым");
        }
    }

    /**
     * Проверка на валидность дат.
     *
     * @param beginDate Дата начала.
     * @param endDate Дата окончания.
     * @return Строка с ошибкой или пустой строкой.
     */
    public static List<String> isDateValidation(String beginDate, String endDate) {
        StringBuffer errorBeginDate = new StringBuffer();
        StringBuffer errorEndDate = new StringBuffer();
        List<String> listErrors = new ArrayList<>();

        boolean beginDateValid = isDateFormatValidator(beginDate, errorBeginDate);
        boolean endDateValid = isDateFormatValidator(endDate, errorEndDate);

        if (beginDateValid && endDateValid) {
            isDateLogicalityValidator(beginDate, endDate, errorEndDate);
        }
        listErrors.add(String.valueOf(errorBeginDate));
        listErrors.add(String.valueOf(errorEndDate));
        return listErrors;
    }

    /**
     * Проверка вводимых дат на логичность последовательноти даты начала и даты окончания.
     *
     * @param beginDate Дата начала.
     * @param endDate Дата окончания.
     * @param error Строка с ошибкой.
     */
    private static void isDateLogicalityValidator(String beginDate, String endDate, StringBuffer error) {
        LocalDate parsedBeginDate = LocalDate.parse(beginDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);
        if (parsedBeginDate.isAfter(parsedEndDate)) {
            error.append("Дата начала задачи не может быть больше даты окончания задачи");
        }
    }

    /**
     * Проверка на формат и существование даты.
     *
     * @param date Дата для проверки.
     * @param error Строка с ошибкой.
     * @return true если дата валидная, false если нет.
     */
    private static boolean isDateFormatValidator(String date, StringBuffer error) {
        try {
            LocalDate.parse(date , DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT));
        }
        catch (DateTimeParseException e) {
            error.append("Введите существующуюю дату в формате ГГГГ-ММ-ДД");
            return false;
        }
        return true;
    }
}
