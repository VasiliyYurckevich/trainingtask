package com.qulix.yurkevichvv.trainingtask.servlets.filter;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Фильтр множественной отправки запроса.
 *
 * @author Q-YVV
 */
public class PreventReplaySubmitFilter implements Filter {

    /**
     * Логгер.
     */
    private static final Logger LOGGER = Logger.getLogger(PreventReplaySubmitFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        List<String> submissionTokenList = (List<String>) httpServletRequest.getSession().getAttribute("TOKEN_LIST");

        String pageToken = httpServletRequest.getParameter("token");
        if (pageToken == null || "".equals(pageToken)) {
            chain.doFilter(request, response);
            return;
        }

        if (submissionTokenList.contains(pageToken)) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        }
        else {
            httpServletResponse.sendRedirect(httpServletRequest.getRequestURI());
        }
        submissionTokenList.remove(pageToken);
    }
}
