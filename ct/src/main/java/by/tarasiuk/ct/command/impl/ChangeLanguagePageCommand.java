package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.manager.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

import static by.tarasiuk.ct.manager.RequestAttribute.*;

public class ChangeLanguagePageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(RequestContent requestContent) {
        HashMap<String, Object> session = requestContent.getSessionAttributes();

        String locale = (String) session.get(LOCALE_PAGE);

        if (LOCALE_RU.equalsIgnoreCase(locale)) {
            requestContent.putSessionAttribute(LOCALE_PAGE, LOCALE_EN);
        } else {
            requestContent.putSessionAttribute(LOCALE_PAGE, LOCALE_RU);
        }

        LOGGER.info("Locale page change to '{}'.", locale);

        return PagePath.MAIN;   //todo как получить адрес страницы, из которой пришёл юзер? Чтобы перенаправить его пять туда же.
    }
}
