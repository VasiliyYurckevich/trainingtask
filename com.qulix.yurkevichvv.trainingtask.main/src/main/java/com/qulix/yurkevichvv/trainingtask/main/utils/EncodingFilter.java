package com.qulix.yurkevichvv.trainingtask.main.utils;

import javax.servlet.*;
import java.io.IOException;

/**
 * Фильтр для кодирования данных.
 *
 * @author Q-YVV
 */
public class EncodingFilter implements Filter {

    private String encoding = "utf-8";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
        throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        filterChain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
    }
}
