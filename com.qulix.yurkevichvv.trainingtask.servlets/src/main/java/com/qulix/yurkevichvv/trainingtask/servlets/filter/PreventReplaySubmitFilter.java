package com.qulix.yurkevichvv.trainingtask.servlets.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.yurkevichvv.trainingtask.servlets.controllers.CSRFTokenHandler;

/**
 * Фильтр множественной отправки запроса.
 *
 * @author Q-YVV
 */
public class PreventReplaySubmitFilter implements Filter {

    /**
     * Обработчик токенов.
     */
    private CSRFTokenHandler tokenHandler;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        this.tokenHandler = new CSRFTokenHandler();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        tokenHandler.handleRequestToken(httpServletRequest);
        chain.doFilter(httpServletRequest, httpServletResponse);
    }
}
