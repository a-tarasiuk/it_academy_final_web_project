package by.tarasiuk.ct.controller.command.impl.manager;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.entity.impl.Token;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.TokenServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.controller.command.AttributeName.INFORMATION_MESSAGE;

public class CreateForwarderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();
    private final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private final TokenServiceImpl tokenService = ServiceProvider.getTokenService();

    @Override
    public String execute(RequestContent content) {
        String page = PagePath.FORWARDER_CREATOR;

        Map<String, String> accountData = content.getRequestParameters();

        String login = accountData.get(AttributeName.ACCOUNT_LOGIN);
        String email = accountData.get(AttributeName.ACCOUNT_EMAIL);

        try {
            if(!accountService.validateAccountData(accountData)) {
                content.putRequestAttributes(accountData);
                content.putRequestAttribute(AttributeName.INVALID_DATA, true);
                LOGGER.info("Incorrect account data '{}'.", accountData);
            } else {
                Optional<Account> findAccountByLogin = accountService.findAccountByLogin(login);
                Optional<Account> findAccountByEmail = accountService.findAccountByEmail(email);

                if(!findAccountByLogin.isPresent() && !findAccountByEmail.isPresent()) {
                    if(accountService.createForwarder(accountData)) {
                        Optional<Account> findAccount = accountService.findAccountByEmail(email);

                        if(findAccount.isPresent()) {
                            Account account = findAccount.get();
                            long accountId = account.getId();
                            String firstName = account.getFirstName();
                            Optional<Object> findEmployee = content.findSessionAttribute(AttributeName.EMPLOYEE);

                            if(findEmployee.isPresent()) {
                                Employee employee = (Employee) findEmployee.get();
                                long companyId = employee.getCompanyId();
                                employeeService.createEmployee(accountId, companyId);
                                tokenService.createToken(accountId);

                                Optional<Token> optionalToken = tokenService.findTokenByAccount(account);

                                if (optionalToken.isPresent()) {
                                    Token token = optionalToken.get();
                                    String tokenNumber = token.getNumber();
                                    Optional<Object> findLocale = content.findSessionAttribute(AttributeName.LOCALE);

                                    if(findLocale.isPresent()) {
                                        String locale = (String) findLocale.get();
                                        accountService.sendActivationEmail(locale, firstName, email, tokenNumber);
                                    }
                                }

                                LOGGER.info("Forwarder and employee successfully created.");
                                page = PagePath.FORWARDER_CREATED_INFO;
                            }
                        }
                    }
                } else {
                    if(findAccountByLogin.isPresent()) {
                        content.putRequestAttribute(AttributeName.MESSAGE_ERROR_LOGIN_ALREADY_EXIST, true);
                    }

                    if(findAccountByEmail.isPresent()) {
                        content.putRequestAttribute(AttributeName.MESSAGE_ERROR_EMAIL_ALREADY_EXIST, true);
                    }

                    content.putRequestAttributes(accountData);
                }
            }
        } catch (ServiceException e) {
            LOGGER.warn("Can't create forwarder.", e);
            content.putRequestAttribute(INFORMATION_MESSAGE, true);
            page = PagePath.ACCOUNT_SETTINGS;   //fixme (сделать перенаправление на страницу ошибок)
        }

        return page;
    }
}
