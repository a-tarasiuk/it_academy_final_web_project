package by.tarasiuk.ct.controller.command.impl.account;

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

import java.util.Map;
import java.util.Optional;

public class ShowAccountSettingsPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();

    @Override
    public String execute(RequestContent requestContent) {
        String page = PagePath.ACCOUNT_SETTINGS;
        Map<String, Object> sessionAttributes = requestContent.getSessionAttributes();
        Account account = (Account) sessionAttributes.get(AttributeName.ACCOUNT);
        long accountId = account.getId();

        try {
            Optional<Account> findAccount = accountService.findAccountById(accountId);

            if(findAccount.isPresent()) {
                Account currentAccount = findAccount.get();
                requestContent.putRequestAttribute(AttributeName.ACCOUNT, currentAccount);
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_SETTINGS_PAGE, e);
            page = PagePath.ACCOUNT_OFFERS;
        }

        LOGGER.info("Command '{}' return path '{}'", CommandType.SHOW_ACCOUNT_SETTINGS_PAGE, PagePath.ACCOUNT_SETTINGS);
        return page;
    }
}
