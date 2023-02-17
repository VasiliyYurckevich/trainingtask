package com.qulix.yurkevichvv.trainingtask.servlets.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Интерфейс Команда.
 *
 * @author Q-YVV
 */
public interface Command {

    /**
     * Основной метод команды для выполнения действия.
     *
     * @param request the {@link HttpServletRequest} объект, содержащий запрос клиента к сервлету
     * @param response the {@link HttpServletResponse} объект, содержащий ответ, который сервлет возвращает клиенту
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
