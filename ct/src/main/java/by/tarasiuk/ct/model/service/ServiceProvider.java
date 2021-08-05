package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.TokenServiceImpl;

public class ServiceProvider {
    private static final AccountServiceImpl accountService = AccountServiceImpl.getInstance();
    private static final CompanyServiceImpl companyService = CompanyServiceImpl.getInstance();
    private static final TokenServiceImpl tokenService = TokenServiceImpl.getInstance();

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
}
