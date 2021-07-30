package by.tarasiuk.ct.command;

import by.tarasiuk.ct.command.impl.*;

/**
 * All available commands of the application
 * @see Filter // todo
 *
 */
public enum CommandType {
    CHANGE_LOCALE_PAGE(new ChangeLocalePageCommand()),
    GO_TO_MAIN_PAGE(new GoToMainPageCommand()),
    GO_TO_SIGN_IN_PAGE(new GoToSignInPageCommand()),
    GO_TO_SIGN_UP_PAGE(new GoToSignUpPageCommand()),
    SIGN_IN(new SignInCommand()),
    ACTIVATE_ACCOUNT(new ActivateAccountCommand()),
    SIGN_UP(new SignUpCommand()),
    LOGOUT(new LogoutCommand());

    /**
     * Command.
     *
     */
    private final Command command;

    /**
     * Package access constructor.
     *
     * @param command {@link Command}
     */
    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return command.
     */
    public Command getCommand() {
        return this.command;
    }
}
