package by.tarasiuk.ct.controller.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.tarasiuk.ct.controller.command.AttributeName.PREVIOUS_COMMAND;

/**
 * Fil checks the role of the account.
 * If the role is <code>ADMINISTRATOR</code>, then the flag is set to display the code block on the jsp page.
 */
@WebFilter
public class PreviousCommandFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String REQUEST_COMPONENT = "?";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        StringBuilder currentCommand = new StringBuilder(request.getRequestURI())
                .append(REQUEST_COMPONENT)
                .append(request.getQueryString());

        String previousCommand = (String) request.getSession().getAttribute(PREVIOUS_COMMAND);
        filterChain.doFilter(request, response);

        request.getSession().setAttribute(PREVIOUS_COMMAND, currentCommand.toString());
    }
}
