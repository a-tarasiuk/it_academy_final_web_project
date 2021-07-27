package by.tarasiuk.ct.util;

import java.util.Locale;
import java.util.ResourceBundle;

import static by.tarasiuk.ct.manager.RequestAttribute.LOCALE_EN;

public class MessageManager {
    private static final String LOCALE_FILE_NAME = "locale";
    private static ResourceBundle bundle = ResourceBundle.getBundle("locale");

    public static MessageManager getInstance() {
        return new MessageManager();
    }

    public String getMassage(String key, String localePage) {
        Locale locale = (localePage != null && localePage.isEmpty()) ? new Locale(localePage) : new Locale(LOCALE_EN);
        return ResourceBundle.getBundle(LOCALE_FILE_NAME, locale).getString(key);
    }
}
