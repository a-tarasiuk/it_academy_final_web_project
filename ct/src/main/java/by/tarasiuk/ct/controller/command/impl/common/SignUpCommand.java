package by.tarasiuk.ct.controller.command.impl.common;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.util.MessageKey;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.entity.impl.Token;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.TokenServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_CONFIRM_PASSWORD;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_EMAIL;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_FIRST_NAME;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_LOGIN;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_PASSWORD;
import static by.tarasiuk.ct.controller.command.AttributeName.COMMAND;
import static by.tarasiuk.ct.controller.command.AttributeName.COMPANY_NAME;
import static by.tarasiuk.ct.controller.command.AttributeName.INFORMATION_MESSAGE;
import static by.tarasiuk.ct.controller.command.AttributeName.LOCALE;
import static by.tarasiuk.ct.controller.command.AttributeName.MESSAGE_ERROR_COMPANY_ALREADY_EXIST;
import static by.tarasiuk.ct.controller.command.AttributeName.MESSAGE_ERROR_EMAIL_ALREADY_EXIST;
import static by.tarasiuk.ct.controller.command.AttributeName.MESSAGE_ERROR_LOGIN_ALREADY_EXIST;
import static by.tarasiuk.ct.controller.command.AttributeName.MESSAGE_INCORRECT_SIGN_UP_DATA;
import static by.tarasiuk.ct.controller.command.AttributeName.MESSAGE_QUERY_ERROR;

/**
 * Sign up command
 */
public class SignUpCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();
    private final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();
    private final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private final TokenServiceImpl tokenService = ServiceProvider.getTokenService();

    @Override
    public String execute(RequestContent content) {
        String path;
        Map<String, String> signUpData = content.getRequestParameters();
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        signUpData.remove(COMMAND);

        try {
            if(!accountService.validateSignUpData(signUpData) || !companyService.validateCompany(signUpData)) {
                signUpData.remove(ACCOUNT_PASSWORD);
                signUpData.remove(ACCOUNT_CONFIRM_PASSWORD);

                content.putRequestAttribute(MESSAGE_INCORRECT_SIGN_UP_DATA, true);
                content.putRequestAttributes(signUpData);

                path = PagePath.SIGN_UP;
            } else {
                String login = signUpData.get(ACCOUNT_LOGIN);
                String email = signUpData.get(ACCOUNT_EMAIL);
                String companyName = signUpData.get(COMPANY_NAME);

                Optional<Account> optionalAccountByLogin = accountService.findAccountByLogin(login);
                Optional<Account> optionalAccountByEmail = accountService.findAccountByEmail(email);
                Optional<Company> optionalCompanyByName = companyService.findCompanyByName(companyName);

                if (!optionalAccountByLogin.isPresent() && !optionalAccountByEmail.isPresent() && !optionalCompanyByName.isPresent()) {
                    String locale = (String) sessionAttributes.get(LOCALE);
                    String firstName = signUpData.get(ACCOUNT_FIRST_NAME);
                    content.putRequestAttribute(INFORMATION_MESSAGE, MessageKey.CONFIRM_MESSAGE);

                    companyService.createCompany(signUpData);
                    accountService.createManager(signUpData);

                    Optional<Account> optionalAccount = accountService.findAccountByEmail(email);
                    Optional<Company> optionalCompany = companyService.findCompanyByName(companyName);

                    if(optionalAccount.isPresent() && optionalCompany.isPresent()) {
                        Account account = optionalAccount.get();
                        Company company = optionalCompany.get();

                        long accountId = account.getId();
                        long companyId = company.getId();

                        employeeService.createEmployee(accountId, companyId);
                        tokenService.createToken(accountId);

                        Optional<Token> optionalToken = tokenService.findTokenByAccount(account);

                        if (optionalToken.isPresent()) {
                            Token token = optionalToken.get();
                            String tokenNumber = token.getNumber();
                            accountService.sendActivationEmail(locale, firstName, email, tokenNumber);
                        }
                    }

                    path = PagePath.INFO;
                } else {
                    if (optionalAccountByLogin.isPresent()) {
                        content.putRequestAttribute(MESSAGE_ERROR_LOGIN_ALREADY_EXIST, true);
                        LOGGER.info("Account with login '{}' exist in the database.", login);
                    }

                    if (optionalAccountByEmail.isPresent()) {
                        content.putRequestAttribute(MESSAGE_ERROR_EMAIL_ALREADY_EXIST, true);
                        LOGGER.info("Account with email '{}' exist in the database.", email);
                    }

                    if (optionalCompanyByName.isPresent()) {
                        content.putRequestAttribute(MESSAGE_ERROR_COMPANY_ALREADY_EXIST, true);
                        LOGGER.info("Company with name '{}' exist in the database.", companyName);
                    }

                    content.putRequestAttributes(signUpData);
                    path = PagePath.SIGN_UP;
                }
            }
        } catch (ServiceException e) {
            LOGGER.warn("Can't sign up: '{}'.", signUpData, e);
            content.putRequestAttribute(MESSAGE_QUERY_ERROR, true);
            path = PagePath.SIGN_UP;
        }

        return path;
    }
}
