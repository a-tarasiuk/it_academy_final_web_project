package by.tarasiuk.ct.controller.command.impl.common;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;

/**
 * Logout command for account.
 */
public class LogoutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * The account object is retrieved from the session and logged out.
     *
     * @param content   Request data content.
     * @return          Page path.
     * @see             by.tarasiuk.ct.controller.RequestContent
     */
    @Override
    public String execute(RequestContent content) {
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        Account account = (Account) sessionAttributes.get(AttributeName.ACCOUNT);
        String login = account.getLogin();
        content.invalidateSession();

        LOGGER.info("Account with login '{}' logout successfully!", login);
        return PagePath.INDEX;
    }
}
