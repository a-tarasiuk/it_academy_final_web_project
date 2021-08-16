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
import java.util.Optional;

/**
 * For a user with the ADMINISTRATOR role only.
 * Ban account command for administrator.
 */
public class UnbanAccountCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();

    /**
     * The database searches for an account by the ID that came from the page.
     * If such an account exists, unban the account.
     * @param content - RequestContent
     * @return page address admin account editor
     */
    @Override
    public String execute(RequestContent content) {
        String page = PagePath.ADMIN_ACCOUNT_EDITOR;

        Optional<String> findAccountId = content.findRequestParameter(AttributeName.ACCOUNT_ID);
        if(findAccountId.isPresent()) {
            long accountId = Long.parseLong(findAccountId.get());

            try {
                if(accountService.unbanAccountById(accountId)) {
                    Optional<Account> findAccount = accountService.findAccountById(accountId);

                    if(findAccount.isPresent()) {
                        Account account = findAccount.get();

                        content.putRequestAttribute(AttributeName.ACCOUNT_FOR_CHANGE, account);
                        content.putRequestAttribute(AttributeName.SUCCESSFUL_OPERATION, true);
                        LOGGER.info("Account with ID '{}' successfully unbanned from admin.", accountId);
                    }
                } else {
                    content.putRequestAttribute(AttributeName.MESSAGE_QUERY_ERROR, true);
                    LOGGER.info("Can not unban account with ID '{}' from admin.", accountId);
                }
            } catch (ServiceException e) {
                LOGGER.error("Failed to process the command '{}'.", CommandType.UNBAN_ACCOUNT, e);
                page = PagePath.INFO;
            }
        }

        return page;
    }
}
