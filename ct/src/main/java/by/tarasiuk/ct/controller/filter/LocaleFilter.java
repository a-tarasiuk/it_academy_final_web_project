package by.tarasiuk.ct.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.tarasiuk.ct.manager.RequestAttribute.LOCALE_EN;
import static by.tarasiuk.ct.manager.RequestAttribute.LOCALE_PAGE;

@WebFilter(filterName = "LocaleFilter", urlPatterns = {"/*"})
public class LocaleFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        if (session.getAttribute(LOCALE_PAGE) == null) {
            session.setAttribute(LOCALE_PAGE, LOCALE_EN);
            LOGGER.info("Default locale page set to '{}'.", LOCALE_EN);
        }

        filterChain.doFilter(request, response);
    }
}
