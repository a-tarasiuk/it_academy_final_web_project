package by.tarasiuk.ct.controller.filter;

import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import static by.tarasiuk.ct.controller.command.CommandType.ACTIVATE_ACCOUNT;
import static by.tarasiuk.ct.controller.command.CommandType.APPLY_FREIGHT;
import static by.tarasiuk.ct.controller.command.CommandType.BAN_ACCOUNT;
import static by.tarasiuk.ct.controller.command.CommandType.CHANGE_LOCALE_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.CREATE_FORWARDER;
import static by.tarasiuk.ct.controller.command.CommandType.CREATE_OFFER;
import static by.tarasiuk.ct.controller.command.CommandType.CREATE_TRADING;
import static by.tarasiuk.ct.controller.command.CommandType.DEACTIVATE_OFFER;
import static by.tarasiuk.ct.controller.command.CommandType.GO_TO_ACCOUNT_EMPLOYEES_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.GO_TO_ACCOUNT_PASSWORD_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.GO_TO_CREATE_OFFER_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.GO_TO_MAIN_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.GO_TO_SIGN_IN_PAGE;
import static by.tarasiuk.ct.controller.command.CommandType.GO_TO_SIGN_UP_PAGE;
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
import static by.tarasiuk.ct.controller.command.CommandType.SIGN_IN;
import static by.tarasiuk.ct.controller.command.CommandType.SIGN_UP;
import static by.tarasiuk.ct.controller.command.CommandType.UNBAN_ACCOUNT;
import static by.tarasiuk.ct.controller.command.CommandType.UPDATE_ACCOUNT;
import static by.tarasiuk.ct.controller.command.CommandType.UPDATE_COMPANY;
import static by.tarasiuk.ct.controller.command.CommandType.UPDATE_OFFER;
import static by.tarasiuk.ct.controller.command.CommandType.UPDATE_PASSWORD;
import static by.tarasiuk.ct.model.entity.impl.Account.Role;
import static by.tarasiuk.ct.model.entity.impl.Account.Role.ADMINISTRATOR;
import static by.tarasiuk.ct.model.entity.impl.Account.Role.FORWARDER;
import static by.tarasiuk.ct.model.entity.impl.Account.Role.GUEST;
import static by.tarasiuk.ct.model.entity.impl.Account.Role.MANAGER;

/**
 * Command filter by account role.
 */
@WebFilter
public class CommandFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final EnumMap<CommandType, List<Role>> allowedCommands  = new EnumMap<>(CommandType.class);

    @Override
    public void init(FilterConfig filterConfig) {
        List<Role> guest = Collections.singletonList(GUEST);
        List<Role> administrator = Collections.singletonList(ADMINISTRATOR);
        List<Role> manager = Collections.singletonList(MANAGER);
        List<Role> forwarderManager = Arrays.asList(MANAGER, FORWARDER);
        List<Role> administratorManagerForwarder = Arrays.asList(ADMINISTRATOR, MANAGER, FORWARDER);
        List<Role> existRoles = Arrays.asList(GUEST, MANAGER, FORWARDER, ADMINISTRATOR);

        /**
         * For all roles.
         */
        allowedCommands.put(GO_TO_MAIN_PAGE, existRoles);

        /**
         * For account with role GUEST.
         */
        allowedCommands.put(GO_TO_SIGN_IN_PAGE, guest);
        allowedCommands.put(GO_TO_SIGN_UP_PAGE, guest);
        allowedCommands.put(SIGN_IN, guest);
        allowedCommands.put(SIGN_UP, guest);
        allowedCommands.put(ACTIVATE_ACCOUNT, guest);

        /**
         * For account with role ADMINISTRATOR.
         */
        allowedCommands.put(BAN_ACCOUNT, administrator);
        allowedCommands.put(UNBAN_ACCOUNT, administrator);
        allowedCommands.put(SHOW_ACCOUNT_LIST_FROM_ADMIN, administrator);
        allowedCommands.put(SHOW_ACCOUNT_EDITOR_FROM_ADMIN, administrator);

        /**
         * For account with role MANAGER.
         */
        allowedCommands.put(GO_TO_ACCOUNT_EMPLOYEES_PAGE, manager);
        allowedCommands.put(SHOW_FORWARDER_CREATOR_PAGE, manager);
        allowedCommands.put(CREATE_FORWARDER, manager);
        allowedCommands.put(UPDATE_COMPANY, manager);
        allowedCommands.put(SHOW_COMPANY_SETTINGS_PAGE, manager);
        allowedCommands.put(SHOW_FORWARDER_SETTINGS_PAGE, manager);

        /**
         * For account with role FORWARDER.
         */
        allowedCommands.put(GO_TO_CREATE_OFFER_PAGE, forwarderManager);
        allowedCommands.put(CREATE_OFFER, forwarderManager);
        allowedCommands.put(DEACTIVATE_OFFER, forwarderManager);
        allowedCommands.put(CREATE_TRADING, forwarderManager);
        allowedCommands.put(APPLY_FREIGHT, forwarderManager);
        allowedCommands.put(SHOW_ACCOUNT_OFFERS, forwarderManager);
        allowedCommands.put(SHOW_ACCOUNT_OFFER, forwarderManager);
        allowedCommands.put(SHOW_ACCOUNT_TRADINGS, forwarderManager);
        allowedCommands.put(SHOW_OFFER_EDITOR, forwarderManager);
        allowedCommands.put(UPDATE_OFFER, forwarderManager);

        /**
         * For account with roles FORWARDER, MANAGER, ADMINISTRATOR.
         */
        allowedCommands.put(LOGOUT, administratorManagerForwarder);
        allowedCommands.put(CHANGE_LOCALE_PAGE, administratorManagerForwarder);
        allowedCommands.put(GO_TO_ACCOUNT_PASSWORD_PAGE, administratorManagerForwarder);
        allowedCommands.put(SHOW_ACCOUNT_SETTINGS_PAGE, administratorManagerForwarder);
        allowedCommands.put(UPDATE_ACCOUNT, administratorManagerForwarder);
        allowedCommands.put(UPDATE_PASSWORD, administratorManagerForwarder);
        allowedCommands.put(SHOW_TRADING_OFFER, administratorManagerForwarder);
        allowedCommands.put(SHOW_OPEN_OFFER_LIST, administratorManagerForwarder);
        allowedCommands.put(SHOW_TRADING_VIEWER, administratorManagerForwarder);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute(AttributeName.ACCOUNT);
        LOGGER.debug("Current account entity is '{}'.", account);

        String command = request.getParameter(AttributeName.COMMAND);
        Account.Role role;
        List<Account.Role> roleList;

        if (command != null && Arrays.stream(CommandType.values())
                .anyMatch(commandType -> command.equalsIgnoreCase(commandType.name()))) {
            LOGGER.debug("Found command '{}'.", command);

            roleList = allowedCommands.get(CommandType.valueOf(command.toUpperCase()));
            LOGGER.debug("Load role list '{}'", roleList);

            if(account != null && account.getRole() != null) {
                role = account.getRole();
                LOGGER.debug("Current account role is '{}'", role);

                if(roleList.stream().anyMatch(role::equals)) {
                    filterChain.doFilter(request, response);
                } else {
                    RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.ERROR_ACCESS_DENIED);
                    LOGGER.debug("Unsupported '{}' command for account with role '{}'", command, role);
                    dispatcher.forward(request, response);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}