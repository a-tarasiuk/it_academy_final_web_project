package by.tarasiuk.ct.command.filter;

import by.tarasiuk.ct.manager.RequestAttribute;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "LocaleFilter", urlPatterns = {"/*"})
public class LocaleFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Locale operationSystemLocale = Locale.getDefault();
        String operationSystemLanguage = operationSystemLocale.getLanguage();
        LOGGER.info("Locale on user operation system is: {}.", operationSystemLocale);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(true);
        String expressionLocalePage = (String) session.getAttribute(RequestAttribute.LOCALE_PAGE);

        if(expressionLocalePage == null) {
            String language;

            switch (operationSystemLanguage.toLowerCase()) {
                case RequestAttribute.USER_LOGIN:
                    language = RequestAttribute.LOCALE_RU;
                    break;
                default:
                    language = RequestAttribute.LOCALE_EN;
                    break;
            }

            session.setAttribute(RequestAttribute.LOCALE_PAGE, language);
            LOGGER.info("Default locale page set to '{}'.", language);
        }

        filterChain.doFilter(request, response);
    }
}
