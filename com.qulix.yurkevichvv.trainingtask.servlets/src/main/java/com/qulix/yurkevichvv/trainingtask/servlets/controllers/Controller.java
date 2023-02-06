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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Контролирует и выполняет команды полученные от клиента.
     *
     * @param req {@link HttpServletRequest} объект, содержащий запрос клиента к сервлету
     * @param resp {@link HttpServletResponse} объект, содержащий ответ сервлета клиенту
     * @throws ServletException определяет общее исключение, которое сервлет может выдать при возникновении затруднений
     * @throws IOException если обнаружена ошибка ввода или вывода, когда сервлет обрабатывает запрос GET
     */
    protected abstract void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
        IOException;
}
