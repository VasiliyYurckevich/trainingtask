package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Обработчик токенов страниц.
 *
 * @author Q-YVV
 */
public class CSRFTokenHandler {

    /**
     * Список активных токенов.
     */
    private static final String TOKEN_LIST = "TOKEN_LIST";

    /**
     * Токен страницы.
     */
    private static final String TOKEN = "token";

    /**
     * Флаг, предотвращающий двойное выполнение действия.
     */
    public static final String IS_FIRST_REQUEST = "isFirstRequest";

    /**
     * Добавляет токен на страницу.
     *
     * @param request запрос, объект {@link HttpServletRequest}
     */
    public void addRequestToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> tokenList = Optional.ofNullable((List<String>) session.getAttribute(TOKEN_LIST)).orElse(new ArrayList<>());
        String token = UUID.randomUUID().toString();
        request.setAttribute(TOKEN, token);
        tokenList.add(token);
        session.setAttribute(TOKEN_LIST, tokenList);
    }

    /**
     * Проверяет токен на странице и записывает результат в запрос.
     *
     * @param request запрос, объект {@link HttpServletRequest}
     */
    public void handleRequestToken(HttpServletRequest request) {
        List<String> tokenList = (List<String>) request.getSession().getAttribute(TOKEN_LIST);

        String pageToken = request.getParameter(TOKEN);

        if (pageToken == null) {
            request.setAttribute(IS_FIRST_REQUEST, true);
            return;
        }
        request.setAttribute(IS_FIRST_REQUEST, tokenList.remove(pageToken));
    }
}
