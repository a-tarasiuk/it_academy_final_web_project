package by.tarasiuk.ct.util;

import java.util.ResourceBundle;

public class LocaleManager {
    private static final ResourceBundle en = ResourceBundle.getBundle("locale");
    private static final ResourceBundle ru = ResourceBundle.getBundle("locale_ru");

    private LocaleManager() {
    }

    public static String getProperty(String key, String locale) {
        return null;
    }
}
