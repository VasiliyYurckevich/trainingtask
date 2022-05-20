import java.io.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Encoding filter.
 *
 * @author  Q-YVV
 * @version 1.0
 * @since   1.0
 */
public class EncodingFilter implements Filter {
    private String encoding = "utf-8";

    /**
     * Filter method.
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
        throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        filterChain.doFilter(req, resp);
    }

    /**
     * Set encoding.
     */
    public void init(FilterConfig filterConfig) {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
    }
}
