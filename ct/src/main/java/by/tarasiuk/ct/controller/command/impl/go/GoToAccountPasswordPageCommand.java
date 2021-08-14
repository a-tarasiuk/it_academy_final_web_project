package by.tarasiuk.ct.controller.command.impl.go;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.command.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoToAccountPasswordPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(RequestContent requestContent) {
        LOGGER.info("Command '{}' return path '{}'", CommandType.GO_TO_ACCOUNT_PASSWORD_PAGE, PagePath.ACCOUNT_PASSWORD);
        return PagePath.ACCOUNT_PASSWORD;
    }
}
