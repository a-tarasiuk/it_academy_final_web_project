package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.dao.impl.AccountDaoImpl;
import by.tarasiuk.ct.model.dao.impl.CompanyDaoImpl;
import by.tarasiuk.ct.model.dao.impl.TokenDaoImpl;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private static final AccountDaoImpl ACCOUNT_DAO_IMPL = AccountDaoImpl.getInstance();
    private static final CompanyDaoImpl COMPANY_DAO_IMPL = CompanyDaoImpl.getInstance();
    private static final TokenDaoImpl TOKEN_DAO_IMPL = TokenDaoImpl.getInstance();

    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        return instance;
    }

    public AccountDaoImpl getAccountDao() {
        return ACCOUNT_DAO_IMPL;
    }

    public CompanyDaoImpl getCompanyDao() {
        return COMPANY_DAO_IMPL;
    }

    public TokenDaoImpl getTokenDao() {
        return TOKEN_DAO_IMPL;
    }
}
