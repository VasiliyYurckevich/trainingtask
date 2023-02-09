package com.qulix.yurkevichvv.trainingtask.servlets.command;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;
import com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter.PageDataService;
import com.qulix.yurkevichvv.trainingtask.servlets.service.validation.ValidationService;

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
     * {@link Map} с параметрами полученными со страницы.
     */
    protected Map<String, String> paramsMap;

    /**
     * {@link Map} ошибок.
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

    /**
     * Действия при отсутствии ошибок.
     *
     * @param req  {@link HttpServletRequest} объект, содержащий запрос клиента к сервлету
     * @param resp {@link HttpServletResponse} объект, содержащий ответ сервлета клиенту
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException      если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     */
    protected void successesAction(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

    }

    /**
     * Действия при наличии ошибок.
     *
     * @param req {@link HttpServletRequest} объект, содержащий запрос клиента к сервлету
     * @param resp {@link HttpServletResponse} объект, содержащий ответ сервлета клиенту
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     */
    protected void failedAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    /**
     * Проверяет наличие ошибок, полученных от валидатора.
     *
     * @return true, если список ошибок пуст, иначе false
     */
    protected boolean isValid() {
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
