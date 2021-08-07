package by.tarasiuk.ct.controller.command;

import by.tarasiuk.ct.controller.command.impl.ActivateAccountCommand;
import by.tarasiuk.ct.controller.command.impl.ChangeLocalePageCommand;
import by.tarasiuk.ct.controller.command.impl.CreateOfferCommand;
import by.tarasiuk.ct.controller.command.impl.LogoutCommand;
import by.tarasiuk.ct.controller.command.impl.SignInCommand;
import by.tarasiuk.ct.controller.command.impl.SignUpCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToAccountOffersPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToCreateOfferPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToMainPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToSignInPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToSignUpPageCommand;

/**
 * All available commands of the application
 * @see Filter // todo
 *
 */
public enum CommandType {
    CHANGE_LOCALE_PAGE(new ChangeLocalePageCommand()),
    ACTIVATE_ACCOUNT(new ActivateAccountCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    LOGOUT(new LogoutCommand()),
    CREATE_OFFER(new CreateOfferCommand()),
    GO_TO_MAIN_PAGE(new GoToMainPageCommand()),
    GO_TO_SIGN_IN_PAGE(new GoToSignInPageCommand()),
    GO_TO_SIGN_UP_PAGE(new GoToSignUpPageCommand()),
    GO_TO_CREATE_OFFER_PAGE(new GoToCreateOfferPageCommand()),
    GO_TO_ACCOUNT_OFFERS_PAGE(new GoToAccountOffersPageCommand());

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
