package by.tarasiuk.ct.controller.command.impl.account;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT_CONFIRM_PASSWORD;
import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT_EMAIL;
import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT_LOGIN;
import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT_PASSWORD;
import static by.tarasiuk.ct.manager.AttributeName.COMPANY_NAME;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_ERROR_COMPANY_ALREADY_EXIST;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_ERROR_EMAIL_ALREADY_EXIST;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_ERROR_LOGIN_ALREADY_EXIST;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_INCORRECT_SIGN_UP_DATA;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_QUERY_ERROR;


public class UpdateAccountCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AccountServiceImpl accountService = ServiceProvider.getAccountService();
    private static final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();;

    @Override
    public String execute(RequestContent content) {
        String page;
        HashMap<String, String> requestParameters = content.getRequestParameters();
        HashMap<String, Object> sessionAttributes = content.getSessionAttributes();

        try {
            if(!accountService.validateSignUpData(requestParameters) || !companyService.validateCompany(requestParameters)) {
                requestParameters.remove(ACCOUNT_PASSWORD);
                requestParameters.remove(ACCOUNT_CONFIRM_PASSWORD);

                content.putRequestAttribute(MESSAGE_INCORRECT_SIGN_UP_DATA, true);
                content.putRequestAttributes(requestParameters);

                page = PagePath.ACCOUNT_SETTINGS;
            } else {
                String login = requestParameters.get(ACCOUNT_LOGIN);
                String email = requestParameters.get(ACCOUNT_EMAIL);
                String companyName = requestParameters.get(COMPANY_NAME);

                Optional<Account> optionalAccountByLogin = accountService.findAccountByLogin(login);
                Optional<Account> optionalAccountByEmail = accountService.findAccountByEmail(email);
                Optional<Company> optionalCompanyByName = companyService.findCompanyByName(companyName);

                if (!optionalAccountByLogin.isPresent() && !optionalAccountByEmail.isPresent() && !optionalCompanyByName.isPresent()) {
                    companyService.createCompany(requestParameters);
                    accountService.createAccount(requestParameters);

                    page = PagePath.INFO;
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

                    content.putRequestAttributes(requestParameters);
                    page = PagePath.MAIN;
                }
            }
        } catch (ServiceException e) {
            LOGGER.warn("Can't sign up: '{}'.", requestParameters, e);
            content.putRequestAttribute(MESSAGE_QUERY_ERROR, true);
            page = PagePath.ACCOUNT_SETTINGS;
        }

        return page;
    }
}
