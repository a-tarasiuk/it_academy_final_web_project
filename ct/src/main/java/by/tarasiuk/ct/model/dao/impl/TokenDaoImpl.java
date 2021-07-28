package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.entity.Entity;
import by.tarasiuk.ct.entity.Token;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.model.dao.TokenDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static by.tarasiuk.ct.manager.ColumnLabel.*;

public class TokenDaoImpl extends BaseDao<Account> implements TokenDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TokenDaoImpl instance = new TokenDaoImpl();

    private final String SQL_PROCEDURE_CREATE_TOKEN = "{CALL create_token (?, ?, ?)}";
    private final String SQL_PROCEDURE_FIND_TOKEN_BY_ACCOUNT_ID = "{CALL find_token_by_account_id (?)}";
    private final String SQL_PROCEDURE_UPDATE_TOKEN_STATUS_BY_ID = "{CALL update_token_status_by_id (?)}";

    private TokenDaoImpl() {
    }

    public static TokenDaoImpl getInstance() {
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
    public boolean createToken(Token token) throws DaoException {
        Connection connection = connectionPool.getConnection();

        long accountId = token.getAccountId();
        String number = token.getNumber();
        String status = token.getStatus().toString();

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_CREATE_TOKEN)) {
            statement.setLong(IndexCreate.ACCOUNT_ID, accountId);
            statement.setString(IndexCreate.TOKEN_NUMBER, number);
            statement.setString(IndexCreate.TOKEN_STATUS, status);

            statement.executeUpdate();

            LOGGER.info("Token was successfully created in the database: {}.", token);
            return true;    //fixme -> statement.executeUpdate(); (см. выше).
        } catch (SQLException e) {
            LOGGER.error("Failed to create token in the database: {}.", token);
            throw new DaoException("Failed to create token in the database: " + token + ".", e);
        } finally {
            closeConnection(connection);
        }
    }

    public Optional<Token> findTokenByAccountId(long accountId) throws DaoException {
        Connection connection = connectionPool.getConnection();
        Optional<Token> optionalToken;

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_TOKEN_BY_ACCOUNT_ID)) {
            statement.setLong(IndexCreate.ACCOUNT_ID, accountId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long tokenId = resultSet.getLong(TOKEN_ID);
                    String number = resultSet.getString(TOKEN_NUMBER);
                    String status = resultSet.getString(TOKEN_STATUS);

                    Token token = new Token();
                    token.setId(tokenId);
                    token.setNumber(number);
                    token.setStatus(Token.Status.valueOf(status));

                    optionalToken = Optional.of(token);
                } else {
                    optionalToken = Optional.empty();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find token by account id '{}' in the database.", accountId);
            throw new DaoException("Failed to find token by account id '" + accountId + "' in the database.", e);
        } finally {
            closeConnection(connection);
        }

        return optionalToken;
    }

    @Override
    public boolean updateStatusById(long tokenId, Token.Status status) throws DaoException {
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_UPDATE_TOKEN_STATUS_BY_ID)) {
            statement.setLong(IndexUpdate.TOKEN_ID, tokenId);

            return statement.execute();
        } catch (SQLException e) {
            LOGGER.error("Failed to update token to status '{}' by token id '{}' in the database.", status, tokenId);
            throw new DaoException("Failed to update token to status '" + status + "' by token id '" + tokenId + "' in the database.", e);
        } finally {
            closeConnection(connection);
        }
    }

    private static final class IndexUpdate {
        private static final int TOKEN_ID = 1;
    }

    private static final class IndexCreate {
        private static final int ACCOUNT_ID = 1;
        private static final int TOKEN_NUMBER = 2;
        private static final int TOKEN_STATUS = 3;
    }
}