package by.tarasiuk.ct.controller.filter;

import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.manager.RequestAttribute;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AdministratorFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Account account =(Account) session.getAttribute(RequestAttribute.ACCOUNT);
        if(account != null && account.getRole().equals(Account.Role.ADMINISTRATOR)) {
            session.setAttribute(RequestAttribute.ACTIVATE_ADMIN_PANEL, true);
        }

        filterChain.doFilter(request, response);
    }
}
