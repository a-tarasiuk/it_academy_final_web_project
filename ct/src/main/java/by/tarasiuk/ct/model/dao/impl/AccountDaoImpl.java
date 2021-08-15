package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.dao.AccountDao;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.model.dao.builder.AccountDaoBuilder;
import by.tarasiuk.ct.model.entity.impl.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDaoImpl extends BaseDao<Account> implements AccountDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AccountDaoImpl instance = new AccountDaoImpl();
    
    private static final String SQL_PROCEDURE_CREATE_ACCOUNT = "{CALL create_account (?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String SQL_PROCEDURE_FIND_ACCOUNT_BY_ID = "{CALL find_account_by_id (?)}";
    private static final String SQL_PROCEDURE_FIND_ACCOUNT_BY_LOGIN = "{CALL find_account_by_login (?)}";
    private static final String SQL_PROCEDURE_FIND_PASSWORD_BY_LOGIN = "{CALL find_password_by_login (?)}";
    private static final String SQL_PROCEDURE_FIND_PASSWORD_BY_ACCOUNT_ID = "{CALL find_password_by_account_id (?)}";
    private static final String SQL_PROCEDURE_SET_PASSWORD_BY_ACCOUNT_ID = "{CALL set_password_by_account_id (?, ?)}";
    private static final String SQL_PROCEDURE_FIND_ACCOUNT_BY_EMAIL = "{CALL find_account_by_email (?)}";
    private static final String SQL_PROCEDURE_UPDATE_ACCOUNT = "{CALL update_account (?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String SQL_PROCEDURE_FIND_ALL_ACCOUNTS = "{CALL find_all_accounts ()}";
    private static final String SQL_PROCEDURE_UPDATE_ACCOUNT_STATUS_BY_ID = "{CALL update_account_status_by_id (?, ?)}";

    private AccountDaoImpl() {
    }

    public static AccountDaoImpl getInstance() {
        return instance;
    }

    private static final class IndexFind {
        private static final int ACCOUNT_ID = 1;
        private static final int LOGIN = 1;
        private static final int EMAIL = 1;
    }

    private static final class CreateIndex {
        private static final int FIRST_NAME = 1;
        private static final int LAST_NAME = 2;
        private static final int LOGIN = 3;
        private static final int EMAIL = 4;
        private static final int REGISTRATION_DATE = 5;
        private static final int PASSWORD_CODED = 6;
        private static final int ROLE = 7;
        private static final int STATUS = 8;
    }

    private static final class UpdateIndex {
        private static final int ACCOUNT_ID = 1;
        private static final int FIRST_NAME = 2;
        private static final int ENCODING_PASSWORD = 2;
        private static final int LAST_NAME = 3;
        private static final int LOGIN = 4;
        private static final int EMAIL = 5;
        private static final int REGISTRATION_DATE = 6;
        private static final int ROLE = 7;
        private static final int STATUS = 8;
    }

    private static final class UpdateStatusIndex {
        private static final int ACCOUNT_ID = 1;
        private static final int STATUS = 2;
    }

    @Override
    public Optional<Account> findEntityById(long id) throws DaoException {
        Optional<Account> findAccount;

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_ACCOUNT_BY_ID)) {
            statement.setLong(IndexFind.ACCOUNT_ID, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Account account = AccountDaoBuilder.buildAccount(resultSet);
                    findAccount = Optional.of(account);
                } else {
                    findAccount = Optional.empty();
                }
            }

            return findAccount;
        } catch (SQLException e) {
            LOGGER.error("Error when performing account search by id '{}'.", id, e);
            throw new DaoException("Error when performing account search by id '" + id + "'.", e);
        }
    }

    @Override
    public boolean createEntity(Account entity) throws DaoException {
        return false;
    }

    @Override
    public List<Account> findAll() throws DaoException {
        List<Account> accountList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_ALL_ACCOUNTS);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                Account account = AccountDaoBuilder.buildAccount(result);
                accountList.add(account);
            }
        } catch (SQLException e) {
            LOGGER.error("Error when find all accounts.", e);
            throw new DaoException("Error when find all accounts.", e);
        }

        return accountList;
    }

    @Override
    public Optional<Account> findAccountByLogin(String login) throws DaoException {
        Optional<Account> findAccount;

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_ACCOUNT_BY_LOGIN)) {
            statement.setString(IndexFind.LOGIN, login);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Account account = AccountDaoBuilder.buildAccount(result);
                    findAccount = Optional.of(account);
                } else {
                    findAccount = Optional.empty();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error when performing account search by login '{}'.", login, e);
            throw new DaoException("Error when performing account search by login '" + login + "'.", e);
        }

        return findAccount;
    }

    @Override
    public Optional<String> findPasswordByLogin(String login) throws DaoException {
        Optional<String> optionalPassword;

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_PASSWORD_BY_LOGIN)) {
            statement.setString(IndexFind.LOGIN, login);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    String password = result.getString(AttributeName.ACCOUNT_PASSWORD_ENCODED);
                    optionalPassword = Optional.of(password);
                } else {
                    optionalPassword = Optional.empty();
                }
            }

            return optionalPassword;
        } catch (SQLException e) {
            LOGGER.error("Error when performing account search password by login '{}'.", login, e);
            throw new DaoException("Error when performing account search password by login '" + login + "'.", e);
        }
    }

    @Override
    public Optional<String> findPasswordByAccountId(long accountId) throws DaoException {
        Optional<String> optionalPassword;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_PASSWORD_BY_ACCOUNT_ID)) {
            statement.setLong(IndexFind.ACCOUNT_ID, accountId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    String password = result.getString(AttributeName.ACCOUNT_PASSWORD_ENCODED);
                    optionalPassword = Optional.of(password);
                } else {
                    optionalPassword = Optional.empty();
                }
            }

            return optionalPassword;
        } catch (SQLException e) {
            LOGGER.error("Error when performing search password by account id '{}'.", accountId, e);
            throw new DaoException("Error when performing search password by account id '" + accountId + "'.", e);
        }
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) throws DaoException {
        Optional<Account> findAccount;

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_ACCOUNT_BY_EMAIL)) {
            statement.setString(IndexFind.EMAIL, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Account account = AccountDaoBuilder.buildAccount(resultSet);
                    findAccount = Optional.of(account);
                } else {
                    findAccount = Optional.empty();
                }
            }

            return findAccount;
        } catch (SQLException e) {
            LOGGER.error("Error when performing account search by email '{}'.", email, e);
            throw new DaoException("Error when performing account search by email '" + email + "'.", e);
        }
    }

    /**
     * It is generally good practice to release resources as soon as you are finished with them to avoid tying up
     * database resources.
     * @param account
     * @return
     * @throws DaoException
     * @throws SQLException Database access error occurs when trying to close <code>Statement</code> object.
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/sql/Statement.html#close--">About release statement.</a>
     */
    @Override
    public boolean createAccount(Account account, String encodingPassword) throws DaoException {
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        String login = account.getLogin();
        String email = account.getEmail();
        LocalDate registrationDate = account.getRegistrationDate();
        String role = account.getRole().toString();
        String status = account.getStatus().toString();

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_CREATE_ACCOUNT)) {
            statement.setString(CreateIndex.FIRST_NAME, firstName);
            statement.setString(CreateIndex.LAST_NAME, lastName);
            statement.setString(CreateIndex.LOGIN, login);
            statement.setString(CreateIndex.EMAIL, email);
            statement.setString(CreateIndex.REGISTRATION_DATE, registrationDate.toString());
            statement.setString(CreateIndex.PASSWORD_CODED, encodingPassword);
            statement.setString(CreateIndex.ROLE, role);
            statement.setString(CreateIndex.STATUS, status);

            statement.executeUpdate();

            LOGGER.info("Account was successfully created in the database: {}.", account);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Failed to create account '{}' in the database.", account, e);
            throw new DaoException("Failed to create account '" + account + "' in the database.", e);
        }
    }

    @Override
    public boolean updateEntity(Account entity) throws DaoException {
        long accountId = entity.getId();
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();
        String login = entity.getLogin();
        String email = entity.getEmail();
        LocalDate registrationDate = entity.getRegistrationDate();
        String role = entity.getRole().toString();
        String status = entity.getStatus().toString();

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_UPDATE_ACCOUNT)) {
            statement.setLong(UpdateIndex.ACCOUNT_ID, accountId);
            statement.setString(UpdateIndex.FIRST_NAME, firstName);
            statement.setString(UpdateIndex.LAST_NAME, lastName);
            statement.setString(UpdateIndex.LOGIN, login);
            statement.setString(UpdateIndex.EMAIL, email);
            statement.setString(UpdateIndex.REGISTRATION_DATE, registrationDate.toString());
            statement.setString(UpdateIndex.ROLE, role);
            statement.setString(UpdateIndex.STATUS, status);

            statement.executeUpdate();

            LOGGER.info("Account was successfully updated in the database: {}.", entity);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Failed to update account '{}' in the database.", entity, e);
            throw new DaoException("Failed to update account '" + entity + "' in the database.", e);
        }
    }

    @Override
    public boolean updatePasswordByAccountId(long accountId, String encodingPassword) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_SET_PASSWORD_BY_ACCOUNT_ID)) {
            statement.setLong(UpdateIndex.ACCOUNT_ID, accountId);
            statement.setString(UpdateIndex.ENCODING_PASSWORD, encodingPassword);

            statement.executeUpdate();

            LOGGER.info("Password was successfully updated for account with ID '{}' in the database.", accountId);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Failed to update password for account with ID '{}' in the database.", accountId, e);
            throw new DaoException("Failed to update password for account with ID '" + accountId + "' in the database.", e);
        }
    }

    @Override
    public boolean updateStatusByAccountId(long id, Account.Status status) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_UPDATE_ACCOUNT_STATUS_BY_ID)) {
            statement.setLong(UpdateStatusIndex.ACCOUNT_ID, id);
            statement.setString(UpdateStatusIndex.STATUS, status.name());

            statement.executeUpdate();

            LOGGER.info("For account with ID '{}' updated status to '{}' in the database.", id, status);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Failed to update status '{}' for account wih ID '{}' in the database.", status, id, e);
            throw new DaoException("Failed to update status '" + status + "' for account wih ID '" + id+ "' in the database.", e);
        }
    }
}
