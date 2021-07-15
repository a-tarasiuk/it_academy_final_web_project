package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface AccountService {
    Optional<Account> signIn(String login, String password) throws ServiceException;
    boolean signUp(Map<String, String> accountData, String password, String confirmPassword) throws ServiceException;
}
