package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.entity.impl.Account;
import by.tarasiuk.ct.entity.impl.Token;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.TokenServiceImpl;
import by.tarasiuk.ct.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT_EMAIL;
import static by.tarasiuk.ct.manager.AttributeName.LOCALE;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE;
import static by.tarasiuk.ct.manager.AttributeName.TOKEN_NUMBER;

public class ActivateAccountCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String MESSAGE_NOT_EXIST_ACCOUNT = "message.notExist.email";
    private static final String MESSAGE_NOT_EXIST_TOKEN = "message.notExist.token";
    private static final String MESSAGE_INCORRECT_TOKEN = "message.incorrect.token";
    private static final String MESSAGE_ALREADY_ACTIVATED = "message.account.alreadyActivated";
    private static final String MESSAGE_SUCCESSFULLY_ACTIVATED = "message.activated.successfullyActivated";
    private static final String MESSAGE_QUERY_ERROR = "message.query.error";
    private static final AccountServiceImpl accountService = ServiceProvider.getAccountService();
    private static final TokenServiceImpl tokenService = ServiceProvider.getTokenService();

    @Override
    public String execute(RequestContent content) {
        Map<String, String> requestParameters = content.getRequestParameters();
        HashMap<String, Object> sessionAttributes = content.getSessionAttributes();

        String message;
        String formatMessage;
        String locale = (String) sessionAttributes.get(LOCALE);
        String email = requestParameters.get(ACCOUNT_EMAIL);
        String requestTokenNumber = requestParameters.get(TOKEN_NUMBER);

        try {
            Optional<Account> optionalAccount = accountService.findAccountByEmail(email);

            if(!optionalAccount.isPresent()) {
                formatMessage = MessageManager.findMassage(MESSAGE_NOT_EXIST_ACCOUNT, locale);
            } else {
                Account account = optionalAccount.get();

                Optional<Token> optionalToken = tokenService.findTokenByAccount(account);

                if(!optionalToken.isPresent()) {
                    formatMessage = MessageManager.findMassage(MESSAGE_NOT_EXIST_TOKEN, locale);
                } else {
                    Token token = optionalToken.get();
                    String foundTokenNumber = token.getNumber();
                    LOGGER.info("Token number from request '{}', from database '{}', is equals: {}.",
                            requestTokenNumber, foundTokenNumber, requestTokenNumber.equals(foundTokenNumber));

                    if(!foundTokenNumber.equals(requestTokenNumber)) {
                        formatMessage = MessageManager.findMassage(MESSAGE_INCORRECT_TOKEN, locale);
                        LOGGER.info("The token number from request '{}' doesn't match the token number in the database '{}'.",
                                requestTokenNumber, foundTokenNumber);
                    } else {
                        Token.Status tokenStatus = token.getStatus();

                        switch (tokenStatus) {
                            case CONFIRMED:
                                formatMessage = MessageManager.findMassage(MESSAGE_ALREADY_ACTIVATED, locale);
                                break;
                            case UNCONFIRMED:
                                Token.Status status = Token.Status.CONFIRMED;
                                tokenService.changeTokenStatus(token, status);

                                Account.Status accountStatus = Account.Status.ACTIVATED;
                                accountService.changeAccountStatus(account, accountStatus);

                                content.putSessionAttribute(ACCOUNT, account);
                                formatMessage = MessageManager.findMassage(MESSAGE_SUCCESSFULLY_ACTIVATED, locale);
                                break;
                            default:
                                LOGGER.warn("Nonexistent constant '{}' in '{}'.", tokenStatus, tokenStatus.getDeclaringClass());
                                throw new EnumConstantNotPresentException(tokenStatus.getClass(), tokenStatus.toString()); //fixme Need an exception?
                        }
                    }
                }
            }
        } catch (ServiceException e) {
            formatMessage = MessageManager.findMassage(MESSAGE_QUERY_ERROR, locale);
            LOGGER.warn("Error when activating email '{}'.", email);
        }

        message = String.format(formatMessage, email);
        content.putRequestAttribute(MESSAGE, message);

        return PagePath.INFO;
    }
}
