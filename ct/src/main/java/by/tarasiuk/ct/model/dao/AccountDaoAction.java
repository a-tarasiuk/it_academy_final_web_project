package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.exception.DaoException;

import java.util.Optional;

public interface AccountDaoAction {
    Optional<Account> getAccountByLogin(String requiredLogin) throws DaoException;
    boolean createAccount(Account account, String password) throws DaoException;
}
