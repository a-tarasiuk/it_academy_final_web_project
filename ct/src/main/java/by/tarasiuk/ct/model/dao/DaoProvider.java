package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.dao.impl.AccountDaoImpl;
import by.tarasiuk.ct.model.dao.impl.CompanyDaoImpl;
import by.tarasiuk.ct.model.dao.impl.EmployeeDaoImpl;
import by.tarasiuk.ct.model.dao.impl.OfferDaoImpl;
import by.tarasiuk.ct.model.dao.impl.TokenDaoImpl;

public class DaoProvider {
    private static final AccountDaoImpl accountDao = AccountDaoImpl.getInstance();
    private static final CompanyDaoImpl companyDao = CompanyDaoImpl.getInstance();
    private static final TokenDaoImpl tokenDao = TokenDaoImpl.getInstance();
    private static final OfferDaoImpl offerDao = OfferDaoImpl.getInstance();
    private static final EmployeeDaoImpl employeeDao = EmployeeDaoImpl.getInstance();

    private DaoProvider() {
    }

    public static AccountDaoImpl getAccountDao() {
        return accountDao;
    }

    public static CompanyDaoImpl getCompanyDao() {
        return companyDao;
    }

    public static TokenDaoImpl getTokenDao() {
        return tokenDao;
    }

    public static OfferDaoImpl getOfferDao() {
        return offerDao;
    }

    public static EmployeeDaoImpl getEmployeeDao() {
        return employeeDao;
    }
}
