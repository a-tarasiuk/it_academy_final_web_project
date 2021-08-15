package by.tarasiuk.ct.controller.command.impl.account;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Token;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.TokenServiceImpl;
import by.tarasiuk.ct.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT_EMAIL;
import static by.tarasiuk.ct.controller.command.AttributeName.INFORMATION_MESSAGE;
import static by.tarasiuk.ct.controller.command.AttributeName.LOCALE;
import static by.tarasiuk.ct.controller.command.AttributeName.TOKEN_NUMBER;
import static by.tarasiuk.ct.util.MessageKey.ACCOUNT_ALREADY_ACTIVATED;
import static by.tarasiuk.ct.util.MessageKey.ACCOUNT_EMAIL_NOT_EXIST;
import static by.tarasiuk.ct.util.MessageKey.ACCOUNT_SUCCESSFULLY_ACTIVATED;
import static by.tarasiuk.ct.util.MessageKey.MESSAGE_QUERY_ERROR;
import static by.tarasiuk.ct.util.MessageKey.TOKEN_INCORRECT;
import static by.tarasiuk.ct.util.MessageKey.TOKEN_NOT_EXIST;

public class ActivateAccountCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AccountServiceImpl accountService = ServiceProvider.getAccountService();
    private final TokenServiceImpl tokenService = ServiceProvider.getTokenService();

    @Override
    public String execute(RequestContent content) {
        Map<String, String> requestParameters = content.getRequestParameters();
        HashMap<String, Object> sessionAttributes = content.getSessionAttributes();

        String message;
        String locale = (String) sessionAttributes.get(LOCALE);
        String email = requestParameters.get(ACCOUNT_EMAIL);
        String requestTokenNumber = requestParameters.get(TOKEN_NUMBER);

        try {
            Optional<Account> optionalAccount = accountService.findAccountByEmail(email);

            if(!optionalAccount.isPresent()) {
                message = MessageManager.findMassage(ACCOUNT_EMAIL_NOT_EXIST, locale);
            } else {
                Account account = optionalAccount.get();

                Optional<Token> optionalToken = tokenService.findTokenByAccount(account);

                if(!optionalToken.isPresent()) {
                    message = TOKEN_NOT_EXIST;
                } else {
                    Token token = optionalToken.get();
                    String foundTokenNumber = token.getNumber();
                    LOGGER.info("Token number from request '{}', from database '{}', is equals: {}.",
                            requestTokenNumber, foundTokenNumber, requestTokenNumber.equals(foundTokenNumber));

                    if(!foundTokenNumber.equals(requestTokenNumber)) {
                        message = TOKEN_INCORRECT;
                        LOGGER.info("The token number from request '{}' doesn't match the token number in the database '{}'.",
                                requestTokenNumber, foundTokenNumber);
                    } else {
                        Token.Status tokenStatus = token.getStatus();

                        switch (tokenStatus) {
                            case CONFIRMED:
                                message = ACCOUNT_ALREADY_ACTIVATED;
                                LOGGER.warn("Account with email '{}' already activated.", email);
                                break;
                            case UNCONFIRMED:
                                Token.Status status = Token.Status.CONFIRMED;
                                tokenService.changeTokenStatus(token, status);

                                Account.Status accountStatus = Account.Status.ACTIVATED;
                                accountService.changeAccountStatus(account, accountStatus);

                                content.putSessionAttribute(ACCOUNT, account);
                                message = ACCOUNT_SUCCESSFULLY_ACTIVATED;
                                LOGGER.warn("Account with email '{}' successfully activated.", email);
                                break;
                            default:
                                LOGGER.warn("Nonexistent constant '{}' in '{}'.", tokenStatus, tokenStatus.getDeclaringClass());
                                throw new EnumConstantNotPresentException(tokenStatus.getClass(), tokenStatus.toString()); //fixme Need an exception?
                        }
                    }
                }
            }
        } catch (ServiceException e) {
            message = MESSAGE_QUERY_ERROR;
            LOGGER.warn("Error when activating email '{}'.", email);
        }

        content.putRequestAttribute(INFORMATION_MESSAGE, message);

        return PagePath.INFORMATION_MESSAGE;
    }
}
