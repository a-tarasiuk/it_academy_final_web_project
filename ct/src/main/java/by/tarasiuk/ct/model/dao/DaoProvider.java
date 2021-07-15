package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.dao.impl.AccountDaoImpl;
import by.tarasiuk.ct.model.dao.impl.AccountRoleDao;
import by.tarasiuk.ct.model.dao.impl.AccountStatusDao;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private static final AccountDaoImpl ACCOUNT_DAO_IMPL = AccountDaoImpl.getInstance();
    private static final AccountRoleDao accountRoleDao = AccountRoleDao.getInstance();
    private static final AccountStatusDao accountStatusDao = AccountStatusDao.getInstance();

    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        return instance;
    }

    public AccountDaoImpl getAccountDao() {
        return ACCOUNT_DAO_IMPL;
    }

    public AccountRoleDao getAccountRoleDao() {
        return accountRoleDao;
    }

    public AccountStatusDao getAccountStatusDao() {
        return accountStatusDao;
    }
}
