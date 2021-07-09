package by.tarasiuk.ct.util;

import java.util.ResourceBundle;

public class MessageManager {
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.messages");

    // класс извлекает информацию из файла messages.properties
    private MessageManager() {
    }

    public static String getProperty(String key) {
        //return resourceBundle.getString(key);
        return null;
    }
}
