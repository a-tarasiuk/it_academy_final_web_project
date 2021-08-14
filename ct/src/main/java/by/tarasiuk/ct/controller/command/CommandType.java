package by.tarasiuk.ct.controller.command;

import by.tarasiuk.ct.controller.command.impl.account.ActivateAccountCommand;
import by.tarasiuk.ct.controller.command.impl.account.ShowAccountSettingsPageCommand;
import by.tarasiuk.ct.controller.command.impl.account.UpdateAccountCommand;
import by.tarasiuk.ct.controller.command.impl.common.ChangeLocalePageCommand;
import by.tarasiuk.ct.controller.command.impl.common.LogoutCommand;
import by.tarasiuk.ct.controller.command.impl.common.SignInCommand;
import by.tarasiuk.ct.controller.command.impl.common.SignUpCommand;
import by.tarasiuk.ct.controller.command.impl.company.ShowCompanySettingsPageCommand;
import by.tarasiuk.ct.controller.command.impl.company.UpdateCompanyCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToAccountEmployeesPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToAccountPasswordPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToCreateOfferPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToMainPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToSignInPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToSignUpPageCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ApplyFreightCommand;
import by.tarasiuk.ct.controller.command.impl.offer.CreateOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.CreateTradingCommand;
import by.tarasiuk.ct.controller.command.impl.offer.DeactivateOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowAccountListOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowAccountOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowAccountTradingsCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowOfferEditorCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowOpenOffersCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowTradingOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowTradingViewerCommand;
import by.tarasiuk.ct.controller.command.impl.offer.UpdateOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.UpdatePasswordCommand;

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
    GO_TO_ACCOUNT_PASSWORD_PAGE(new GoToAccountPasswordPageCommand()),
    SHOW_ACCOUNT_SETTINGS_PAGE(new ShowAccountSettingsPageCommand()),
    SHOW_OPEN_OFFERS(new ShowOpenOffersCommand()),
    SHOW_OFFER_EDITOR(new ShowOfferEditorCommand()),
    SHOW_TRADING_OFFER(new ShowTradingOfferCommand()),
    SHOW_TRADING_VIEWER(new ShowTradingViewerCommand()),
    SHOW_ACCOUNT_OFFERS(new ShowAccountListOfferCommand()),
    SHOW_ACCOUNT_OFFER(new ShowAccountOfferCommand()),
    SHOW_ACCOUNT_TRADINGS(new ShowAccountTradingsCommand()),
    SHOW_COMPANY_SETTINGS_PAGE(new ShowCompanySettingsPageCommand()),
    DEACTIVATE_OFFER(new DeactivateOfferCommand()),
    CREATE_TRADING(new CreateTradingCommand()),
    APPLY_FREIGHT(new ApplyFreightCommand()),
    UPDATE_ACCOUNT(new UpdateAccountCommand()),
    UPDATE_PASSWORD(new UpdatePasswordCommand()),
    UPDATE_COMPANY(new UpdateCompanyCommand()),
    UPDATE_OFFER(new UpdateOfferCommand());

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
