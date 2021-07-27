package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.command.CommandType;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Optional;

import static by.tarasiuk.ct.manager.RequestAttribute.*;

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = new AccountServiceImpl();

    @Override
    public String execute(RequestContent content) {
        String goToPage;
        HashMap<String, String> request = content.getRequestParameters();
        String login = request.get(ACCOUNT_LOGIN);
        String password = request.get(ACCOUNT_PASSWORD);

        if(!accountService.validateSignInData(login, password)) {
            content.putRequestAttribute(ACCOUNT_LOGIN, login);
            content.putRequestAttribute(MESSAGE_INCORRECT_SIGN_IN_DATA, true);
            goToPage = PagePath.SIGN_IN;

            LOGGER.info("Invalid sign in data for login '{}'.", login);
        } else {
            try {
                Optional<Account> optionalAccount = accountService.signIn(login, password);

                if(optionalAccount.isPresent()) {
                    Account account = optionalAccount.get();
                    Account.Status status = account.getStatus();

                    switch (status) {
                        case ACTIVATED:
                            goToPage = PagePath.MAIN;
                            break;
                        case BANNED:
                        case NOT_ACTIVATED:
                            goToPage = PagePath.INFO;
                            break;
                        default:
                            content.putRequestAttribute(MESSAGE_QUERY_ERROR, true);
                            throw new EnumConstantNotPresentException(status.getClass(), status.toString()); //fixme Need an exception?
                    }

                    LOGGER.info("Sign in successfully for account with login '{}' (status '{}', role '{}').", login, status, account.getRole());
                    content.putSessionAttribute(ACCOUNT, account);
                } else {
                    content.putRequestAttribute(MESSAGE_INCORRECT_SIGN_IN_DATA, true);
                    goToPage = PagePath.SIGN_IN;
                }
            } catch (ServiceException e) {
                LOGGER.error("Failed to process the command '{}'.", CommandType.SIGN_IN);
                goToPage = PagePath.SIGN_IN;
            }
        }

        return goToPage;
    }
}
