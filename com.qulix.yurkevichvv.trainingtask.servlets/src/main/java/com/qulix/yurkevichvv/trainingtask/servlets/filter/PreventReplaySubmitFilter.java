package com.qulix.yurkevichvv.trainingtask.servlets.filter;

import com.qulix.yurkevichvv.trainingtask.servlets.controllers.CSRFTokenHandler;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

/**
 * Фильтр множественной отправки запроса.
 *
 * @author Q-YVV
 */
public class PreventReplaySubmitFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (new CSRFTokenHandler().handleRequestToken(httpServletRequest)) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        httpServletResponse.sendRedirect(httpServletRequest.getRequestURI());
    }
}
