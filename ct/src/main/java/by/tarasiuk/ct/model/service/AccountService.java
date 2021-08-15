package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountService {
    void updatePersonalDataByAccountId(long id, Map<String, String> personalData) throws ServiceException;

    void changeAccountStatus(Account account, Account.Status status) throws ServiceException;
    void sendActivationEmail(String locale, String firstName, String emailTo, String token);
    boolean validateSignInData(String login, String password) throws ServiceException;
    boolean validateSignUpData(Map<String, String> signUpData) throws ServiceException;

    boolean validateAccountData(Map<String, String> accountData) throws ServiceException;

    boolean validatePersonalAccountData(Map<String, String> accountData) throws ServiceException;
    boolean changeAccountPasswordByAccountId(long accountId, String newPassword) throws ServiceException;
    Optional<Account> signIn(String login, String password) throws ServiceException;
    Optional<Account> findAccountByEmail(String email) throws ServiceException;
    Optional<Account> findAccountByLogin(String login) throws ServiceException;

    boolean banAccountById(long id) throws ServiceException;

    boolean unbanAccountById(long id) throws ServiceException;

    List<Account> findAccountList() throws ServiceException;
}
