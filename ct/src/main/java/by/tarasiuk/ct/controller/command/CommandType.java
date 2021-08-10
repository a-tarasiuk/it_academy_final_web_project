package by.tarasiuk.ct.controller.command;

import by.tarasiuk.ct.controller.command.impl.account.ActivateAccountCommand;
import by.tarasiuk.ct.controller.command.impl.common.ChangeLocalePageCommand;
import by.tarasiuk.ct.controller.command.impl.offer.*;
import by.tarasiuk.ct.controller.command.impl.common.LogoutCommand;
import by.tarasiuk.ct.controller.command.impl.common.SignInCommand;
import by.tarasiuk.ct.controller.command.impl.common.SignUpCommand;
import by.tarasiuk.ct.controller.command.impl.go.*;

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
    GO_TO_ACCOUNT_EMPLOYEES_PAGE(new GoToAccountEmployeesPageCommand()),
    SHOW_OPEN_OFFERS(new ShowOpenOffersCommand()),
    SHOW_TRADING_OFFER(new ShowTradingOfferCommand()),
    SHOW_ACCOUNT_OFFERS(new ShowAccountOffersCommand()),
    SHOW_ACCOUNT_TRADINGS(new ShowAccountTradingsCommand()),
    CREATE_TRADING(new CreateTradingCommand());

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
