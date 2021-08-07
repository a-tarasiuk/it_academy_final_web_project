package by.tarasiuk.ct.controller.command;

import by.tarasiuk.ct.controller.RequestContent;

/**
 * String - address page for which you need to go
 */

public interface Command {
    String execute(RequestContent content);

    default void refresh() {
        // метод для возвращения на ту же самую страницу, это защита от F5
        /* code */
    }
}
