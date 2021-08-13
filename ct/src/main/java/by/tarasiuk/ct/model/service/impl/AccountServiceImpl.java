package by.tarasiuk.ct.model.service.impl;

import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.dao.DaoProvider;
import by.tarasiuk.ct.model.dao.impl.AccountDaoImpl;
import by.tarasiuk.ct.model.service.AccountService;
import by.tarasiuk.ct.model.dao.builder.AccountDaoBuilder;
import by.tarasiuk.ct.util.AccountValidator;
import by.tarasiuk.ct.util.BouncyCastle;
import by.tarasiuk.ct.util.EmailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT_PASSWORD;

public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AccountServiceImpl instance = new AccountServiceImpl();
    private static final AccountDaoImpl accountDao = DaoProvider.getAccountDao();

    private AccountServiceImpl() {
    }

    public static AccountServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateSignInData(String login, String password) throws ServiceException {
        return AccountValidator.isValidSignInData(login, password);
    }

    @Override
    public boolean validateSignUpData(Map<String, String> signUpData) throws ServiceException {
        return AccountValidator.isValidSignUpData(signUpData);
    }

    @Override
    public Optional<Account> signIn(String login, String password) throws ServiceException {
        Optional<Account> optionalAccount;
        try {
            optionalAccount = accountDao.findAccountByLogin(login);

            if (optionalAccount.isPresent()) {
                LOGGER.info("Account with login '{}' was found in the database.", login);
                Optional<String> optionalPassword = accountDao.findPasswordByLogin(login);

                if(optionalPassword.isPresent()) {
                    String encodedPassword = BouncyCastle.encoding(password);
                    String findPassword = optionalPassword.get();

                    if(!encodedPassword.equals(findPassword)) {
                        LOGGER.info("Incorrect password for login '{}'.", login);
                        optionalAccount = Optional.empty();
                    }
                }
            } else {
                optionalAccount = Optional.empty();
                LOGGER.info("Account with login '{}' not found in the database.", login);
            }
        } catch (DaoException e) {
            LOGGER.error("Sign in error for login '{}'.", login, e);
            throw new ServiceException("Sign in error for login '" + login + "'.", e);
        }

        return optionalAccount;
    }

    @Override
    public boolean createAccount(Map<String, String> signUpData) throws ServiceException {
        Account account = AccountDaoBuilder.buildAccount(signUpData);
        String password = signUpData.get(ACCOUNT_PASSWORD);
        String encodingPassword = BouncyCastle.encoding(password);

        try {
            return accountDao.createAccount(account, encodingPassword);
        } catch (DaoException e) {
            LOGGER.error("Account creation error '{}'.", signUpData, e);
            throw new ServiceException("Account creation error: " + signUpData, e);
        }
    }

    @Override
    public void sendActivationEmail(String locale, String firstName, String emailTo, String token) {
        EmailSender.sendActivationEmail(locale, firstName, emailTo, token);
    }

    public Optional<Account> findAccountById(long accountId) throws ServiceException {
        Optional<Account> optionalAccount;

        try {
            optionalAccount = accountDao.findEntityById(accountId);
            LOGGER.info(optionalAccount.isPresent()
                    ? "Successfully was find account by id '{}'."
                    : "Account with id '{}' not found in the database.", accountId);
        } catch (DaoException e) {
            LOGGER.error("Error when searching for an account by id '{}'.", accountId, e);
            throw new ServiceException("Error when searching for an account by id '" + accountId + "'.", e);
        }

        return optionalAccount;
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) throws ServiceException {
        Optional<Account> optionalAccount;

        try {
            optionalAccount = accountDao.findAccountByEmail(email);
            LOGGER.info(optionalAccount.isPresent()
                    ? "Successfully was find account by email '{}'."
                    : "Account with email '{}' not found in the database.", email);
        } catch (DaoException e) {
            LOGGER.error("Error when searching for an account by mail '{}'.", email, e);
            throw new ServiceException("Error when searching for an account by mail '" + email + "'.", e);
        }

        return optionalAccount;
    }

    @Override
    public Optional<Account> findAccountByLogin(String login) throws ServiceException {
        Optional<Account> optionalAccount;

        try {
            optionalAccount = accountDao.findAccountByLogin(login);
            LOGGER.info(optionalAccount.isPresent()
                    ? "Successfully was find account by login '{}': {}."
                    : "Account with login '{}' not found in the database.", login);
        } catch (DaoException e) {
            LOGGER.error("Error when searching for an account by login '{}'.", login, e);
            throw new ServiceException("Error when searching for an account by login '" + login + "'.", e);
        }

        return optionalAccount;
    }

    @Override
    public void changeAccountStatus(Account account, Account.Status status) throws ServiceException {
        account.setStatus(status);
        updateAccount(account);
    }

    @Override
    public void updateAccount(Account account) throws ServiceException {
        boolean result;

        try {
            result = accountDao.updateAccount(account);
            LOGGER.info(result
                    ? "Successfully was updateEntity account '{}' in the database."
                    : "Account doesn't updateEntity '{}' in the database.", account);
        } catch (DaoException e) {
            LOGGER.error("Error when updating account '{}'.", account, e);
            throw new ServiceException("Error when updating account '" + account + "'.", e);
        }
    }
}
