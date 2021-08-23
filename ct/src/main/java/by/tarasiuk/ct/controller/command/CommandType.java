package by.tarasiuk.ct.controller.command;

import by.tarasiuk.ct.controller.command.impl.account.ActivateAccountCommand;
import by.tarasiuk.ct.controller.command.impl.account.ShowAccountSettingsPageCommand;
import by.tarasiuk.ct.controller.command.impl.account.ShowAccountTradingViewerCommand;
import by.tarasiuk.ct.controller.command.impl.account.UpdateAccountCommand;
import by.tarasiuk.ct.controller.command.impl.admin.BanAccountCommand;
import by.tarasiuk.ct.controller.command.impl.admin.ShowAccountEditorFromAdminCommand;
import by.tarasiuk.ct.controller.command.impl.admin.ShowAccountListFromAdminCommand;
import by.tarasiuk.ct.controller.command.impl.admin.UnbanAccountCommand;
import by.tarasiuk.ct.controller.command.impl.common.ChangeLocalePageCommand;
import by.tarasiuk.ct.controller.command.impl.common.LogoutCommand;
import by.tarasiuk.ct.controller.command.impl.common.SignInCommand;
import by.tarasiuk.ct.controller.command.impl.common.SignUpCommand;
import by.tarasiuk.ct.controller.command.impl.company.ShowCompanySettingsPageCommand;
import by.tarasiuk.ct.controller.command.impl.company.UpdateCompanyCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToAboutPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToAccountEmployeesPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToAccountPasswordPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToContactPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToCreateOfferPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToMainPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToSignInPageCommand;
import by.tarasiuk.ct.controller.command.impl.go.GoToSignUpPageCommand;
import by.tarasiuk.ct.controller.command.impl.manager.CreateForwarderCommand;
import by.tarasiuk.ct.controller.command.impl.manager.ShowForwarderCreatorPageCommand;
import by.tarasiuk.ct.controller.command.impl.manager.ShowForwarderSettingsPageCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ApplyFreightCommand;
import by.tarasiuk.ct.controller.command.impl.offer.CreateOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.CreateTradingCommand;
import by.tarasiuk.ct.controller.command.impl.offer.DeactivateOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowAccountListOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowAccountOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowAccountTradingListCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowOfferEditorCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowOpenOfferListCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowTradingOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.ShowTradingViewerCommand;
import by.tarasiuk.ct.controller.command.impl.offer.UpdateOfferCommand;
import by.tarasiuk.ct.controller.command.impl.offer.UpdatePasswordCommand;

/**
 * All available commands of the application.
 *
 * @see by.tarasiuk.ct.controller.filter.CommandFilter
 */
public enum CommandType {
    /**
     * Command list for all roles.
     */
    GO_TO_MAIN_PAGE(new GoToMainPageCommand()),
    CHANGE_LOCALE_PAGE(new ChangeLocalePageCommand()),
    GO_TO_ABOUT_PAGE(new GoToAboutPageCommand()),
    GO_TO_CONTACT_PAGE(new GoToContactPageCommand()),

    /**
     * Command list for GUEST.
     */
    GO_TO_SIGN_IN_PAGE(new GoToSignInPageCommand()),
    GO_TO_SIGN_UP_PAGE(new GoToSignUpPageCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_UP(new SignUpCommand()),
    ACTIVATE_ACCOUNT(new ActivateAccountCommand()),

    /**
     * Command list for account with role ADMINISTRATOR only.
     */
    BAN_ACCOUNT(new BanAccountCommand()),
    UNBAN_ACCOUNT(new UnbanAccountCommand()),
    SHOW_ACCOUNT_LIST_FROM_ADMIN(new ShowAccountListFromAdminCommand()),
    SHOW_ACCOUNT_EDITOR_FROM_ADMIN(new ShowAccountEditorFromAdminCommand()),

    /**
     * Command list for account with role MANAGER only.
     */
    GO_TO_ACCOUNT_EMPLOYEES_PAGE(new GoToAccountEmployeesPageCommand()),
    SHOW_FORWARDER_CREATOR_PAGE(new ShowForwarderCreatorPageCommand()),
    CREATE_FORWARDER(new CreateForwarderCommand()),
    UPDATE_COMPANY(new UpdateCompanyCommand()),
    SHOW_COMPANY_SETTINGS_PAGE(new ShowCompanySettingsPageCommand()),
    SHOW_FORWARDER_SETTINGS_PAGE(new ShowForwarderSettingsPageCommand()),

    /**
     * Command list for account with role MANAGER and FORWARDER.
     */
    GO_TO_CREATE_OFFER_PAGE(new GoToCreateOfferPageCommand()),
    CREATE_OFFER(new CreateOfferCommand()),
    DEACTIVATE_OFFER(new DeactivateOfferCommand()),
    CREATE_TRADING(new CreateTradingCommand()),
    APPLY_FREIGHT(new ApplyFreightCommand()),
    SHOW_ACCOUNT_OFFERS(new ShowAccountListOfferCommand()),
    SHOW_ACCOUNT_OFFER(new ShowAccountOfferCommand()),
    SHOW_ACCOUNT_TRADINGS(new ShowAccountTradingListCommand()),
    SHOW_OFFER_EDITOR(new ShowOfferEditorCommand()),
    UPDATE_OFFER(new UpdateOfferCommand()),
    SHOW_ACCOUNT_TRADING_VIEWER(new ShowAccountTradingViewerCommand()),

    /**
     * Command list for account with role ADMINISTRATOR, MANAGER and FORWARDER.
     */
    LOGOUT(new LogoutCommand()),
    GO_TO_ACCOUNT_PASSWORD_PAGE(new GoToAccountPasswordPageCommand()),
    SHOW_ACCOUNT_SETTINGS_PAGE(new ShowAccountSettingsPageCommand()),
    UPDATE_ACCOUNT(new UpdateAccountCommand()),
    UPDATE_PASSWORD(new UpdatePasswordCommand()),
    SHOW_TRADING_OFFER(new ShowTradingOfferCommand()),
    SHOW_OPEN_OFFER_LIST(new ShowOpenOfferListCommand()),
    SHOW_TRADING_VIEWER(new ShowTradingViewerCommand());

    /**
     * Command.
     */
    private final Command command;

    /**
     * Package access constructor.
     * @param command {@link Command}
     */
    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Gets command.
     * @return command
     */
    public Command getCommand() {
        return this.command;
    }
}
