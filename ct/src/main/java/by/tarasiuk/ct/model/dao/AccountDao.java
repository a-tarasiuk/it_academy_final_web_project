package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.entity.impl.Account;
import by.tarasiuk.ct.exception.DaoException;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findAccountByLogin(String login) throws DaoException;
    Optional<Account> findAccountByEmail(String email) throws DaoException;
    Optional<String> findPasswordByLogin(String login) throws DaoException;
    boolean createAccount(Account account, String password) throws DaoException;
}
