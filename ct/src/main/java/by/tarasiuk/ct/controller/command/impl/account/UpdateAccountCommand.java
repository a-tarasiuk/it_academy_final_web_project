package by.tarasiuk.ct.controller.command.impl.account;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.controller.command.AttributeName.INFORMATION_MESSAGE;
import static by.tarasiuk.ct.controller.command.AttributeName.INVALID_DATA;
import static by.tarasiuk.ct.controller.command.AttributeName.SUCCESSFUL_OPERATION;

/**
 * Update account command.
 */
public class UpdateAccountCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();

    /**
     * Validating the account information obtained from the page.
     * In case of successful validation, the information is updated with the issuance of a successful message.
     * @param content   Request data from page.
     * @return
     */
    @Override
    public String execute(RequestContent content) {
        String page = PagePath.ACCOUNT_SETTINGS;
        Map<String, String> personalData = content.getRequestParameters();

        try {
            if(!accountService.validatePersonalAccountData(personalData)) {
                content.putRequestAttribute(INVALID_DATA, true);
                content.putSessionAttribute(SUCCESSFUL_OPERATION, false);
            } else {
                Optional<Object> findAccount = content.findSessionAttribute(ACCOUNT);

                if(findAccount.isPresent()) {
                    Account account = (Account) findAccount.get();
                    long accountId = account.getId();
                    accountService.updatePersonalDataByAccountId(accountId, personalData);
                    Optional<Account> changedAccount = accountService.findAccountById(accountId);

                    if(changedAccount.isPresent()) {
                        account = changedAccount.get();
                        content.putSessionAttribute(ACCOUNT, account);
                        content.putSessionAttribute(SUCCESSFUL_OPERATION, true);
                        content.putRequestAttribute(INVALID_DATA, false);
                        LOGGER.info("Account was successfully update to '{}'.", account);
                    }
                }
            }
        } catch (ServiceException e) {
            LOGGER.warn("Can't update personal data '{}'.", personalData, e);
            content.putRequestAttribute(INFORMATION_MESSAGE, true);
            page = PagePath.ACCOUNT_SETTINGS;   //fixme (сделать перенаправление на страницу ошибок)
        }

        return page;
    }
}
