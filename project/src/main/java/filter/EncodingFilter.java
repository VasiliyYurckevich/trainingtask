package filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Encoding filter.
 *
 * @author  Yurkevichvv
 * @version 1.0
 */
@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {

    private String encoding = "utf-8";// default encoding

    /**
     * Filter method.
     *
     * @param req request
     * @param resp response
     *
     */
    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain filterChain)
            throws IOException, ServletException {
        req.setCharacterEncoding(encoding);// set encoding
        filterChain.doFilter(req, resp);// do filter
    }

    /**
     * Set encoding.
     *
     * @param filterConfig filter config
     */
    public void init(FilterConfig filterConfig) {
        String encodingParam = filterConfig.getInitParameter("encoding");// get encoding param
        if (encodingParam != null) {
            encoding = encodingParam;// set encoding
        }
    }

}