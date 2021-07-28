package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface AccountService {
    void sendActivationEmail(String locale, String firstName, String emailTo, String token) throws ServiceException;
    boolean validateSignInData(String login, String password) throws ServiceException;
    boolean validateSignUpData(Map<String, String> signUpData) throws ServiceException;
    boolean createNewAccount(Map<String, String> signUpData) throws ServiceException;
    Optional<Account> signIn(String login, String password) throws ServiceException;
    Optional<Account> findAccountByEmail(String email) throws ServiceException;
    Optional<Account> findAccountByLogin(String login) throws ServiceException;
}
