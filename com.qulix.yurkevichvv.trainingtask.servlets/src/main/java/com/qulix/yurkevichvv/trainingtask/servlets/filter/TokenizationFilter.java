package com.qulix.yurkevichvv.trainingtask.servlets.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Фильтр для добавления токенов на страницу.
 *
 * @author Q-YVV
 */
public class TokenizationFilter implements Filter {

    /**
     * Список активных токенов.
     */
    private static final String TOKEN_LIST = "TOKEN_LIST";

    /**
     * Токен страницы.
     */
    private static final String TOKEN = "token";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpSession session = httpServletRequest.getSession();

        List<String> tokenList = Optional.ofNullable((List<String>) session.getAttribute(TOKEN_LIST)).orElse(new ArrayList<>());

        generateToken(httpServletRequest, tokenList);
        session.setAttribute(TOKEN_LIST, tokenList);
        chain.doFilter(request, response);
    }

    /**
     * Генерирует токен и добавляет его в список.
     *
     * @param req запрос
     * @param tokenList список токенов
     */
    private void generateToken(HttpServletRequest req, List<String> tokenList) {
        String token = UUID.randomUUID().toString();
        req.setAttribute(TOKEN, token);
        tokenList.add(token);
    }
}
