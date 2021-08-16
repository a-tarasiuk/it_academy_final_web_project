package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.dao.impl.AccountDaoImpl;
import by.tarasiuk.ct.model.dao.impl.CompanyDaoImpl;
import by.tarasiuk.ct.model.dao.impl.EmployeeDaoImpl;
import by.tarasiuk.ct.model.dao.impl.OfferDaoImpl;
import by.tarasiuk.ct.model.dao.impl.TokenDaoImpl;
import by.tarasiuk.ct.model.dao.impl.TradingDaoImpl;

/**
 * Provider which returns a singleton instance of the corresponding object from DAO layer.
 */
public class DaoProvider {
    private static final AccountDaoImpl accountDao = AccountDaoImpl.getInstance();
    private static final CompanyDaoImpl companyDao = CompanyDaoImpl.getInstance();
    private static final TokenDaoImpl tokenDao = TokenDaoImpl.getInstance();
    private static final OfferDaoImpl offerDao = OfferDaoImpl.getInstance();
    private static final EmployeeDaoImpl employeeDao = EmployeeDaoImpl.getInstance();
    private static final TradingDaoImpl tradingDao = TradingDaoImpl.getInstance();

    private DaoProvider() {
    }

    /**
     * Get a class object {@link by.tarasiuk.ct.model.dao.impl.AccountDaoImpl}
     *
     * @return      Class instance of {@link by.tarasiuk.ct.model.dao.AccountDao}
     */
    public static AccountDaoImpl getAccountDao() {
        return accountDao;
    }

    /**
     * Get a class object {@link by.tarasiuk.ct.model.dao.impl.CompanyDaoImpl}
     *
     * @return      Class instance of {@link by.tarasiuk.ct.model.dao.CompanyDao}
     */
    public static CompanyDaoImpl getCompanyDao() {
        return companyDao;
    }

    /**
     * Get a class object {@link by.tarasiuk.ct.model.dao.impl.TokenDaoImpl}
     *
     * @return      Class instance of {@link by.tarasiuk.ct.model.dao.TokenDao}
     */
    public static TokenDaoImpl getTokenDao() {
        return tokenDao;
    }

    /**
     * Get a class object {@link by.tarasiuk.ct.model.dao.impl.OfferDaoImpl}
     *
     * @return      Class instance of {@link by.tarasiuk.ct.model.dao.OfferDao}
     */
    public static OfferDaoImpl getOfferDao() {
        return offerDao;
    }

    /**
     * Get a class object {@link by.tarasiuk.ct.model.dao.impl.EmployeeDaoImpl}
     *
     * @return      Class instance of {@link by.tarasiuk.ct.model.dao.EmployeeDao}
     */
    public static EmployeeDaoImpl getEmployeeDao() {
        return employeeDao;
    }

    /**
     * Get a class object {@link by.tarasiuk.ct.model.dao.impl.TradingDaoImpl}
     *
     * @return      Class instance of {@link by.tarasiuk.ct.model.dao.TradingDao}
     */
    public static TradingDaoImpl getTradingDao() {
        return tradingDao;
    }
}
