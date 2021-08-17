package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.entity.impl.Token;
import by.tarasiuk.ct.exception.DaoException;
import java.util.Optional;

/**
 * Interface for operations with the data of the taken,
 * which are contained in the in the database table <code>tokens</code>.
 */
public interface TokenDao {

    /**
     * Find token entity by account ID.
     *
     * @param accountId         Account ID.
     * @return                  Optional of token entity.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or an argument is supplied to this method.
     */
    Optional<Token> findTokenByAccountId(long accountId) throws DaoException;
}
