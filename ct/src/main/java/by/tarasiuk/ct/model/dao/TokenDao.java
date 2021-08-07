package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.entity.impl.Token;
import by.tarasiuk.ct.exception.DaoException;

public interface TokenDao {
    boolean createToken(Token token) throws DaoException;
    boolean updateToken(Token token) throws DaoException;
}
