package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.entity.Entity;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.manager.AttributeName;
import by.tarasiuk.ct.model.dao.AccountDao;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.util.AccountBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AccountDaoImpl extends BaseDao<Account> implements AccountDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AccountDaoImpl instance = new AccountDaoImpl();
    
    private final String SQL_PROCEDURE_CREATE_ACCOUNT = "{CALL create_account (?, ?, ?, ?, ?, ?, ?, ?)}";
    private final String SQL_PROCEDURE_FIND_ACCOUNT_BY_LOGIN = "{CALL find_account_by_login (?)}";
    private final String SQL_PROCEDURE_FIND_PASSWORD_BY_LOGIN = "{CALL find_password_by_login (?)}";
    private final String SQL_PROCEDURE_FIND_ACCOUNT_BY_EMAIL = "{CALL find_account_by_email (?)}";

    private AccountDaoImpl() {
    }

    public static AccountDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Account> findEntityById(int id) throws DaoException {
        return Optional.empty(); // todo
    }

    @Override
    public List<Entity> findAll() throws DaoException {
        return null;
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    @Override
    public Optional<Account> findAccountByLogin(String login) throws DaoException {
        Connection connection = connectionPool.getConnection();
        Optional<Account> findAccount;

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_ACCOUNT_BY_LOGIN)) {
            statement.setString(IndexFind.LOGIN, login);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Account account = AccountBuilder.buildAccount(result);
                    findAccount = Optional.of(account);
                } else {
                    findAccount = Optional.empty();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error when performing account search by login '{}'.", login, e);
            throw new DaoException("Error when performing account search by login '" + login + "'.", e);
        } finally {
            closeConnection(connection);
        }

        return findAccount;
    }

    @Override
    public Optional<String> findPasswordByLogin(String login) throws DaoException {
        Connection connection = connectionPool.getConnection();
        Optional<String> optionalPassword;

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_PASSWORD_BY_LOGIN)) {
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
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) throws DaoException {
        Connection connection = connectionPool.getConnection();
        Optional<Account> findAccount;

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_ACCOUNT_BY_EMAIL)) {
            statement.setString(IndexFind.EMAIL, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Account account = AccountBuilder.buildAccount(resultSet);
                    findAccount = Optional.of(account);
                } else {
                    findAccount = Optional.empty();
                }
            }

            return findAccount;
        } catch (SQLException e) {
            LOGGER.error("Error when performing account search by email '{}'.", email, e);
            throw new DaoException("Error when performing account search by email '" + email + "'.", e);
        } finally {
            closeConnection(connection);
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
        Connection connection = connectionPool.getConnection();

        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        String login = account.getLogin();
        String email = account.getEmail();
        LocalDate registrationDate = account.getRegistrationDate();
        String role = account.getRole().toString();
        String status = account.getStatus().toString();

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_CREATE_ACCOUNT)) {
            statement.setString(IndexCreate.FIRST_NAME, firstName);
            statement.setString(IndexCreate.LAST_NAME, lastName);
            statement.setString(IndexCreate.LOGIN, login);
            statement.setString(IndexCreate.EMAIL, email);
            statement.setString(IndexCreate.REGISTRATION_DATE, registrationDate.toString());
            statement.setString(IndexCreate.PASSWORD_CODED, encodingPassword);
            statement.setString(IndexCreate.ROLE, role);
            statement.setString(IndexCreate.STATUS, status);

            statement.executeUpdate();

            LOGGER.info("Account was successfully created in the database: {}.", account);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Failed to create account '{}' in the database.", account);
            throw new DaoException("Failed to create account '" + account + "' in the database.", e);
        } finally {
            closeConnection(connection);
        }
    }

    private static final class IndexFind {
        private static final int LOGIN = 1;
        private static final int EMAIL = 1;
    }

    private static final class IndexCreate {
        private static final int FIRST_NAME = 1;
        private static final int LAST_NAME = 2;
        private static final int LOGIN = 3;
        private static final int EMAIL = 4;
        private static final int REGISTRATION_DATE = 5;
        private static final int PASSWORD_CODED = 6;
        private static final int ROLE = 7;
        private static final int STATUS = 8;
    }
}
