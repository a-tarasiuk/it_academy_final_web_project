package by.tarasiuk.ct.model.service.impl;

import by.tarasiuk.ct.entity.impl.Account;
import by.tarasiuk.ct.entity.impl.Token;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.dao.DaoProvider;
import by.tarasiuk.ct.model.dao.impl.TokenDaoImpl;
import by.tarasiuk.ct.model.service.TokenService;
import by.tarasiuk.ct.util.TokenGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class TokenServiceImpl implements TokenService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TokenServiceImpl instance = new TokenServiceImpl();
    private static final TokenDaoImpl tokenDao = DaoProvider.getTokenDao();

    private TokenServiceImpl() {
    }

    public static TokenServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createToken(long accountId) throws ServiceException {
        String tokenNumber = TokenGenerator.generate();

        Token token = new Token();
        token.setNumber(tokenNumber);
        token.setAccountId(accountId);
        token.setStatus(Token.Status.UNCONFIRMED);

        try {
            return tokenDao.createToken(token);
        } catch (DaoException e) {
            LOGGER.error("Error while generating token for account with id '{}'.", accountId, e);
            throw new ServiceException("Error while generating token for account with id '" + accountId +"'.", e);
        }
    }

    @Override
    public Optional<Token> findTokenByAccount(Account account) throws ServiceException {
        Optional<Token> optionalToken;
        long accountId = account.getId();

        try {
            optionalToken = tokenDao.findTokenByAccountId(accountId);
            LOGGER.info(optionalToken.isPresent()
                    ? "Successfully was find token for account '{}'."
                    : "Token with account id '{}' not found in the database.", account);
        } catch (DaoException e) {
            LOGGER.error("Error when searching an token for account '{}'.", account, e);
            throw new ServiceException("Error when searching an token for account '" + account + "'.", e);
        }

        return optionalToken;
    }

    public void changeTokenStatus(Token token, Token.Status status) throws ServiceException {
        token.setStatus(status);
        updateToken(token);
    }

    public boolean updateToken(Token token) throws ServiceException {
        boolean result;
        try {
            result = tokenDao.updateToken(token);
            LOGGER.info(result
                    ? "Successfully updateEntity token '{}'."
                    : "Token '{}' not found in the database.", token);
        } catch (DaoException e) {
            LOGGER.error("Error when updateEntity token '{}'.", token, e);
            throw new ServiceException("Error when updateEntity token '" + token + "'.", e);
        }

        return result;
    }
}
