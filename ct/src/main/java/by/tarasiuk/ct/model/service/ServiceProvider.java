package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import by.tarasiuk.ct.model.service.impl.TokenServiceImpl;
import by.tarasiuk.ct.model.service.impl.TradingServiceImpl;

/**
 * Provider which returns a singleton instance of the corresponding object from SERVICE layer.
 */
public class ServiceProvider {
    private static final AccountServiceImpl accountService = AccountServiceImpl.getInstance();
    private static final CompanyServiceImpl companyService = CompanyServiceImpl.getInstance();
    private static final TokenServiceImpl tokenService = TokenServiceImpl.getInstance();
    private static final OfferServiceImpl offerService = OfferServiceImpl.getInstance();
    private static final EmployeeServiceImpl employeeService = EmployeeServiceImpl.getInstance();
    private static final TradingServiceImpl tradingService = TradingServiceImpl.getInstance();

    private ServiceProvider() {
    }

    /**
     * Getting an instance of a singleton class which performs actions on Account data.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Account
     * @see         by.tarasiuk.ct.model.dao.AccountDao
     * @see         by.tarasiuk.ct.model.dao.impl.AccountDaoImpl
     */
    public static AccountServiceImpl getAccountService() {
        return accountService;
    }

    /**
     * Getting an instance of a singleton class which performs actions on Company data.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Company
     * @see         by.tarasiuk.ct.model.dao.CompanyDao
     * @see         by.tarasiuk.ct.model.dao.impl.CompanyDaoImpl
     */
    public static CompanyServiceImpl getCompanyService() {
        return companyService;
    }

    /**
     * Getting an instance of a singleton class which performs actions on Token data.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Token
     * @see         by.tarasiuk.ct.model.dao.TokenDao
     * @see         by.tarasiuk.ct.model.dao.impl.TokenDaoImpl
     */
    public static TokenServiceImpl getTokenService() {
        return tokenService;
    }

    /**
     * Getting an instance of a singleton class which performs actions on Offer data.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Offer
     * @see         by.tarasiuk.ct.model.dao.OfferDao
     * @see         by.tarasiuk.ct.model.dao.impl.OfferDaoImpl
     */
    public static OfferServiceImpl getOfferService() {
        return offerService;
    }

    /**
     * Getting an instance of a singleton class which performs actions on Employee data.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Employee
     * @see         by.tarasiuk.ct.model.dao.EmployeeDao
     * @see         by.tarasiuk.ct.model.dao.impl.EmployeeDaoImpl
     */
    public static EmployeeServiceImpl getEmployeeService() {
        return employeeService;
    }

    /**
     * Getting an instance of a singleton class which performs actions on Trading data.
     *
     * @return      Singleton class.
     * @see         by.tarasiuk.ct.model.entity.impl.Trading
     * @see         by.tarasiuk.ct.model.dao.TradingDao
     * @see         by.tarasiuk.ct.model.dao.impl.TradingDaoImpl
     */
    public static TradingServiceImpl getTradingService() {
        return tradingService;
    }
}
