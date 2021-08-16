package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Token;
import by.tarasiuk.ct.exception.ServiceException;
import java.util.Optional;

/**
 * Service that works with token data.
 *
 * @see by.tarasiuk.ct.model.entity.impl.Token
 * @see by.tarasiuk.ct.model.dao.TokenDao
 * @see by.tarasiuk.ct.model.dao.impl.TokenDaoImpl
 */
public interface TokenService {
    /**
     * Set token status.
     *
     * @param token                 Token entity.
     * @param status                Token status.
     * @throws ServiceException     Default exception of service layer.
     */
    void changeTokenStatus(Token token, Token.Status status) throws ServiceException;

    /**
     * Create token for account with ID.
     *
     * @param accountId             Account ID.
     * @return                      <code>true</code> if the token has been created.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean createToken(long accountId) throws ServiceException;

    /**
     * Update token entity.
     *
     * @param token                 Token entity.
     * @return                      <code>true</code> if the token has been updated.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean updateToken(Token token) throws ServiceException;

    /**
     * Find token by Account entity.
     *
     * @param account               Account entity.
     * @return                      Optional of token.
     * @throws ServiceException     Default exception of service layer.
     */
    Optional<Token> findTokenByAccount(Account account) throws ServiceException;
}
