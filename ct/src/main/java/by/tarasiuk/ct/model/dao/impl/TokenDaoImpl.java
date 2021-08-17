package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.model.dao.TokenDao;
import by.tarasiuk.ct.model.entity.impl.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static by.tarasiuk.ct.model.dao.ColumnLabel.TOKEN_ID;
import static by.tarasiuk.ct.model.dao.ColumnLabel.TOKEN_NUMBER;
import static by.tarasiuk.ct.model.dao.ColumnLabel.TOKEN_STATUS;

public class TokenDaoImpl extends BaseDao<Token> implements TokenDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TokenDaoImpl instance = new TokenDaoImpl();

    private static final String SQL_PROCEDURE_CREATE_TOKEN = "{CALL create_token (?, ?, ?)}";
    private static final String SQL_PROCEDURE_UPDATE_TOKEN = "{CALL update_token (?, ?, ?, ?)}";
    private static final String SQL_PROCEDURE_FIND_TOKEN_BY_ACCOUNT_ID = "{CALL find_token_by_account_id (?)}";

    private TokenDaoImpl() {
    }

    public static TokenDaoImpl getInstance() {
        return instance;
    }

    private static final class IndexUpdate {
        private static final int TOKEN_ID = 1;
        private static final int ACCOUNT_ID = 2;
        private static final int TOKEN_NUMBER = 3;
        private static final int TOKEN_STATUS = 4;
    }

    private static final class IndexCreate {
        private static final int ACCOUNT_ID = 1;
        private static final int TOKEN_NUMBER = 2;
        private static final int TOKEN_STATUS = 3;
    }

    @Override
    public boolean createToken(Token token) throws DaoException {
        boolean result;

        long accountId = token.getAccountId();
        String number = token.getNumber();
        String status = token.getStatus().toString();

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_CREATE_TOKEN)) {
            statement.setLong(IndexCreate.ACCOUNT_ID, accountId);
            statement.setString(IndexCreate.TOKEN_NUMBER, number);
            statement.setString(IndexCreate.TOKEN_STATUS, status);

            int rowCount = statement.executeUpdate();
            result = rowCount == 1;

            LOGGER.info(result ? "Token was successfully created in the database: {}."
                    : "Failed to create token '{}'.", token);
        } catch (SQLException e) {
            LOGGER.error("Failed to create token in the database: {}.", token, e);
            throw new DaoException("Failed to create token in the database: " + token + ".", e);
        }

        return result;
    }

    @Override
    public Optional<Token> findTokenByAccountId(long accountId) throws DaoException {
        Optional<Token> optionalToken = Optional.empty();

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_TOKEN_BY_ACCOUNT_ID)) {
                statement.setLong(IndexCreate.ACCOUNT_ID, accountId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        long tokenId = resultSet.getLong(TOKEN_ID);
                        String number = resultSet.getString(TOKEN_NUMBER);
                        String status = resultSet.getString(TOKEN_STATUS);

                        Token token = new Token();
                        token.setId(tokenId);
                        token.setAccountId(accountId);
                        token.setNumber(number);
                        token.setStatus(Token.Status.valueOf(status));

                        optionalToken = Optional.of(token);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find token by account id '{}' in the database.", accountId, e);
            throw new DaoException("Failed to find token by account id '" + accountId + "' in the database.", e);
        }

        return optionalToken;
    }

    @Override
    public boolean updateToken(Token token) throws DaoException {
        boolean result;

        long tokenId = token.getId();
        long accountId = token.getAccountId();
        String tokenNumber = token.getNumber();
        String tokenStatus = token.getStatus().name();

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_UPDATE_TOKEN)) {
            statement.setLong(IndexUpdate.TOKEN_ID, tokenId);
            statement.setLong(IndexUpdate.ACCOUNT_ID, accountId);
            statement.setString(IndexUpdate.TOKEN_NUMBER, tokenNumber);
            statement.setString(IndexUpdate.TOKEN_STATUS, tokenStatus);

            int rowCount = statement.executeUpdate();
            result = rowCount == 1;

            LOGGER.info(result ? "Token was successfully updated in the database: {}."
                    : "Failed to update token '{}'.", token);
        } catch (SQLException e) {
            LOGGER.error("Failed to updateEntity token '{}' in the database.", token, e);
            throw new DaoException("Failed to updateEntity token '" + token + "' in the database.", e);
        }

        return result;
    }

    @Override
    public boolean createEntity(Token entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Token> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateEntity(Token entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Token> findEntityById(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
