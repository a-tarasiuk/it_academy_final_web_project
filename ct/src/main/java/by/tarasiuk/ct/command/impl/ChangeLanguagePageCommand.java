package by.tarasiuk.ct.command.impl;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.manager.RequestAttribute;
import by.tarasiuk.ct.manager.PagePath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeLanguagePageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String oldLocale = (String) session.getAttribute(RequestAttribute.LOCALE_PAGE);

        String changedLocale;
        switch (oldLocale) {
            case RequestAttribute.LOCALE_EN:
                changedLocale = RequestAttribute.LOCALE_RU;
                break;
            default:
                changedLocale = RequestAttribute.LOCALE_EN;
        }

        session.setAttribute(RequestAttribute.LOCALE_PAGE, changedLocale);
        LOGGER.info("Locale page change from '{}' to '{}'.", oldLocale, changedLocale);

        return PagePath.MAIN;   //todo как получить адрес страницы, из которой пришёл юзер? Чтобы перенаправить его пять туда же.
    }
}
