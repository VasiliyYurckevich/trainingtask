package com.qulix.yurkevichvv.trainingtask.servlets.service.validation;

import java.util.HashMap;
import java.util.Map;

import com.qulix.yurkevichvv.trainingtask.model.FieldValidation;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

/**
 * Валидация данных {@link Task}.
 *
 * @author Q-YVV
 */
public final class TaskValidation implements ValidationService {

    /**
     * Статуса задачи.
     */
    private static final String STATUS = "status";

    /**
     * Название задачи.
     */
    private static final String TITLE = "title";

    /**
     * Время выполнение задачи.
     */
    private static final String WORK_TIME = "workTime";

    /**
     * Длинна короткой строки.
     */
    private static final int SHORT_LENGTH = 50;

    /**
     * Дата начала выполнения задачи.
     */
    private static final String BEGIN_DATE = "beginDate";

    /**
     * Дата окончания выполнения задачи.
     */
    private static final String END_DATE = "endDate";

    @Override
    public Map<String, String> validate(Map<String, String> paramsMap) {
        Map<String, String> errorList = new HashMap<>(paramsMap.size());
        errorList.put(STATUS, FieldValidation.checkString(paramsMap.get(STATUS), SHORT_LENGTH));
        errorList.put(TITLE, FieldValidation.checkString(paramsMap.get(TITLE), SHORT_LENGTH));
        errorList.put(WORK_TIME, FieldValidation.checkNumber(paramsMap.get(WORK_TIME)));
        errorList.putAll(FieldValidation.checkDate(paramsMap.get(BEGIN_DATE), paramsMap.get(END_DATE)));
        return errorList;
    }
}
