package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface AccountService {
    boolean validateSignInData(String login, String password);

    boolean validateSignUpData(Map<String, String> signUpData);

    Optional<Account> signIn(String login, String password) throws ServiceException;

    boolean createNewAccount(Map<String, String> signUpData) throws ServiceException;

    boolean isExistLogin(String login) throws ServiceException;

    boolean isExistEmail(String email) throws ServiceException;

    void sendActivationEmail(String locale, String firstName, String emailTo);
}
