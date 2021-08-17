package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.exception.ServiceException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service that works with account data.
 */
public interface AccountService {

    /**
     * Update personal account data by account ID.
     *
     * @param id                    Account ID.
     * @param personalData          Contain first name, last name.
     * @throws ServiceException     Default exception of service layer.
     */
    void updatePersonalDataByAccountId(long id, Map<String, String> personalData) throws ServiceException;

    /**
     * Change account status for account entity.
     *
     * @param account               Account entity.
     * @param status                Account status.
     * @throws ServiceException     Default exception of service layer.
     */
    void changeAccountStatus(Account account, Account.Status status) throws ServiceException;

    /**
     * Sending an email to an email containing a token to activate your account.
     *
     * @param locale                Locale page.
     * @param firstName             Account first name.
     * @param emailTo               Account email.
     * @param token                 Token number.
     */
    void sendActivationEmail(String locale, String firstName, String emailTo, String token);

    /**
     * Create account with role <code>FORWARDER</code>.
     *
     * @param forwarderData      Forwarder data.
     * @return                      <code>true</code> if the sign up data is valid.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean createForwarder(Map<String, String> forwarderData) throws ServiceException;

    /**
     * Create account with role <code>MANAGER</code>.
     *
     * @param managerData           Manager data.
     * @return                      <code>true</code> if the sign up data is valid.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean createManager(Map<String, String> managerData) throws ServiceException;

    /**
     * Account password verification by account ID.
     *
     * @param accountId             Account ID.
     * @param password              Account password.
     * @return                      <code>true</code> if the sign up data is valid.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean isAccountPasswordByAccountId(long accountId, String password) throws ServiceException;

    /**
     * Validate current account password, new account password and confirmed new password.
     *
     * @param oldPassword           Current account password.
     * @param newPassword           New account password.
     * @param confirmNewPassword    Confirmed new account password.
     * @return                      <code>true</code> if the sign up data is valid.
     *                              Otherwise return <code>false</code>.
     */
    boolean validatePasswordsForChange(String oldPassword, String newPassword, String confirmNewPassword);

    /**
     * Validate sign in account data.
     *
     * @param login                 Account login.
     * @param password              Account password.
     * @return                      <code>true</code> if the sign in data is valid.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean validateSignInData(String login, String password) throws ServiceException;

    /**
     * Validate sign up account data.
     *
     * @param signUpData            Validate sigh up data.
     * @return                      <code>true</code> if the sign up data is valid.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean validateSignUpData(Map<String, String> signUpData) throws ServiceException;

    /**
     * Validate account data.
     *
     * @param accountData           Account data.
     * @return                      <code>true</code> if the account data is valid.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean validateAccountData(Map<String, String> accountData) throws ServiceException;

    /**
     * Validate personal account data.
     *
     * @param accountData           Personal account data.
     * @return                      <code>true</code> if the personal account data is valid.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean validatePersonalAccountData(Map<String, String> accountData) throws ServiceException;

    /**
     * Change account password by account ID.
     *
     * @param accountId             Account ID.
     * @param newPassword           New password.
     * @return                      <code>true</code> if the account password has been changed.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean changeAccountPasswordByAccountId(long accountId, String newPassword) throws ServiceException;

    /**
     * Ban account by account ID.
     *
     * @param id                    Account ID.
     * @return                      <code>true</code> if the account has been banned.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean banAccountById(long id) throws ServiceException;

    /**
     * Unban account by account ID.
     *
     * @param id                    Account ID.
     * @return                      <code>true</code> if the account has been unbanned.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean unbanAccountById(long id) throws ServiceException;

    /**
     * Trying to sign in.
     *
     * @param login                 Account login.
     * @param password              Account password.
     * @return                      Optional of account entity.
     * @throws ServiceException     Default exception of service layer.
     */
    Optional<Account> signIn(String login, String password) throws ServiceException;

    /**
     * Find account entity by account ID.
     *
     * @param accountId             Account ID.
     * @return                      Optional of account.
     * @throws ServiceException     Default exception of service layer.
     */
    Optional<Account> findAccountById(long accountId) throws ServiceException;

    /**
     * Find account by email.
     *
     * @param email                 Account email.
     * @return                      Optional of account entity.
     * @throws ServiceException     Default exception of service layer.
     */
    Optional<Account> findAccountByEmail(String email) throws ServiceException;

    /**
     * Find account by login.
     *
     * @param login                 Account login.
     * @return                      Optional of account entity.
     * @throws ServiceException     Default exception of service layer.
     */
    Optional<Account> findAccountByLogin(String login) throws ServiceException;

    /**
     * Find all accounts.
     *
     * @return                      List of account entity.
     * @throws ServiceException     Default exception of service layer.
     */
    List<Account> findAccountList() throws ServiceException;
}
