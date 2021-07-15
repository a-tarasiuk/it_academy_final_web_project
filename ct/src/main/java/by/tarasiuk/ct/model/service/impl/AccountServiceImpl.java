package by.tarasiuk.ct.model.service.impl;

import by.tarasiuk.ct.command.CommandType;
import by.tarasiuk.ct.entity.*;
import by.tarasiuk.ct.exception.CryptException;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.RequestAttribute;
import by.tarasiuk.ct.model.dao.impl.AccountDaoImpl;
import by.tarasiuk.ct.model.dao.DaoProvider;
import by.tarasiuk.ct.util.AesCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class AccountServiceImpl implements by.tarasiuk.ct.model.service.AccountService {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final AccountDaoImpl ACCOUNT_DAO_IMPL = DaoProvider.getInstance().getAccountDao();
    private static final AesCrypt crypt = AesCrypt.getInstance();

    @Override
    public Optional<Account> signIn(String login, String password) throws ServiceException {
        try {
            return ACCOUNT_DAO_IMPL.getAccountByLogin(login);
        } catch (DaoException e) {
            LOGGER.error("Failed to process the command '{}' with login '{}'.", CommandType.SIGN_IN, login);
            throw new ServiceException("Failed to process the command '" + CommandType.SIGN_IN +"' with login '" + login + "'.", e);
        }
    }

    @Override
    public boolean signUp(Map<String, String> accountData, String passwordWithoutEncrypt, String confirmPassword) throws ServiceException {
        try {
            String encryptPassword = crypt.encrypt(passwordWithoutEncrypt);
            Account account = findAccountFromMap(accountData);

            return ACCOUNT_DAO_IMPL.createAccount(account, encryptPassword);
        } catch (CryptException e) { // todo : нужно ли обрабатывать исключение, если оно уже обрабатывается в классе AesCrypt
            LOGGER.warn("Failed to encrypt password: {}.", passwordWithoutEncrypt);
            throw new ServiceException("Failed to encrypt password: " + passwordWithoutEncrypt, e);
        } catch (DaoException e) {
            LOGGER.error("Failed to create account with login '{}' into database.", accountData.get(RequestAttribute.USER_LOGIN));
            throw new ServiceException("Failed to create account with login '" + accountData.get(RequestAttribute.USER_LOGIN)  + "' into database.", e);
        }
    }

    private Account findAccountFromMap(Map<String, String> accountData) {
        String login = accountData.get(RequestAttribute.USER_LOGIN);
        String email = accountData.get(RequestAttribute.USER_EMAIL);

        String registrationDateString = accountData.get(RequestAttribute.USER_REGISTRATION_DATE);
        LocalDate registrationDate = LocalDate.parse(registrationDateString);

        String phoneNumber = accountData.get(RequestAttribute.USER_PHONE_NUMBER);
        String address = accountData.get(RequestAttribute.USER_ADDRESS);

        String roleName = accountData.get(RequestAttribute.USER_ROLE_ID);
        Account.AccountRole role = Account.AccountRole.valueOf(roleName.toUpperCase());

        String statusName = accountData.get(RequestAttribute.USER_STATUS_ID);
        Account.AccountStatus status = Account.AccountStatus.valueOf(statusName.toUpperCase());

        Account account = new Account();
        account.setLogin(login);
        account.setEmail(email);
        account.setRegistrationDate(registrationDate);
        account.setPhoneNumber(phoneNumber);
        account.setAddress(address);
        account.setAccountRole(role);
        account.setAccountStatus(status);

        return account;
    }
}
