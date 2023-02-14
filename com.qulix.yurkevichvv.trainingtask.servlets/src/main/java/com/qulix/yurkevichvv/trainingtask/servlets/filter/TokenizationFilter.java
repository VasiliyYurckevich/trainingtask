package com.qulix.yurkevichvv.trainingtask.servlets.filter;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

    /**
     * Длина токена.
     */
    private static final int TOKEN_LENGTH = 25;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpSession session = httpServletRequest.getSession();

        List<String> tokenList = (List<String>) session.getAttribute(TOKEN_LIST);
        if (tokenList == null) {
            session.setAttribute(TOKEN_LIST, new ArrayList<>());
        }
        generateToken(httpServletRequest);
        chain.doFilter(request, response);
    }

    private void generateToken(HttpServletRequest req) {
        byte[] randomBytes = new byte[TOKEN_LENGTH];
        new SecureRandom().nextBytes(randomBytes);
        String token = Base64.getUrlEncoder().encodeToString(randomBytes);

        List<String> tokenList = (List<String>) req.getSession().getAttribute(TOKEN_LIST);
        tokenList.add(token);

        req.setAttribute(TOKEN, token);
    }
}
