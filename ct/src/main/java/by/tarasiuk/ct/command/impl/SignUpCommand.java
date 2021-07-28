package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.entity.Company;
import by.tarasiuk.ct.entity.Token;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.service.TokenService;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.TokenServiceImpl;
import by.tarasiuk.ct.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT_CONFIRM_PASSWORD;
import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT_EMAIL;
import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT_FIRST_NAME;
import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT_LOGIN;
import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT_PASSWORD;
import static by.tarasiuk.ct.manager.AttributeName.COMMAND;
import static by.tarasiuk.ct.manager.AttributeName.COMPANY_NAME;
import static by.tarasiuk.ct.manager.AttributeName.LOCALE_EN_US;
import static by.tarasiuk.ct.manager.AttributeName.LOCALE_PAGE;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_ERROR_COMPANY_ALREADY_EXIST;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_ERROR_EMAIL_ALREADY_EXIST;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_ERROR_LOGIN_ALREADY_EXIST;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_INCORRECT_SIGN_UP_DATA;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_QUERY_ERROR;

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
                Optional<Company> optionalCompany = companyService.findCompanyByName(companyName);

                if (!optionalAccountByLogin.isPresent() && !optionalAccountByEmail.isPresent() && !optionalCompany.isPresent()) {
                    TokenService tokenService = new TokenServiceImpl();

                    Optional<Object> localePage = content.findSessionAttribute(LOCALE_PAGE);
                    String locale = localePage.map(o -> (String) o).orElse(LOCALE_EN_US);
                    String firstName = signUpData.get(ACCOUNT_FIRST_NAME);
                    String formatMessage = MessageManager.getInstance().findMassage(CONFIRM_MESSAGE, locale);
                    String pageMessage = String.format(formatMessage, firstName, email);
                    content.putRequestAttribute(MESSAGE, pageMessage);

                    companyService.createNewCompany(signUpData);
                    accountService.createNewAccount(signUpData);

                    Optional<Account> optionalAccount = accountService.findAccountByEmail(email);
                    if(optionalAccount.isPresent()) {
                        Account account = optionalAccount.get();
                        long accountId = account.getId();

                        tokenService.createToken(accountId);

                        Optional<Token> optionalToken = tokenService.findTokenByAccountId(accountId);
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

                    if (optionalCompany.isPresent()) {
                        content.putRequestAttribute(MESSAGE_ERROR_COMPANY_ALREADY_EXIST, true);
                        LOGGER.info("Company with name '{}' exist in the database.", companyName);
                    }

                    content.putRequestAttributes(signUpData);
                    path = PagePath.SIGN_UP;
                }
            }
        } catch (ServiceException e) {
            LOGGER.warn("Can't sign up: '{}'.", signUpData);
            content.putRequestAttribute(MESSAGE_QUERY_ERROR, true);
            path = PagePath.SIGN_UP;
        }

        return path;
    }
}
