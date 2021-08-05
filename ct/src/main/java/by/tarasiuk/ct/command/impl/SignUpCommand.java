package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.entity.impl.Account;
import by.tarasiuk.ct.entity.impl.Company;
import by.tarasiuk.ct.entity.impl.Token;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.TokenServiceImpl;
import by.tarasiuk.ct.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.*;

public class SignUpCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CONFIRM_MESSAGE = "form.signUp.confirmEmail";
    private static final AccountServiceImpl accountService = ServiceProvider.getAccountService();
    private static final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();
    private static final TokenServiceImpl tokenService = ServiceProvider.getTokenService();

    @Override
    public String execute(RequestContent content) {
        String path;
        HashMap<String, String> signUpData = content.getRequestParameters();
        HashMap<String, Object> sessionAttributes = content.getSessionAttributes();
        signUpData.remove(COMMAND);

        try {
            if(!accountService.validateSignUpData(signUpData) || !companyService.validateCompany(signUpData)) {
                signUpData.remove(ACCOUNT_PASSWORD);
                signUpData.remove(ACCOUNT_CONFIRM_PASSWORD);

                content.putRequestAttribute(SHOW_MESSAGE_INCORRECT_SIGN_UP_DATA, true);
                content.putRequestAttributes(signUpData);

                path = PagePath.SIGN_UP;
            } else {
                String login = signUpData.get(ACCOUNT_LOGIN);
                String email = signUpData.get(ACCOUNT_EMAIL);
                String companyName = signUpData.get(COMPANY_NAME);

                Optional<Account> optionalAccountByLogin = accountService.findAccountByLogin(login);
                Optional<Account> optionalAccountByEmail = accountService.findAccountByEmail(email);
                Optional<Company> optionalCompany = companyService.findCompanyByName(companyName);

                if (!optionalAccountByLogin.isPresent() && !optionalAccountByEmail.isPresent() && !optionalCompany.isPresent()) {
                    String locale = (String) sessionAttributes.get(LOCALE);
                    String firstName = signUpData.get(ACCOUNT_FIRST_NAME);
                    String formatMessage = MessageManager.findMassage(CONFIRM_MESSAGE, locale);
                    String pageMessage = String.format(formatMessage, firstName, email);
                    content.putRequestAttribute(MESSAGE, pageMessage);

                    companyService.createNewCompany(signUpData);
                    accountService.signUp(signUpData);

                    Optional<Account> optionalAccount = accountService.findAccountByEmail(email);
                    if(optionalAccount.isPresent()) {
                        Account account = optionalAccount.get();
                        long accountId = account.getId();
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
                        content.putRequestAttribute(SHOW_MESSAGE_ERROR_LOGIN_ALREADY_EXIST, true);
                        LOGGER.info("Account with login '{}' exist in the database.", login);
                    }

                    if (optionalAccountByEmail.isPresent()) {
                        content.putRequestAttribute(SHOW_MESSAGE_ERROR_EMAIL_ALREADY_EXIST, true);
                        LOGGER.info("Account with email '{}' exist in the database.", email);
                    }

                    if (optionalCompany.isPresent()) {
                        content.putRequestAttribute(SHOW_MESSAGE_ERROR_COMPANY_ALREADY_EXIST, true);
                        LOGGER.info("Company with name '{}' exist in the database.", companyName);
                    }

                    content.putRequestAttributes(signUpData);
                    path = PagePath.SIGN_UP;
                }
            }
        } catch (ServiceException e) {
            LOGGER.warn("Can't sign up: '{}'.", signUpData);
            content.putRequestAttribute(SHOW_MESSAGE_QUERY_ERROR, true);
            path = PagePath.SIGN_UP;
        }

        return path;
    }
}
