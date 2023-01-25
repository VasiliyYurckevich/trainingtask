package com.qulix.yurkevichvv.trainingtask.servlets.service;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Отвечает за взаимодействие сущностей модели и данных со страниц.
 *
 * @param <T>
 */
public interface PageDataService<T extends Entity> {

    void setOutputDataToEntity(Map<String, String> paramsMap, T entity);

    void setValidatedDataToPage(HttpServletRequest req, Map<String, String> paramsMap, Map<String, String> errorsMap);

    Map<String, String> getDataFromPage(HttpServletRequest req);

    void setDataToPage(HttpServletRequest req, T entity);

    T getEntity(HttpServletRequest req);
}
