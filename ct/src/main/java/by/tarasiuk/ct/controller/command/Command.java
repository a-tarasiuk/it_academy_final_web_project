package by.tarasiuk.ct.controller.command;

import by.tarasiuk.ct.controller.RequestContent;

/**
 * Interface for commands executing.
 */
@FunctionalInterface
public interface Command {
    String execute(RequestContent content);
}
