package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.manager.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Locale;

import static by.tarasiuk.ct.manager.AttributeName.LOCALE;

public class ChangeLocalePageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(RequestContent requestContent) {
        HashMap<String, String> requestParameters = requestContent.getRequestParameters();

        Locale oldLocale = requestContent.getLocale();
        String newLocale = requestParameters.get(LOCALE);
        LOGGER.info("Locale from JSP request: '{}'.", newLocale);
        requestContent.putSessionAttribute(LOCALE, newLocale);

        LOGGER.info("Locale page change from '{}' to '{}'.", oldLocale, newLocale);
        return PagePath.MAIN;   //todo как получить адрес страницы, из которой пришёл юзер? Чтобы перенаправить его пять туда же.
    }
}
