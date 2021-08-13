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
import java.io.IOException;

import static by.tarasiuk.ct.model.entity.impl.Account.Role;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.controller.command.AttributeName.SHOW_ADMIN_PANEL;

@WebFilter
public class AdministratorFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute(ACCOUNT);
        if(account != null && account.getRole().equals(Role.ADMINISTRATOR)) {
            session.setAttribute(SHOW_ADMIN_PANEL, true);
        }

        filterChain.doFilter(request, response);
    }
}
