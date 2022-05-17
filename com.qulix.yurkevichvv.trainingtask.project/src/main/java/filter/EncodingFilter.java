
package filter;

import java.io.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Encoding filter.
 *<h2>Description</h2>
 * <p>This filter is used to encode the request and response.</p>
 * <p>The encoding is specified in the filter configuration.</p>
 *
 * @author  Q-YVV
 * @version 1.0
 * @since   1.0
 */
@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {

    private String encoding = "utf-8";

    /**
     * Filter method.
     *
     * @param req request
     * @param resp response
     *
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
        throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        filterChain.doFilter(req, resp);
    }

    /**
     * Set encoding.
     *
     * @param filterConfig filter config
     */
    public void init(FilterConfig filterConfig) {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
    }

}
