package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.entity.Token;
import by.tarasiuk.ct.exception.DaoException;

public interface TokenDao {
    boolean createToken(Token token) throws DaoException;
    boolean updateStatusById(long accountId, Token.Status status) throws DaoException;
}
