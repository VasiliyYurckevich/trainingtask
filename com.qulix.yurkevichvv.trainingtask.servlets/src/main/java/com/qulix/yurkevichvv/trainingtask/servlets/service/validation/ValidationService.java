package com.qulix.yurkevichvv.trainingtask.servlets.service.validation;

import java.util.Map;

public interface ValidationService {

    /**
     * Проверяет правильнось полученных из формы данных.
     *
     * @param paramsMap список параметров
     * @return список ошибок
     */
    Map<String, String> validate(Map<String, String> paramsMap);
}
