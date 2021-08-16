package by.tarasiuk.ct.model.service.builder;

import by.tarasiuk.ct.model.entity.impl.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.util.Map;

import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_EMAIL;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_FIRST_NAME;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_LAST_NAME;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_LOGIN;

/**
 * Account builder for service layer.
 */
public class AccountServiceBuilder {
    private static final Logger LOGGER = LogManager.getLogger();

    private AccountServiceBuilder() {
    }

    /**
     * Create account from account data with role.
     *
     * @param accountData   Account data.
     * @param role          Account role.
     * @return              Account entity.
     * @see                 by.tarasiuk.ct.model.entity.impl.Account
     */
    public static Account buildAccount(Map<String, String> accountData, Account.Role role) {
        String firstName = accountData.get(ACCOUNT_FIRST_NAME);
        String lastName = accountData.get(ACCOUNT_LAST_NAME);
        String login = accountData.get(ACCOUNT_LOGIN);
        String email = accountData.get(ACCOUNT_EMAIL);
        LocalDate registrationDate = LocalDate.now();
        Account.Status status = Account.Status.NOT_ACTIVATED;

        Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setLogin(login);
        account.setEmail(email);
        account.setRegistrationDate(registrationDate);
        account.setRole(role);
        account.setStatus(status);

        LOGGER.debug("Account '{}' successfully build.", account);
        return account;
    }
}
