package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Контроллер команд получаемых из запроса клиента.
 *
 * @author Q-YVV
 */
public abstract class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TokenHandler.addRequestToken(request);
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TokenHandler.addRequestToken(request);
        processRequest(request, response);
    }

    /**
     * Контролирует и выполняет команды полученные от клиента.
     *
     * @param request {@link HttpServletRequest} объект, содержащий запрос клиента к сервлету
     * @param response {@link HttpServletResponse} объект, содержащий ответ сервлета клиенту
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     */
    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException;
}
