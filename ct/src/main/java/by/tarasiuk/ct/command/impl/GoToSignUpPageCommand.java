package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.command.CommandType;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.manager.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoToSignUpPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(RequestContent requestContent) {
        LOGGER.info("Command '{}' return path '{}'", CommandType.GO_TO_SIGN_UP_PAGE, PagePath.SIGN_UP);
        return PagePath.SIGN_UP;
    }
}
