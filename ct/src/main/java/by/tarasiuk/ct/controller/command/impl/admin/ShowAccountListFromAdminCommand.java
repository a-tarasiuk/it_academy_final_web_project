package by.tarasiuk.ct.controller.command.impl.admin;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

/**
 * Show all accounts command for administrator
 */
public class ShowAccountListFromAdminCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();

    @Override
    public String execute(RequestContent content) {
        String page = PagePath.ADMIN_ACCOUNT_LIST;

        try {
            List<Account> accountList = accountService.findAccountList();
            content.putRequestAttribute(AttributeName.ACCOUNT_LIST, accountList);
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_LIST_FROM_ADMIN, e);
            page = PagePath.INFO;
        }

        return page;
    }
}
