package by.tarasiuk.ct.controller.filter;

import by.tarasiuk.ct.model.entity.impl.Account;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.tarasiuk.ct.model.entity.impl.Account.Role;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.controller.command.AttributeName.SHOW_ADMIN_FUNCTIONS;

/**
 * Fil checks the role of the account.
 * If the role is <code>ADMINISTRATOR</code>, then the flag is set to display the code block on the jsp page.
 */
@WebFilter
public class AdministratorFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        if(session != null) {
            Account account = (Account) session.getAttribute(ACCOUNT);
            LOGGER.debug("Current account entity '{}'.", account);

            if(account != null && account.getRole() != null && account.getRole().equals(Role.ADMINISTRATOR)) {
                session.setAttribute(SHOW_ADMIN_FUNCTIONS, true);
            }
        }

        filterChain.doFilter(request, response);
    }
}
