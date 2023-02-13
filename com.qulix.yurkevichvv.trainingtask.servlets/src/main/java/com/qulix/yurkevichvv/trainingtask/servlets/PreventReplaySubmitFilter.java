package com.qulix.yurkevichvv.trainingtask.servlets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreventReplaySubmitFilter implements Filter {
    public static final String TOKEN_LIST = "TOKEN_LIST";
    public static final String TOKEN = "token";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();

        List submissionTokenList = (List) session.getAttribute(TOKEN_LIST);
        if (submissionTokenList == null) {
            submissionTokenList = new ArrayList();
            session.setAttribute(TOKEN_LIST, submissionTokenList);
        }

        String pageToken = httpServletRequest.getParameter(TOKEN);
            if (pageToken == null || "".equals(pageToken)) {
            chain.doFilter(request, response);
            return;
        }

        submissionTokenList.forEach(System.out::println);
        if (submissionTokenList.contains(pageToken)) {
            submissionTokenList.remove(pageToken);
            chain.doFilter(request, response);
            return;
        }

    }
}
