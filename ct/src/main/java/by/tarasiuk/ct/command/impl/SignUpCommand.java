package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Optional;

import static by.tarasiuk.ct.manager.RequestAttribute.*;

public class SignUpCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CONFIRM_MESSAGE = "form.signUp.confirmEmail";

    @Override
    public String execute(RequestContent content) {
        String path;
        HashMap<String, String> signUpData = content.getRequestParameters();
        signUpData.remove(COMMAND);

        AccountServiceImpl accountService = new AccountServiceImpl();
        CompanyServiceImpl companyService = new CompanyServiceImpl();
        
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

            try {
                boolean isFoundLogin = accountService.isExistLogin(login);
                boolean isFoundEmail = accountService.isExistEmail(email);
                boolean isFoundCompany = companyService.isExistCompany(companyName);

                if(!isFoundLogin && !isFoundEmail && !isFoundCompany) {
                    Optional<Object> localePage = content.findSessionAttribute(LOCALE_PAGE);
                    String locale = localePage.map(o -> (String) o).orElse(LOCALE_EN);
                    String firstName = signUpData.get(ACCOUNT_FIRST_NAME);
                    String formatMessage = MessageManager.getInstance().getMassage(CONFIRM_MESSAGE, locale);
                    String pageMessage = String.format(formatMessage, firstName, email);
                    content.putRequestAttribute(MESSAGE, pageMessage);

                    companyService.createNewCompany(signUpData);
                    accountService.createNewAccount(signUpData);
                    accountService.sendActivationEmail(locale, firstName, email);

                    path = PagePath.INFO;
                } else {
                    if(isFoundLogin) {
                        content.putRequestAttribute(MESSAGE_ERROR_LOGIN_ALREADY_EXIST, true);
                        LOGGER.info("Account with login '{}' exist in the database.", login);
                    }

                    if(isFoundEmail) {
                        content.putRequestAttribute(MESSAGE_ERROR_EMAIL_ALREADY_EXIST, true);
                        LOGGER.info("Account with email '{}' exist in the database.", email);
                    }

                    if(isFoundCompany) {
                        content.putRequestAttribute(MESSAGE_ERROR_COMPANY_ALREADY_EXIST, true);
                        LOGGER.info("Company with name '{}' exist in the database.", companyName);
                    }

                    content.putRequestAttributes(signUpData);
                    path = PagePath.SIGN_UP;
                }

            } catch (ServiceException e) {
                LOGGER.warn("Can't sign up: '{}'.", signUpData);
                content.putRequestAttribute(MESSAGE_QUERY_ERROR, true);
                path = PagePath.SIGN_UP;
            }
        }

        return path;
    }
}
