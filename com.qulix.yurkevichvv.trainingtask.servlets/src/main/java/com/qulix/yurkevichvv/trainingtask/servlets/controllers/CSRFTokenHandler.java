package com.qulix.yurkevichvv.trainingtask.servlets.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CSRFTokenHandler {

    /**
     * Список активных токенов.
     */
    private static final String TOKEN_LIST = "TOKEN_LIST";

    /**
     * Токен страницы.
     */
    private static final String TOKEN = "token";

    public void addRequestToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> tokenList = Optional.ofNullable((List<String>) session.getAttribute(TOKEN_LIST)).orElse(new ArrayList<>());
        String token = UUID.randomUUID().toString();
        request.setAttribute(TOKEN, token);
        tokenList.add(token);
        session.setAttribute(TOKEN_LIST, tokenList);
    }

    public boolean handleRequestToken(HttpServletRequest request) {
        List<String> tokenList = (List<String>) request.getSession().getAttribute("TOKEN_LIST");

        String pageToken = request.getParameter("token");

        if (pageToken == null) {
            return true;
        }
        return tokenList.remove(pageToken);
    }
}
