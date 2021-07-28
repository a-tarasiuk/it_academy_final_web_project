package by.tarasiuk.ct.util;

import java.util.Locale;
import java.util.ResourceBundle;

import static by.tarasiuk.ct.manager.AttributeName.LOCALE_EN_US;

public class MessageManager {
    private static final String LOCALE_FILE_NAME = "locale";
    private static ResourceBundle bundle = ResourceBundle.getBundle("locale");

    public static MessageManager getInstance() {
        return new MessageManager();
    }

    public String findMassage(String key, String localePage) {
        Locale locale = (localePage != null && localePage.isEmpty()) ? new Locale(localePage) : new Locale(LOCALE_EN_US);
        return ResourceBundle.getBundle(LOCALE_FILE_NAME, locale).getString(key);
    }
}
