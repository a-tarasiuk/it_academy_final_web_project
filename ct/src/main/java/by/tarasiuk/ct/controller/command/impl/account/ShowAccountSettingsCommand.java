package by.tarasiuk.ct.controller.command.impl.account;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.AttributeName;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class ShowAccountSettingsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private static final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();

    @Override
    public String execute(RequestContent requestContent) {
        String page = PagePath.ACCOUNT_SETTINGS;
        Map<String, Object> sessionAttributes = requestContent.getSessionAttributes();
        Account account = (Account) sessionAttributes.get(AttributeName.ACCOUNT);
        long accountId = account.getId();

        try {
            Optional<Employee> findEmployee = employeeService.findEmployeeByAccountId(accountId);

            if(findEmployee.isPresent()) {
                Employee employee = findEmployee.get();
                long companyId = employee.getCompanyId();

                Optional<Company> findCompany = companyService.findCompanyById(companyId);
                if(findCompany.isPresent()) {
                    Company company = findCompany.get();

                    requestContent.putRequestAttribute(AttributeName.ACCOUNT, account);
                    requestContent.putRequestAttribute(AttributeName.COMPANY, company);
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_SETTINGS, e);
            page = PagePath.ACCOUNT_OFFERS;
        }

        LOGGER.info("Command '{}' return path '{}'", CommandType.SHOW_ACCOUNT_SETTINGS, PagePath.ACCOUNT_SETTINGS);
        return page;
    }
}
