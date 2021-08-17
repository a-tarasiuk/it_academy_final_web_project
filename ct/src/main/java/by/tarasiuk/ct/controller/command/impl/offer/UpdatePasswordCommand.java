package by.tarasiuk.ct.controller.command.impl.offer;

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

/**
 * Update account password command.
 */
public class UpdatePasswordCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();

    /**
     * Checking for the validity of passwords.
     * If successful - checking if the old password matches the password of the account in the database.
     * If successful, the password is updated.
     * Otherwise, return to the page and display the message.
     *
     * @param content   Request data content.
     * @return          Page path.
     * @see             by.tarasiuk.ct.controller.RequestContent
     */
    @Override
    public String execute(RequestContent content) {
        String page = PagePath.ACCOUNT_PASSWORD;

        Map<String, String> passwords = content.getRequestParameters();

        String oldPassword = passwords.get(AttributeName.ACCOUNT_OLD_PASSWORD);
        String newPassword = passwords.get(AttributeName.ACCOUNT_NEW_PASSWORD);
        String confirmNewPassword = passwords.get(AttributeName.ACCOUNT_CONFIRM_NEW_PASSWORD);

        if(!accountService.validatePasswordsForChange(oldPassword, newPassword, confirmNewPassword)) {
            content.putRequestAttribute(AttributeName.INVALID_DATA, true);
            LOGGER.debug("Passwords do not meet validity requirements.");
        } else {
            Optional<Object> findAccount = content.findSessionAttribute(AttributeName.ACCOUNT);

            try {
                if(findAccount.isPresent()) {
                    Account account = (Account) findAccount.get();
                    long accountId = account.getId();

                    if(!accountService.isAccountPasswordByAccountId(accountId, oldPassword)) {
                        content.putRequestAttribute(AttributeName.WRONG_PASSWORD, true);
                        LOGGER.debug("Wrong password for account with ID {}.", accountId);
                    } else {
                        boolean result = accountService.changeAccountPasswordByAccountId(accountId, newPassword);

                        if(!result) {
                            content.putRequestAttribute(AttributeName.MESSAGE_QUERY_ERROR, true);
                        } else {
                            content.putRequestAttribute(AttributeName.SUCCESSFUL_OPERATION, true);
                            LOGGER.info("Password successfully was changed for account with ID {}.", accountId);
                        }
                    }
                }
            } catch (ServiceException e) {
                LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_OFFERS, e);
                page = PagePath.ACCOUNT_PASSWORD;
            }
        }

        return page;
    }
}
