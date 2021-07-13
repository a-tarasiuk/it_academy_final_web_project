package by.tarasiuk.ct.command;

import by.tarasiuk.ct.command.impl.ChangeLanguagePageCommand;
import by.tarasiuk.ct.command.impl.GoToMainPageCommand;
import by.tarasiuk.ct.command.impl.GoToSignInPageCommand;
import by.tarasiuk.ct.command.impl.GoToSignUpPageCommand;
import by.tarasiuk.ct.command.impl.SignInCommand;
import by.tarasiuk.ct.command.impl.SignUpCommand;
import by.tarasiuk.ct.command.impl.LogoutCommand;

/**
 * All available commands of the application
 * @see Filter // todo
 *
 */
public enum CommandType {
    CHANGE_LOCALE_PAGE(new ChangeLanguagePageCommand()),
    GO_TO_MAIN_PAGE(new GoToMainPageCommand()),
    GO_TO_SIGN_IN_PAGE(new GoToSignInPageCommand()),
    GO_TO_SIGN_UP_PAGE(new GoToSignUpPageCommand()),
    SIGN_IN(new SignInCommand()),
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
