package by.tarasiuk.ct.controller.filter;

import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import static by.tarasiuk.ct.controller.command.CommandType.APPLY_FREIGHT;
import static by.tarasiuk.ct.controller.command.CommandType.BAN_ACCOUNT;
import static by.tarasiuk.ct.controller.command.CommandType.CREATE_FORWARDER;
import static by.tarasiuk.ct.controller.command.CommandType.CREATE_OFFER;
import static by.tarasiuk.ct.controller.command.CommandType.CREATE_TRADING;
import static by.tarasiuk.ct.controller.command.CommandType.DEACTIVATE_OFFER;
import static by.tarasiuk.ct.controller.command.CommandType.GO_TO_ACCOUNT_EMPLOYEES_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.GO_TO_ACCOUNT_PASSWORD_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.GO_TO_CREATE_OFFER_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.LOGOUT;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_ACCOUNT_EDITOR_FROM_ADMIN;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_ACCOUNT_LIST_FROM_ADMIN;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_ACCOUNT_OFFER;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_ACCOUNT_OFFERS;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_ACCOUNT_SETTINGS_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_ACCOUNT_TRADINGS;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_COMPANY_SETTINGS_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_FORWARDER_CREATOR_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_FORWARDER_SETTINGS_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_OFFER_EDITOR;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_OPEN_OFFER_LIST;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_TRADING_OFFER;
import static by.tarasiuk.ct.controller.command.CommandType.SHOW_TRADING_VIEWER;
import static by.tarasiuk.ct.controller.command.CommandType.UNBAN_ACCOUNT;
import static by.tarasiuk.ct.controller.command.CommandType.UPDATE_ACCOUNT;
import static by.tarasiuk.ct.controller.command.CommandType.UPDATE_COMPANY;
import static by.tarasiuk.ct.controller.command.CommandType.UPDATE_OFFER;
import static by.tarasiuk.ct.controller.command.CommandType.UPDATE_PASSWORD;
import static by.tarasiuk.ct.model.entity.impl.Account.Role;
import static by.tarasiuk.ct.model.entity.impl.Account.Role.ADMINISTRATOR;
import static by.tarasiuk.ct.model.entity.impl.Account.Role.FORWARDER;
import static by.tarasiuk.ct.model.entity.impl.Account.Role.MANAGER;

/**
 * Command filter by account role.
 */
@WebFilter
public class CommandFilter implements Filter {
    private static final EnumMap<CommandType, List<Role>> allowedCommands  = new EnumMap<>(CommandType.class);

    @Override
    public void init(FilterConfig filterConfig) {
        List<Role> administrator = Collections.singletonList(ADMINISTRATOR);
        List<Role> manager = Collections.singletonList(MANAGER);
        List<Role> managerAndForwarder = Arrays.asList(MANAGER, FORWARDER);
        List<Role> allRoles = Arrays.asList(ADMINISTRATOR, MANAGER, FORWARDER);

        allowedCommands.put(BAN_ACCOUNT, administrator);
        allowedCommands.put(UNBAN_ACCOUNT, administrator);
        allowedCommands.put(SHOW_ACCOUNT_LIST_FROM_ADMIN, administrator);
        allowedCommands.put(SHOW_ACCOUNT_EDITOR_FROM_ADMIN, administrator);

        allowedCommands.put(GO_TO_ACCOUNT_EMPLOYEES_PAGE, manager);
        allowedCommands.put(SHOW_FORWARDER_CREATOR_PAGE, manager);
        allowedCommands.put(CREATE_FORWARDER, manager);
        allowedCommands.put(UPDATE_COMPANY, manager);
        allowedCommands.put(SHOW_COMPANY_SETTINGS_PAGE, manager);
        allowedCommands.put(SHOW_FORWARDER_SETTINGS_PAGE, manager);

        allowedCommands.put(GO_TO_CREATE_OFFER_PAGE, managerAndForwarder);
        allowedCommands.put(CREATE_OFFER, managerAndForwarder);
        allowedCommands.put(SHOW_OPEN_OFFER_LIST, managerAndForwarder);
        allowedCommands.put(DEACTIVATE_OFFER, managerAndForwarder);
        allowedCommands.put(CREATE_TRADING, managerAndForwarder);
        allowedCommands.put(APPLY_FREIGHT, managerAndForwarder);
        allowedCommands.put(SHOW_ACCOUNT_OFFERS, managerAndForwarder);
        allowedCommands.put(SHOW_ACCOUNT_OFFER, managerAndForwarder);
        allowedCommands.put(SHOW_ACCOUNT_TRADINGS, managerAndForwarder);
        allowedCommands.put(SHOW_OFFER_EDITOR, managerAndForwarder);
        allowedCommands.put(UPDATE_OFFER, managerAndForwarder);

        allowedCommands.put(LOGOUT, allRoles);
        allowedCommands.put(GO_TO_ACCOUNT_PASSWORD_PAGE, allRoles);
        allowedCommands.put(SHOW_ACCOUNT_SETTINGS_PAGE, allRoles);
        allowedCommands.put(UPDATE_ACCOUNT, allRoles);
        allowedCommands.put(UPDATE_PASSWORD, allRoles);
        allowedCommands.put(SHOW_TRADING_OFFER, allRoles);
        allowedCommands.put(SHOW_TRADING_VIEWER, allRoles);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute(AttributeName.ACCOUNT);
        String command = request.getParameter(AttributeName.COMMAND);
        Account.Role role;
        List<Account.Role> roleList;

        if (command != null && Arrays.stream(CommandType.values()).anyMatch(commandType -> command.equalsIgnoreCase(commandType.name()))) {
            roleList = allowedCommands.get(CommandType.valueOf(command.toUpperCase()));

            if(account != null) {
                role = account.getRole();

                if(roleList.stream().anyMatch(role::equals)) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + PagePath.ERROR_ACCESS_DENIED);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}