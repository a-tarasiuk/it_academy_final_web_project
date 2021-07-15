package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.command.CommandType;
import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.entity.AccountStatus;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.manager.RequestAttribute;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.util.AccountValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountServiceImpl = new AccountServiceImpl();

    @Override
    public String execute(HttpServletRequest request) {
        String goToPage = null;
        HttpSession session = request.getSession();

        String login = (String) session.getAttribute(RequestAttribute.USER_LOGIN);
        String password = (String) session.getAttribute(RequestAttribute.USER_PASSWORD);

        if(!AccountValidator.isValidSingInData(login, password)) {
            LOGGER.error("Invalid login '{}' or password '{}'.", login, password);
            request.setAttribute("incorrect_login_or_password_message", "Incorrect login or password! Try again.");
            goToPage = PagePath.SIGN_IN;
        }

        try {
            Optional<Account> optionalAccount = accountServiceImpl.signIn(login, password);

            if(optionalAccount.isPresent()) {
                Account foundAccount = optionalAccount.get();
                AccountStatus accountStatus = foundAccount.getAccountStatus();

                switch (accountStatus) {
                    case ACTIVE:
                        goToPage = PagePath.MAIN;
                        break;
                    case BANNED:
                        goToPage = PagePath.SIGN_IN;
                        break;
                    case UNDER_CONSIDERATION:
                        goToPage = PagePath.MAIN;
                        break;
                    default:
                        goToPage = PagePath.MAIN;
                        //throw new EnumConstantNotPresentException("Status " + accountStatus + " not found!"); //todo Это непроверяемый Exception
                        break;
                }

                LOGGER.info("Account with login '{}' have has status '{}'.", login, accountStatus);
                request.setAttribute(RequestAttribute.USER_LOGIN, login);
            } else {
                request.setAttribute("incorrectLoginOrPasswordMessage", "Fucking!");
                goToPage = PagePath.SIGN_IN;
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SIGN_IN);
            goToPage = PagePath.SIGN_IN;
        }

        return goToPage;
    }
}
