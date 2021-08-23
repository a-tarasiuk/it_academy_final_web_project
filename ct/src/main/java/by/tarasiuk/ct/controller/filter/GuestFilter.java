package by.tarasiuk.ct.controller.filter;

import by.tarasiuk.ct.controller.command.AttributeName;
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
import java.io.IOException;

import static by.tarasiuk.ct.model.entity.impl.Account.Role.GUEST;

/**
 * The filter checks the account role.
 */
@WebFilter
public class GuestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);

        Account account;

        if(session.isNew()) {
            account = new Account();
            account.setRole(GUEST);

        } else {
            account = (Account) session.getAttribute(AttributeName.ACCOUNT);

            if (account == null) {
                account = new Account();
                account.setRole(GUEST);
            } else if(account.getRole() == null) {
                account.setRole(GUEST);
            }
        }

        session.setAttribute(AttributeName.ACCOUNT, account);

        filterChain.doFilter(request, response);
    }
}