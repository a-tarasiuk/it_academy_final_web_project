package by.tarasiuk.ct.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private static final String LOCALE_FILE_NAME = "locale";
    private static ResourceBundle bundle = ResourceBundle.getBundle("locale");

    public static MessageManager getInstance() {
        return new MessageManager();
    }

    public String findMassage(String key, Locale locale) {
        return ResourceBundle.getBundle(LOCALE_FILE_NAME, locale).getString(key);
    }
}
