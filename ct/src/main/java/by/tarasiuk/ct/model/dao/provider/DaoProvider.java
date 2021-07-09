package by.tarasiuk.ct.model.dao.provider;

import by.tarasiuk.ct.model.dao.impl.AccountDao;
import by.tarasiuk.ct.model.dao.impl.AccountRoleDao;
import by.tarasiuk.ct.model.dao.impl.AccountStatusDao;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private static final AccountDao accountDao = AccountDao.getInstance();
    private static final AccountRoleDao accountRoleDao = AccountRoleDao.getInstance();
    private static final AccountStatusDao accountStatusDao = AccountStatusDao.getInstance();

    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        return instance;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public AccountRoleDao getAccountRoleDao() {
        return accountRoleDao;
    }

    public AccountStatusDao getAccountStatusDao() {
        return accountStatusDao;
    }
}
