package by.tarasiuk.ct.model.service.impl;

import by.tarasiuk.ct.command.CommandType;
import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.dao.DaoProvider;
import by.tarasiuk.ct.model.dao.impl.AccountDaoImpl;
import by.tarasiuk.ct.model.service.AccountService;
import by.tarasiuk.ct.util.AccountBuilder;
import by.tarasiuk.ct.util.AccountValidator;
import by.tarasiuk.ct.util.BouncyCastle;
import by.tarasiuk.ct.util.EmailSender;
import by.tarasiuk.ct.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.manager.RequestAttribute.ACCOUNT_PASSWORD;

public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AccountDaoImpl accountDao = DaoProvider.getInstance().getAccountDao();
    private static final String EMAIL_SUBJECT = "email.signUp.completionRegistration.subject";
    private static final String TEXT_CONFIRM_EMAIL = "email.signUp.completionRegistration.text";

    @Override
    public boolean validateSignInData(String login, String password) {
        return AccountValidator.isValidSingInData(login, password);
    }

    @Override
    public boolean validateSignUpData(Map<String, String> signUpData) {
        return AccountValidator.isValidSingUpData(signUpData);
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
            LOGGER.error("Failed to process the command '{}' with login '{}'.", CommandType.SIGN_IN, login);
            throw new ServiceException("Failed to process the command '" + CommandType.SIGN_IN +"' with login '" + login + "'.", e);
        }

        return optionalAccount;
    }

    @Override
    public boolean createNewAccount(Map<String, String> signUpData) throws ServiceException {
        Account account = AccountBuilder.buildAccount(signUpData);
        String password = signUpData.get(ACCOUNT_PASSWORD);
        String encodingPassword = BouncyCastle.encoding(password);

        try {
            return accountDao.createAccount(account, encodingPassword);
        } catch (DaoException e) {
            LOGGER.error("Sign up error for account with login: '{}'.", account.getLogin());
            throw new ServiceException("Sign up error for account with login: " + account.getLogin(), e);
        }
    }

    @Override
    public boolean isExistLogin(String login) throws ServiceException {
        boolean result;

        try {
            Optional<Account> accountByLogin = accountDao.findAccountByLogin(login);
            result = accountByLogin.isPresent();
            LOGGER.info(result ? "{} '{}' is exist in the database." : "{} '{}' not found in the database.", "Account with login", login);
        } catch (DaoException e) {
            LOGGER.error("Failed to perform account search operation by login: '{}'.", login);
            throw new ServiceException("Failed to perform account search operation by login: " + login, e);
        }

        return result;
    }

    @Override
    public boolean isExistEmail(String email) throws ServiceException {
        boolean result;

        try {
            Optional<Account> accountByEmail = accountDao.findAccountByEmail(email);
            result = accountByEmail.isPresent();
            LOGGER.info(result ? "{} '{}' is exist in the database." : "{} '{}' not found in the database.", "Account with email", email);
        } catch (DaoException e) {
            LOGGER.error("Failed to perform account search operation by email: '{}'.", email);
            throw new ServiceException("Failed to perform account search operation by email: " + email, e);
        }

        return result;
    }

    @Override
    public void sendActivationEmail(String locale, String firstName, String emailTo) {
        MessageManager messageManager = MessageManager.getInstance();
        String emailSubject = messageManager.getMassage(EMAIL_SUBJECT, locale);
        String formatMessage = messageManager.getMassage(TEXT_CONFIRM_EMAIL, locale);
        String emailMessage = String.format(formatMessage, firstName, "command=?activate");

        EmailSender.send(emailTo, emailSubject, emailMessage);
    }
}
