package by.tarasiuk.ct.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.tarasiuk.ct.manager.AttributeName.LOCALE;
import static by.tarasiuk.ct.manager.AttributeName.EN_US;

@WebFilter
public class DefaultLocaleFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("INSIDE - DEFAULT LOCALE FILTER");   //fixme

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);

        if(session.isNew()) {
            session.setAttribute(LOCALE, EN_US);
            LOGGER.info("Default locale page set to '{}'.", EN_US);
        }

        filterChain.doFilter(request, response);
    }
}
