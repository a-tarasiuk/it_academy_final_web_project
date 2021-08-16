package by.tarasiuk.ct.controller.command.impl.go;

import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Go to the main page command.
 */
public class GoToMainPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Direction to the main page.
     * @param content - RequestContent
     * @return account forwarders page
     */
    @Override
    public String execute(RequestContent content) {
        LOGGER.info("Command '{}' return path '{}'", CommandType.GO_TO_MAIN_PAGE, PagePath.MAIN);
        return PagePath.MAIN;
    }
}
