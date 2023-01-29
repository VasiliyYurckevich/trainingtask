package com.qulix.yurkevichvv.trainingtask.servlets.command;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.model.services.IService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ValidationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Команда с предварительной валидацией полученных данных.
 *
 * @author Q-YVV
 */
public class CommandWithValidation<T extends Entity> implements Command {

    /**
     * {@link ValidationService} для валидации {@link Entity}.
     */
    protected final ValidationService validationService;

    /**
     * {@link PageDataService} для работы с данными {@link Entity}.
     */
    protected final PageDataService<T> pageDataService;

    /**
     * Список с параметрами получеными со страницы.
     */
    protected Map<String, String> paramsMap;

    /**
     * Список ошибок.
     */
    protected Map<String, String> errorsMap;


    /**
     * Конструктор.
     *
     * @param validationService сервис валидации
     * @param pageDataService сервис взаимодействия сущностей модели и данных со страниц
     */
    public CommandWithValidation(ValidationService validationService, PageDataService<T> pageDataService) {
        this.validationService = validationService;
        this.pageDataService = pageDataService;
    }

    protected void successesAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

    }

    protected void failedAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    protected boolean isValid(){
        return errorsMap.values().stream().allMatch(Objects::isNull);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        paramsMap = pageDataService.getDataFromPage(req);
        errorsMap = validationService.validate(paramsMap);
        if (isValid()) {
            successesAction(req, resp);
        }
        else {
            failedAction(req, resp);
        }
    }
}
