package com.qulix.yurkevichvv.trainingtask.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Фильтр для кодирования данных.
 *
 * @author Q-YVV
 */
public class EncodingFilter implements Filter {

    /**
     * Название выборного типа кодирования.
     */
    private String encoding = "utf-8";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
        throws IOException, ServletException {

        req.setCharacterEncoding(encoding);
        filterChain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println(filterConfig.toString());
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
    }
}
