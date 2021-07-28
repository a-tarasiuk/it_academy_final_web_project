package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.entity.Account;
import by.tarasiuk.ct.entity.Token;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.TokenServiceImpl;
import by.tarasiuk.ct.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.*;

public class ActivateAccountCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String MESSAGE_NOT_EXIST_ACCOUNT = "message.notExist.email";
    private static final String MESSAGE_NOT_EXIST_TOKEN = "message.notExist.token";
    private static final String MESSAGE_INCORRECT_TOKEN = "message.incorrect.token";
    private static final String MESSAGE_ALREADY_ACTIVATED = "message.account.alreadyActivated";
    private static final String MESSAGE_SUCCESSFULLY_ACTIVATED = "message.activated.successfullyActivated";

    @Override
    public String execute(RequestContent content) {
        String page;

        Map<String, String> requestParameters = content.getRequestParameters();
        Map<String, Object> sessionAttributes = content.getSessionAttributes();

        String locale = (String) sessionAttributes.get(LOCALE_PAGE);
        String email = requestParameters.get(ACCOUNT_EMAIL);
        String tokenNumber = requestParameters.get(TOKEN_NUMBER);

        LOGGER.info("CURRENT LOCALE PAGE: {}.", content.getLocale());

        AccountServiceImpl accountService = new AccountServiceImpl();
        try {
            Optional<Account> optionalAccount = accountService.findAccountByEmail(email);

            if(!optionalAccount.isPresent()) {
                String formatMessage = MessageManager.getInstance().findMassage(MESSAGE_NOT_EXIST_ACCOUNT, locale);
                String message = String.format(formatMessage, email);
                content.putRequestAttribute(MESSAGE, message);
                page = PagePath.INFO;
            } else {
                String message;
                Account account = optionalAccount.get();
                long accountId = account.getId();
                TokenServiceImpl tokenService = new TokenServiceImpl();
                Optional<Token> optionalToken = tokenService.findTokenByAccountId(accountId);

                if(!optionalToken.isPresent()) {
                    message = String.format(MESSAGE_NOT_EXIST_TOKEN, email);
                    content.putRequestAttribute(MESSAGE, message);
                } else {
                    Token token = optionalToken.get();
                    String foundTokenNumber = token.getNumber();

                    if(!foundTokenNumber.equals(tokenNumber)) {
                        message = String.format(MESSAGE_INCORRECT_TOKEN, email);
                        content.putRequestAttribute(MESSAGE, message);
                    } else {
                        Token.Status status = token.getStatus();

                        switch (status) {
                            case CONFIRMED:
                                message = String.format(MESSAGE_ALREADY_ACTIVATED, email);
                                content.putRequestAttribute(MESSAGE, message);
                                break;
                            case UNCONFIRMED:
                                message = String.format(MESSAGE_SUCCESSFULLY_ACTIVATED, email);
                                long tokenId = token.getId();
                                tokenService.confirmTokenById(tokenId);
                                content.putSessionAttribute(ACCOUNT, account);
                                page = PagePath.ACCOUNT;
                                break;
                            default:
                                message = String.format(MESSAGE_SUCCESSFULLY_ACTIVATED, email);
                                content.putRequestAttribute(MESSAGE, message);
                                //throw new EnumConstantNotPresentException(status.getClass(), status.name());
                                break;
                        }
                    }
                }
                page = PagePath.INFO;
                content.putRequestAttribute(MESSAGE, message);
            }
        } catch (ServiceException e) {
            LOGGER.warn("Error when activating email '{}'.", email);
            content.putRequestAttribute(MESSAGE_QUERY_ERROR, true);
            page = PagePath.SIGN_UP;
        }

        return page;
    }
}
