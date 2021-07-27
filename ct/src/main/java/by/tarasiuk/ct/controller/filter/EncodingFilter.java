package by.tarasiuk.ct.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "EncodingFilter", urlPatterns = {"/*"})
public class EncodingFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private final static String ENCODING_UTF_8 = "UTF-8";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String requestCharacterEncoding = servletRequest.getCharacterEncoding();
        String responseCharacterEncoding = servletResponse.getCharacterEncoding();

        if(!requestCharacterEncoding.equalsIgnoreCase(ENCODING_UTF_8) || !responseCharacterEncoding.equalsIgnoreCase(ENCODING_UTF_8)) {
            servletRequest.setCharacterEncoding(ENCODING_UTF_8);
            servletResponse.setCharacterEncoding(ENCODING_UTF_8);
            LOGGER.info("Encoding was change for request '{}', for response '{}'.",
                    servletRequest.getCharacterEncoding(), servletResponse.getCharacterEncoding());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
