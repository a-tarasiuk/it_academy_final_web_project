package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Token;
import by.tarasiuk.ct.exception.ServiceException;

import java.util.Optional;

public interface TokenService {
    void changeTokenStatus(Token token, Token.Status status) throws ServiceException;
    boolean createToken(long accountId) throws ServiceException;
    boolean updateToken(Token token) throws ServiceException;
    Optional<Token> findTokenByAccount(Account account) throws ServiceException;
}
