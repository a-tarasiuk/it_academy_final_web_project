package by.tarasiuk.ct.controller.command;

import by.tarasiuk.ct.controller.RequestContent;

/**
 * Functional Interface for commands executing.
 */
@FunctionalInterface
public interface Command {
    /**
     * @param content   Request data content.
     * @return          Page path.
     * @see             by.tarasiuk.ct.controller.RequestContent
     */
    String execute(RequestContent content);
}
