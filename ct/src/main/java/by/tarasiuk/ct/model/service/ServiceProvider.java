package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.model.service.impl.*;

public class ServiceProvider {
    private static final AccountServiceImpl accountService = AccountServiceImpl.getInstance();
    private static final CompanyServiceImpl companyService = CompanyServiceImpl.getInstance();
    private static final TokenServiceImpl tokenService = TokenServiceImpl.getInstance();
    private static final OfferServiceImpl offerService = OfferServiceImpl.getInstance();
    private static final EmployeeServiceImpl employeeService = EmployeeServiceImpl.getInstance();
    private static final TradingServiceImpl tradingService = TradingServiceImpl.getInstance();

    private ServiceProvider() {
    }

    public static AccountServiceImpl getAccountService() {
        return accountService;
    }

    public static CompanyServiceImpl getCompanyService() {
        return companyService;
    }

    public static TokenServiceImpl getTokenService() {
        return tokenService;
    }

    public static OfferServiceImpl getOfferService() {
        return offerService;
    }

    public static EmployeeServiceImpl getEmployeeService() {
        return employeeService;
    }

    public static TradingServiceImpl getTradingService() {
        return tradingService;
    }
}
