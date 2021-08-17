package by.tarasiuk.ct.controller.command.impl.manager;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Only for an account with the <code>MANAGER</code> role.
 * Show the page where can be changed information about forwarder.
 */
public class ShowForwarderSettingsPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();

    /**
     * Search in the database for a list of all employees of a given company.
     *
     * @param content   Request data content.
     * @return          Page path.
     * @see             by.tarasiuk.ct.controller.RequestContent
     */
    @Override
    public String execute(RequestContent content) {
        String page = PagePath.FORWARDER_SETTINGS;

        Optional<Object> findEmployee = content.findSessionAttribute(AttributeName.EMPLOYEE);

        if(findEmployee.isPresent()) {
            Employee currentEmployee = (Employee) findEmployee.get();
            long companyId = currentEmployee.getCompanyId();

            try {
                List<Employee> employeeList = employeeService.findEmployeeListByCompanyId(companyId);
                LOGGER.debug("By company with ID '{}' was found '{}' employees.", companyId, employeeList.size());

                List<Account> accountList;

                if(employeeList != null && !employeeList.isEmpty()) {
                    accountList = new ArrayList<>();

                    for(Employee employee: employeeList) {
                        long accountId = employee.getAccountId();
                        Optional<Account> findAccount = accountService.findAccountById(accountId);

                        if(findAccount.isPresent()) {
                            Account account = findAccount.get();
                            accountList.add(account);
                        }
                    }

                    content.putRequestAttribute(AttributeName.ACCOUNT_LIST, accountList);
                    LOGGER.info("By company with ID '{}' was find '{}' accounts.", companyId, accountList.size());
                } else {
                    LOGGER.debug("By company with ID '{}' accounts not found.", companyId);
                }
            } catch (ServiceException e) {
                LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_FORWARDER_SETTINGS_PAGE, e);
                page = PagePath.ERROR;
            }
        }

        return page;
    }
}
