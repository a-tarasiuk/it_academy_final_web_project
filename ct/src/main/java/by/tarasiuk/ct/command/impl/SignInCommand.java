package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.command.CommandType;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.entity.impl.Account;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.*;

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = new AccountServiceImpl();

    @Override
    public String execute(RequestContent content) {
        String page;
        HashMap<String, String> request = content.getRequestParameters();
        String login = request.get(ACCOUNT_LOGIN);
        String password = request.get(ACCOUNT_PASSWORD);

        try {
            if(!accountService.validateSignInData(login, password)) {
                content.putRequestAttribute(ACCOUNT_LOGIN, login);
                content.putRequestAttribute(SHOW_MESSAGE_INCORRECT_SIGN_IN_DATA, true);
                page = PagePath.SIGN_IN;

                LOGGER.info("Invalid sign in data for login '{}'.", login);
            } else {
                Optional<Account> optionalAccount = accountService.signIn(login, password);

                if(optionalAccount.isPresent()) {
                    Account account = optionalAccount.get();
                    Account.Status status = account.getStatus();

                    switch (status) {
                        case ACTIVATED:
                            content.putSessionAttribute(ACCOUNT, account);
                            page = PagePath.MAIN;
                            LOGGER.info("Sign in successfully for account with login '{}' (status '{}', role '{}').", login, status, account.getRole());
                            break;
                        case BANNED:
                            content.putRequestAttribute(SHOW_MESSAGE_BANNED_ACCOUNT, true);
                            page = PagePath.SIGN_IN;
                            LOGGER.info("Failed to sign in for account with login '{}' because status '{}'.", login, status);
                            break;
                        case NOT_ACTIVATED:
                            content.putRequestAttribute(SHOW_MESSAGE_NOT_ACTIVATED_ACCOUNT, true);
                            page = PagePath.SIGN_IN;
                            LOGGER.info("Failed to sign in for account with login '{}' because status '{}'.", login, status);
                            break;
                        default:
                            content.putRequestAttribute(SHOW_MESSAGE_QUERY_ERROR, true);
                            throw new EnumConstantNotPresentException(status.getClass(), status.toString()); //fixme Need an exception?
                    }
                } else {
                    content.putRequestAttribute(SHOW_MESSAGE_INCORRECT_SIGN_IN_DATA, true);
                    page = PagePath.SIGN_IN;
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SIGN_IN);
            page = PagePath.SIGN_IN;
        }

        return page;
    }
}
