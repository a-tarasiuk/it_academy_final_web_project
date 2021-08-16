package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.exception.DaoException;
import java.util.Optional;

public interface AccountDao {
    boolean createAccount(Account account, String encodingPassword) throws DaoException;
    boolean updatePasswordByAccountId(long accountId, String encodingPassword) throws DaoException;
    boolean updateStatusByAccountId(long id, Account.Status status) throws DaoException;
    Optional<Account> findAccountByLogin(String login) throws DaoException;
    Optional<String> findPasswordByAccountId(long accountId) throws DaoException;
    Optional<Account> findAccountByEmail(String email) throws DaoException;
    Optional<String> findPasswordByLogin(String login) throws DaoException;
}
