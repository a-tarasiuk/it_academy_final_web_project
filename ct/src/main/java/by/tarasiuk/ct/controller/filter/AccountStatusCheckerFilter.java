package by.tarasiuk.ct.controller.filter;

import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
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
import java.util.Optional;

/**
 * The filter checks the account status.
 */
@WebFilter
public class AccountStatusCheckerFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);

        Account sessionAccount = (Account) session.getAttribute(AttributeName.ACCOUNT);
        Account.Role sessionRole = sessionAccount.getRole();
        if(sessionRole.equals(Account.Role.GUEST)) {
            filterChain.doFilter(request, response);
        } else {
            try {
                Optional<Account> findAccount = accountService.findAccountById(sessionAccount.getId());

                if(findAccount.isPresent()) {
                    Account dbAccount = findAccount.get();
                    Account.Status sessionStatus = sessionAccount.getStatus();
                    Account.Status dbStatus = dbAccount.getStatus();

                    if(!sessionStatus.equals(dbStatus)) {
                        session.invalidate();
                        LOGGER.debug("Session invalidate for '{}'", dbAccount);

                        RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.ERROR_ACCESS_DENIED);
                        dispatcher.forward(request, response);
                    } else {
                        filterChain.doFilter(request, response);
                    }
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }
}