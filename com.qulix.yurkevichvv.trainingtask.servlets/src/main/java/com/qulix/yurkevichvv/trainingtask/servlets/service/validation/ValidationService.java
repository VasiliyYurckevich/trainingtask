package com.qulix.yurkevichvv.trainingtask.servlets.service.validation;

import java.util.Map;

/**
 * Проверяет данные полученные от клиента.
 *
 * @author Q-YVV
 */
public interface ValidationService {

    /**
     * Проверяет правильность полученных из формы данных.
     *
     * @param paramsMap список параметров
     * @return список ошибок
     */
    Map<String, String> validate(Map<String, String> paramsMap);
}
