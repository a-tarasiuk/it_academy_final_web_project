package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.entity.impl.Trading;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.TradingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.controller.command.AttributeName.COMPANY;
import static by.tarasiuk.ct.controller.command.AttributeName.OFFER_FREIGHT;
import static by.tarasiuk.ct.controller.command.AttributeName.TRADING;
import static by.tarasiuk.ct.controller.command.AttributeName.TRADING_ID;

/**
 * Show trading viewer page command.
 */
public class ShowTradingViewerCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();
    private final TradingServiceImpl tradingService = ServiceProvider.getTradingService();
    private final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();

    /**
     * Search for trading by ID in the database.
     * If successful, search for a company by company ID and trading data.
     *
     * @param content   Request data content.
     * @return          Page path.
     * @see             by.tarasiuk.ct.controller.RequestContent
     */
    @Override
    public String execute(RequestContent content) {
        String page = PagePath.TRADING_VIEWER;
        Map<String, String> parameters = content.getRequestParameters();
        long tradingId = Long.parseLong(parameters.get(TRADING_ID));
        float offerFreight = Float.parseFloat(parameters.get(OFFER_FREIGHT));

        LOGGER.debug("Get trading ID '{}' for trading viewer page.", tradingId);

        try {
            Optional<Trading> findTrading = tradingService.findTradingById(tradingId);

            if(findTrading.isPresent()) {
                Trading trading = findTrading.get();
                LOGGER.debug("Trading '{}' was found for trading viewer page.", trading);
                long employeeId = trading.getEmployeeId();

                Optional<Employee> findEmployee = employeeService.findEmployeeById(employeeId);

                if(findEmployee.isPresent()) {
                    Employee employee = findEmployee.get();
                    long companyId = employee.getCompanyId();
                    long accountId = employee.getAccountId();

                    Optional<Company> findCompany = companyService.findCompanyById(companyId);
                    Optional<Account> findAccount = accountService.findAccountById(accountId);

                    if(findCompany.isPresent() && findAccount.isPresent()) {
                        Company company = findCompany.get();
                        Account account = findAccount.get();

                        content.putRequestAttribute(COMPANY, company);
                        content.putRequestAttribute(ACCOUNT, account);
                        content.putRequestAttribute(TRADING, trading);
                        content.putRequestAttribute(OFFER_FREIGHT, offerFreight);

                        LOGGER.info("Trading data successfully send on trading viewer page.");
                    }
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_TRADING_VIEWER, e);
            page = PagePath.ACCOUNT_OFFER;
        }

        return page;
    }
}
