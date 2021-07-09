package by.tarasiuk.ct.command;


import jakarta.servlet.http.HttpServletRequest;

/**
 * String - address page for which you need to go
 */

public interface Command {
    String execute(HttpServletRequest request);

    default void refresh() {
        // метод для возвращения на ту же самую страницу, это защита от F5
        /* code */
    }
}
