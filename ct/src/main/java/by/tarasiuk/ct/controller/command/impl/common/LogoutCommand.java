package by.tarasiuk.ct.controller.command.impl.common;

import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.manager.AttributeName;
import by.tarasiuk.ct.manager.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(RequestContent content) {
        HashMap<String, Object> sessionAttributes = content.getSessionAttributes();
        Account account = (Account) sessionAttributes.get(AttributeName.ACCOUNT);
        String login = account.getLogin();

        content.invalidateSession();

        LOGGER.info("Account with login '{}' logout successfully!", login);
        return PagePath.INDEX;
    }
}
