package by.tarasiuk.ct.controller.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Search for prohibited tags in the query string and replace them.
 */
@WebFilter
public class XssFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Enumeration<String> parameterNames = servletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = servletRequest.getParameterValues(paramName);

            for (int i = 0; i < paramValues.length; i++) {
                if (paramValues[i] != null) {
                    paramValues[i] = paramValues[i].replaceAll("<", "&lt;")
                            .replaceAll(">", "&gt;")
                            .replaceAll("\"", "&quot;")
                            .replaceAll("'", "&#x27;")
                            .replaceAll("&", "&amp;")
                            .replaceAll("/", "&#x2F;")
                            .replaceAll("\\(", "&#40;")
                            .replaceAll("\\)", "&#41;")
                            .replaceAll(":", "&#58;");
                }
            }
            servletRequest.setAttribute(paramName, paramValues);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}