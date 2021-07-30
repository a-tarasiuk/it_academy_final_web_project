package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.entity.impl.Account;
import by.tarasiuk.ct.entity.impl.Token;
import by.tarasiuk.ct.exception.ServiceException;

import java.util.Optional;

public interface TokenService {
    boolean createToken(long accountId) throws ServiceException;
    Optional<Token> findTokenByAccount(Account account) throws ServiceException;
}
