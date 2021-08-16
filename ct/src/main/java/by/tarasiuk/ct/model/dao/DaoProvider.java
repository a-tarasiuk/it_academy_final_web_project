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
     * Getting an instance of a singleton class which performs actions on Account entity.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Account
     * @see         by.tarasiuk.ct.model.dao.AccountDao
     * @see         by.tarasiuk.ct.model.dao.impl.AccountDaoImpl
     */
    public static AccountDaoImpl getAccountDao() {
        return accountDao;
    }

    /**
     * Getting an instance of a singleton class which performs actions on Company entity.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Company
     * @see         by.tarasiuk.ct.model.dao.CompanyDao
     * @see         by.tarasiuk.ct.model.dao.impl.CompanyDaoImpl
     */
    public static CompanyDaoImpl getCompanyDao() {
        return companyDao;
    }

    /**
     * Getting an instance of a singleton class which performs actions on Token entity.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Token
     * @see         by.tarasiuk.ct.model.dao.TokenDao
     * @see         by.tarasiuk.ct.model.dao.impl.TokenDaoImpl
     */
    public static TokenDaoImpl getTokenDao() {
        return tokenDao;
    }

    /**
     * Getting an instance of a singleton class which performs actions on Offer entity.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Offer
     * @see         by.tarasiuk.ct.model.dao.OfferDao
     * @see         by.tarasiuk.ct.model.dao.impl.OfferDaoImpl
     */
    public static OfferDaoImpl getOfferDao() {
        return offerDao;
    }

    /**
     * Getting an instance of a singleton class which performs actions on Employee entity.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Employee
     * @see         by.tarasiuk.ct.model.dao.EmployeeDao
     * @see         by.tarasiuk.ct.model.dao.impl.EmployeeDaoImpl
     */
    public static EmployeeDaoImpl getEmployeeDao() {
        return employeeDao;
    }

    /**
     * Getting an instance of a singleton class which performs actions on Trading entity.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Trading
     * @see         by.tarasiuk.ct.model.dao.TradingDao
     * @see         by.tarasiuk.ct.model.dao.impl.TradingDaoImpl
     */
    public static TradingDaoImpl getTradingDao() {
        return tradingDao;
    }
}
