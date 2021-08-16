package by.tarasiuk.ct.model.dao.builder;

import by.tarasiuk.ct.model.entity.impl.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_EMAIL;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_FIRST_NAME;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_ID;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_LAST_NAME;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_LOGIN;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_REGISTRATION_DATE;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_ROLE;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_STATUS;

/**
 * Build account object from <code>ResultSet</code>.
 * @see java.sql.ResultSet
 */
public class AccountDaoBuilder {
    private static final Logger LOGGER = LogManager.getLogger();

    private AccountDaoBuilder() {
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
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setLogin(login);
        account.setEmail(email);
        account.setRegistrationDate(registrationDate);
        account.setRole(role);
        account.setStatus(status);

        LOGGER.info("Successfully build account from result set: {}.", account);
        return account;
    }
}
