package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.entity.impl.Account;
import by.tarasiuk.ct.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface AccountService {
    void sendActivationEmail(String locale, String firstName, String emailTo, String token);
    boolean validateSignInData(String login, String password) throws ServiceException;
    boolean validateSignUpData(Map<String, String> signUpData) throws ServiceException;
    boolean signUp(Map<String, String> signUpData) throws ServiceException;
    Optional<Account> signIn(String login, String password) throws ServiceException;
    Optional<Account> findAccountByEmail(String email) throws ServiceException;
    Optional<Account> findAccountByLogin(String login) throws ServiceException;
}
