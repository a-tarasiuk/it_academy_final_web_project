package by.tarasiuk.ct.util;

import by.tarasiuk.ct.entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import static by.tarasiuk.ct.manager.RequestAttribute.ACCOUNT_EMAIL;
import static by.tarasiuk.ct.manager.RequestAttribute.ACCOUNT_FIRST_NAME;
import static by.tarasiuk.ct.manager.RequestAttribute.ACCOUNT_ID;
import static by.tarasiuk.ct.manager.RequestAttribute.ACCOUNT_LAST_NAME;
import static by.tarasiuk.ct.manager.RequestAttribute.ACCOUNT_LOGIN;
import static by.tarasiuk.ct.manager.RequestAttribute.ACCOUNT_REGISTRATION_DATE;
import static by.tarasiuk.ct.manager.RequestAttribute.ACCOUNT_ROLE;
import static by.tarasiuk.ct.manager.RequestAttribute.ACCOUNT_STATUS;

public class AccountBuilder {
    private static final Logger LOGGER = LogManager.getLogger();

    private AccountBuilder() {
    }

    public static Account buildAccount(Map<String, String> accountData) {
        String firstName = accountData.get(ACCOUNT_FIRST_NAME);
        String lastName = accountData.get(ACCOUNT_LAST_NAME);
        String login = accountData.get(ACCOUNT_LOGIN);
        String email = accountData.get(ACCOUNT_EMAIL);
        LocalDate registrationDate = LocalDate.now();
        Account.Role role = Account.Role.MANAGER;
        Account.Status status = Account.Status.NOT_ACTIVATED;

        Account account = new Account();
        return getAccount(firstName, lastName, login, email, registrationDate, role, status, account);
    }

    public static Account buildAccount(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ACCOUNT_ID);
        String firstName = resultSet.getString(ACCOUNT_FIRST_NAME);
        String lastName = resultSet.getString(ACCOUNT_LAST_NAME);
        String login = resultSet.getString(ACCOUNT_LOGIN);
        String email = resultSet.getString(ACCOUNT_EMAIL);
        LocalDate registrationDate = resultSet.getDate(ACCOUNT_REGISTRATION_DATE).toLocalDate();
        Account.Role role = Account.Role.valueOf(resultSet.getString(ACCOUNT_ROLE));
        Account.Status status = Account.Status.valueOf(resultSet.getString(ACCOUNT_STATUS));

        Account account = new Account();
        account.setId(id);
        return getAccount(firstName, lastName, login, email, registrationDate, role, status, account);
    }

    private static Account getAccount(String firstName, String lastName, String login, String email, LocalDate registrationDate, Account.Role role, Account.Status status, Account account) {
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setLogin(login);
        account.setEmail(email);
        account.setRegistrationDate(registrationDate);
        account.setRole(role);
        account.setStatus(status);

        LOGGER.info("Account with login '{}' successfully build: {}.", login, account);
        return account;
    }
}
