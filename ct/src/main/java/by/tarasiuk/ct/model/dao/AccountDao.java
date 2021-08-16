package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.exception.DaoException;
import java.util.Optional;

/**
 * Interface for operations with the data of the account,
 * which are contained in the in the database table <code>accounts</code>.
 */
public interface AccountDao {
    /**
     * Creating an Account entity in the database.
     *
     * @param   account           Account entity.
     * @param   encodingPassword  Encoding password for account entity.
     * @return                    <code>true</code> if the account is successfully created in the database.
     *                            Otherwise return <code>false</code>.
     * @throws  DaoException      If a database access error occurs; this method is called on a closed PreparedStatement.
     */
    boolean createAccount(Account account, String encodingPassword) throws DaoException;

    /**
     * Update encoding password for an Account entity by account ID in the database.
     *
     * @param accountId         Account entity ID.
     * @param encodingPassword  Encoding password for account entity.
     * @return                  <code>true</code> if the password is successfully updated in the database.
     *                          Otherwise return <code>false</code>.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement.
     */
    boolean updatePasswordByAccountId(long accountId, String encodingPassword) throws DaoException;

    /**
     * Updating account status by account ID in the database.
     *
     * @param id                Account entity ID.
     * @param status            Account status.
     * @return                  <code>true</code> if the password is successfully updated in the database.
     *                          Otherwise return <code>false</code>.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement.
     */
    boolean updateStatusByAccountId(long id, Account.Status status) throws DaoException;

    /**
     * Find account entity by account ID in the database.
     *
     * @param login             Account login.
     * @return                  Optional of Account entity if existing Account in the database.
     *                          Otherwise return an empty Optional instance. No value is present for this Optional.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or an argument is supplied to this method.
     */
    Optional<Account> findAccountByLogin(String login) throws DaoException;

    /**
     * Find password for account entity by account ID in the database.
     *
     * @param accountId         Account ID.
     * @return                  Optional of String if existing Account with ID in the database.
     *                          Otherwise return an empty Optional instance. No value is present for this Optional.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or an argument is supplied to this method.
     */
    Optional<String> findPasswordByAccountId(long accountId) throws DaoException;

    /**
     * Find Account entity by email in the database.
     *
     * @param email             Account email.
     * @return                  Optional of Account entity if existing email in the database.
     *                          Otherwise return an empty Optional instance. No value is present for this Optional.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or an argument is supplied to this method.
     */
    Optional<Account> findAccountByEmail(String email) throws DaoException;

    /**
     * Find Account entity by email in the database.
     *
     * @param login             Account login.
     * @return                  Optional of String if existing Account with login in the database.
     *                          Otherwise return an empty Optional instance. No value is present for this Optional.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or an argument is supplied to this method.
     */
    Optional<String> findPasswordByLogin(String login) throws DaoException;
}
