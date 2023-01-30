package com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;

/**
 * Отвечает за взаимодействие сущностей модели и данных со страниц.
 *
 * @param <T> сущность {@link Entity}
 * @author Q-YVV
 */
public interface PageDataService<T extends Entity> {

    /**
     * Обновляет данные {@link Entity} из списка валидированных параметров.
     *
     * @param paramsMap {@link Map} с данными полученными от клиента
     * @param entity сущность для обновления данных
     */
    void setOutputDataToEntity(Map<String, String> paramsMap, T entity);

    /**
     * Помещает данные не прошедшие валидацию на страницу.
     *
     * @param req {@link HttpServletRequest} объект, содержащий данные запроса клиента к серверу
     * @param paramsMap {@link Map} с данными полученными от клиента
     * @param errorsMap {@link Map} с сообщениями об ошибках
     */
    void setFailedDataToPage(HttpServletRequest req, Map<String, String> paramsMap, Map<String, String> errorsMap);

    /**
     * Создает объект {@link Map} с данными сущности, введенными клиентом.
     *
     * @param req {@link HttpServletRequest} объект, содержащий данные запроса клиента к серверу
     * @return {@link Map} с данными полученными от клиента
     */
    Map<String, String> getDataFromPage(HttpServletRequest req);

    /**
     * Помещает данные полученные из {@link Entity} на страницу.
     *
     * @param req {@link HttpServletRequest} объект, содержащий данные запроса клиента к серверу
     * @param entity сущность с требуемыми данными
     */
    void setDataToPage(HttpServletRequest req, T entity);

    /**
     * Возвращает сущность с требуемым ID либо создает новую.
     *
     * @param req {@link HttpServletRequest} объект, содержащий данные запроса клиента к серверу
     * @return сущность с требуемыми данными
     */
    T getEntity(HttpServletRequest req);
}
