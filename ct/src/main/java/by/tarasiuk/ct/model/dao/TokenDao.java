package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.entity.impl.Token;
import by.tarasiuk.ct.exception.DaoException;

import java.util.Optional;

public interface TokenDao {
    boolean createToken(Token token) throws DaoException;

    Optional<Token> findTokenByAccountId(long accountId) throws DaoException;

    boolean updateToken(Token token) throws DaoException;
}
