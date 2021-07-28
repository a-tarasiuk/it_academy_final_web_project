package by.tarasiuk.ct.model.service.impl;

import by.tarasiuk.ct.entity.Token;
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
    private static final TokenDaoImpl tokenDao = DaoProvider.getInstance().getTokenDao();

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
            LOGGER.error("Error while generating token for account with id '{}'.", accountId);
            throw new ServiceException("Error while generating token for account with id '" + accountId +"'.", e);
        }
    }

    @Override
    public Optional<Token> findTokenByAccountId(long accountId) throws ServiceException {
        Optional<Token> optionalToken;

        try {
            optionalToken = tokenDao.findTokenByAccountId(accountId);
            LOGGER.info(optionalToken.isPresent()
                    ? "Successfully was find token by account id '{}'."
                    : "Token with account id '{}' not found in the database.", accountId);
        } catch (DaoException e) {
            LOGGER.error("Error when searching for an token by account id '{}'.", accountId);
            throw new ServiceException("Error when searching for an token by account id '" + accountId + "'.", e);
        }

        return optionalToken;
    }

    @Override
    public void confirmTokenById(long tokenId) throws ServiceException {
        boolean result;
        Token.Status status = Token.Status.CONFIRMED;
        try {
            result = tokenDao.updateStatusById(tokenId, status);
            LOGGER.info(result
                    ? "Successfully confirm token by token id '{}'."
                    : "Token with id '{}' not found in the database.", tokenId);
        } catch (DaoException e) {
            LOGGER.error("Error when confirm token status by token id '{}'.", tokenId);
            throw new ServiceException("Error when confirm token status by token id '" + tokenId + "'.", e);
        }
    }
}
