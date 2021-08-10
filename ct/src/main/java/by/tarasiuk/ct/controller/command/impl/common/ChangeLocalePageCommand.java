package by.tarasiuk.ct.controller.command.impl.common;

import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.manager.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

import static by.tarasiuk.ct.manager.AttributeName.LOCALE;

public class ChangeLocalePageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(RequestContent content) {
        HashMap<String, String> requestParameters = content.getRequestParameters();
        HashMap<String, Object> sessionAttributes = content.getSessionAttributes();

        String newLocale = requestParameters.get(LOCALE);
        String oldLocale = (String) sessionAttributes.get(LOCALE);

        content.putSessionAttribute(LOCALE, newLocale);

        LOGGER.info("Locale page change from '{}' to '{}'.", oldLocale, newLocale);
        return PagePath.MAIN;   //todo как получить адрес страницы, из которой пришёл юзер? Чтобы перенаправить его пять туда же.
    }
}
