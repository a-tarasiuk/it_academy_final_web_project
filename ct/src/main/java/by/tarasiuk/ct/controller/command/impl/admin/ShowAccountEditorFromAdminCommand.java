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
 * Show account editor page command for administrator.
 */
public class ShowAccountEditorFromAdminCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();

    /**
     * The account ID comes from the request.
     * The account ID is used to search the database for the account object.
     * If successful, the account object is sent to the page for editing.
     * @param content - RequestContent
     * @return admin account editor page
     */
    @Override
    public String execute(RequestContent content) {
        String page = PagePath.ADMIN_ACCOUNT_EDITOR;

        Optional<String> findAccountId = content.findRequestParameter(AttributeName.ACCOUNT_ID);
        if(findAccountId.isPresent()) {
            long accountId = Long.parseLong(findAccountId.get());

            try {
                Optional<Account> findAccount = accountService.findAccountById(accountId);
                if(findAccount.isPresent()) {
                    Account account = findAccount.get();
                    content.putRequestAttribute(AttributeName.ACCOUNT_FOR_CHANGE, account);
                }
            } catch (ServiceException e) {
                LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_EDITOR_FROM_ADMIN, e);
                page = PagePath.INFO;
            }
        }

        return page;
    }
}
