package by.tarasiuk.ct.model.service.impl;

import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.dao.DaoProvider;
import by.tarasiuk.ct.model.dao.impl.AccountDaoImpl;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.service.AccountService;
import by.tarasiuk.ct.model.service.builder.AccountServiceBuilder;
import by.tarasiuk.ct.util.BouncyCastle;
import by.tarasiuk.ct.util.EmailSender;
import by.tarasiuk.ct.validator.AccountValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_PASSWORD;

public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AccountServiceImpl instance = new AccountServiceImpl();
    private final AccountDaoImpl accountDao = DaoProvider.getAccountDao();

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
    public boolean validateAccountData(Map<String, String> accountData) throws ServiceException {
        return AccountValidator.isValidAccountData(accountData);
    }

    @Override
    public boolean validatePersonalAccountData(Map<String, String> accountData) throws ServiceException {
        return AccountValidator.isValidPersonalAccountData(accountData);
    }

    @Override
    public boolean changeAccountPasswordByAccountId(long accountId, String newPassword) throws ServiceException {
        boolean result;

        try {
            String encodingNewPassword = BouncyCastle.encoding(newPassword);

            result = accountDao.updatePasswordByAccountId(accountId, encodingNewPassword);

            LOGGER.info(result
                    ? "Password successfully was changed for account with ID '{}'."
                    : "Failed to change password for account with ID", accountId);
        } catch (DaoException e) {
            LOGGER.error("Error on change password for account with ID '{}'.", accountId, e);
            throw new ServiceException("Error on change password for account with ID '" + accountId + "'.", e);
        }

        return result;
    }

    @Override
    public boolean isAccountPasswordByAccountId(long accountId, String password) throws ServiceException {
        boolean result = false;

        try {
            Optional<String> findCurrentPassword = accountDao.findPasswordByAccountId(accountId);

            if(findCurrentPassword.isPresent()) {
                String encodingCurrentPassword = findCurrentPassword.get();
                String encodingPassword = BouncyCastle.encoding(password);

                result = encodingCurrentPassword.equals(encodingPassword);
            }
        } catch (DaoException e) {
            LOGGER.error("Error on change password for account with ID '{}'.", accountId, e);
            throw new ServiceException("Error on change password for account with ID '" + accountId + "'.", e);
        }

        return result;
    }

    @Override
    public boolean validatePasswordsForChange(String oldPassword, String newPassword, String confirmNewPassword) {
        if(oldPassword == null || newPassword == null || confirmNewPassword == null
                || oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            return false;
        }

        boolean isValidOldPassword = AccountValidator.isValidPassword(oldPassword);
        boolean isValidNewPassword = AccountValidator.isValidPassword(newPassword);
        boolean isValidConfirmNewPassword = AccountValidator.isValidPassword(confirmNewPassword);

        return  isValidOldPassword && isValidNewPassword && isValidConfirmNewPassword;
    }

    @Override
    public Optional<Account> signIn(String login, String password) throws ServiceException {
        Optional<Account> findAccount;

        try {
            findAccount = accountDao.findAccountByLogin(login);

            if (findAccount.isPresent()) {
                LOGGER.info("Account with login '{}' was found in the database.", login);
                Optional<String> optionalPassword = accountDao.findPasswordByLogin(login);

                if(optionalPassword.isPresent()) {
                    String encodedPassword = BouncyCastle.encoding(password);
                    String findPassword = optionalPassword.get();

                    if(!encodedPassword.equals(findPassword)) {
                        findAccount = Optional.empty();
                        LOGGER.info("Incorrect password for login '{}'.", login);
                    }
                }
            } else {
                findAccount = Optional.empty();
                LOGGER.info("Account with login '{}' not found in the database.", login);
            }
        } catch (DaoException e) {
            LOGGER.error("Sign in error for login '{}'.", login, e);
            throw new ServiceException("Sign in error for login '" + login + "'.", e);
        }

        return findAccount;
    }

    @Override
    public boolean createForwarder(Map<String, String> forwarderData) throws ServiceException {
        Account.Role role = Account.Role.FORWARDER;
        Account account = AccountServiceBuilder.buildAccount(forwarderData, role);
        String password = forwarderData.get(ACCOUNT_PASSWORD);

        return createAccount(account, password);
    }

    @Override
    public boolean createManager(Map<String, String> managerData) throws ServiceException {
        Account.Role role = Account.Role.MANAGER;
        Account account = AccountServiceBuilder.buildAccount(managerData, role);
        String password = managerData.get(ACCOUNT_PASSWORD);

        return createAccount(account, password);
    }

    @Override
    public void sendActivationEmail(String locale, String firstName, String emailTo, String token) {
        EmailSender.sendActivationEmail(locale, firstName, emailTo, token);
    }

    @Override
    public Optional<Account> findAccountById(long accountId) throws ServiceException {
        Optional<Account> findAccount;

        try {
            findAccount = accountDao.findEntityById(accountId);
            LOGGER.info(findAccount.isPresent()
                    ? "Successfully was find account by id '{}'."
                    : "Account with id '{}' not found in the database.", accountId);
        } catch (DaoException e) {
            LOGGER.error("Error when searching for an account by id '{}'.", accountId, e);
            throw new ServiceException("Error when searching for an account by id '" + accountId + "'.", e);
        }

        return findAccount;
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) throws ServiceException {
        Optional<Account> findAccount;

        try {
            findAccount = accountDao.findAccountByEmail(email);
            LOGGER.info(findAccount.isPresent()
                    ? "Successfully was find account by email '{}'."
                    : "Account with email '{}' not found in the database.", email);
        } catch (DaoException e) {
            LOGGER.error("Error when searching for an account by mail '{}'.", email, e);
            throw new ServiceException("Error when searching for an account by mail '" + email + "'.", e);
        }

        return findAccount;
    }

    @Override
    public Optional<Account> findAccountByLogin(String login) throws ServiceException {
        Optional<Account> findAccount;

        try {
            findAccount = accountDao.findAccountByLogin(login);
            LOGGER.info(findAccount.isPresent()
                    ? "Successfully was find account by login '{}': {}."
                    : "Account with login '{}' not found in the database.", login);
        } catch (DaoException e) {
            LOGGER.error("Error when searching for an account by login '{}'.", login, e);
            throw new ServiceException("Error when searching for an account by login '" + login + "'.", e);
        }

        return findAccount;
    }

    @Override
    public void updatePersonalDataByAccountId(long id, Map<String, String> personalData) throws ServiceException {
        try {
            Optional<Account> findAccount = accountDao.findEntityById(id);

            if(findAccount.isPresent()) {
                String accountFirstName = personalData.get(AttributeName.ACCOUNT_FIRST_NAME);
                String accountLastName = personalData.get(AttributeName.ACCOUNT_LAST_NAME);

                Account account = findAccount.get();
                account.setFirstName(accountFirstName);
                account.setLastName(accountLastName);

                boolean result = updateAccount(account);

                LOGGER.info(result
                        ? "Successfully was update for account ID '{}' personal data '{}' in the database."
                        : "Account with ID '{}' doesn't update personal data '{}' in the database.", id, personalData);
            }
        } catch (DaoException e) {
            LOGGER.error("Error when updating personal data '{}' for account with ID '{}'.", personalData, id, e);
            throw new ServiceException("Error when updating personal data '" + personalData + "' for account with ID '" + id + "'.", e);
        }
    }

    @Override
    public void changeAccountStatus(Account account, Account.Status status) throws ServiceException {
        account.setStatus(status);
        updateAccount(account);
    }

    @Override
    public boolean banAccountById(long id) throws ServiceException {
        Account.Status status = Account.Status.BANNED;

        try {
            return accountDao.updateStatusByAccountId(id, status);
        } catch (DaoException e) {
            LOGGER.error("Error while ban account with ID '{}'.", id, e);
            throw new ServiceException("Error while ban account with ID '" + id + "'.", e);
        }
    }

    @Override
    public boolean unbanAccountById(long id) throws ServiceException {
        Account.Status status = Account.Status.ACTIVATED;

        try {
            return accountDao.updateStatusByAccountId(id, status);
        } catch (DaoException e) {
            LOGGER.error("Error while unban account with ID '{}'.", id, e);
            throw new ServiceException("Error while unban account with ID '" + id + "'.", e);
        }
    }

    @Override
    public List<Account> findAccountList() throws ServiceException {
        List<Account> accountList;

        try {
            accountList = accountDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Error while find all accounts.", e);
            throw new ServiceException("Error while find all accounts.", e);
        }

        return accountList;
    }

    private boolean updateAccount(Account account) throws ServiceException {
        boolean result;

        try {
            result = accountDao.updateEntity(account);
            LOGGER.info(result
                    ? "Successfully was update account '{}' in the database."
                    : "Account doesn't update account '{}' in the database.", account);
        } catch (DaoException e) {
            LOGGER.error("Error when updating account '{}'.", account, e);
            throw new ServiceException("Error when updating account '" + account + "'.", e);
        }

        return result;
    }

    private boolean createAccount(Account account, String password) throws ServiceException {
        String encodingPassword = BouncyCastle.encoding(password);

        try {
            return accountDao.createAccount(account, encodingPassword);
        } catch (DaoException e) {
            LOGGER.error("Account creation error '{}'.", account, e);
            throw new ServiceException("Account creation error: " + account, e);
        }
    }
}
