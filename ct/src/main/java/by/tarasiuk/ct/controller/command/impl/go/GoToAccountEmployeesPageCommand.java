package by.tarasiuk.ct.controller.command.impl.go;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.command.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Go to the account employees page command.
 */
public class GoToAccountEmployeesPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Direction to a page with a forwarders list.
     * 
     * @param content   Request data content.
     * @return          Page path.
     * @see             by.tarasiuk.ct.controller.RequestContent
     */
    @Override
    public String execute(RequestContent content) {
        LOGGER.info("Command '{}' return path '{}'", CommandType.GO_TO_ACCOUNT_EMPLOYEES_PAGE, PagePath.ACCOUNT_FORWARDERS);
        return PagePath.ACCOUNT_FORWARDERS;
    }
}
