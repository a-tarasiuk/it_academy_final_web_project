package by.tarasiuk.ct.controller.command;

/**
 * Manager, that helps with defining page paths.
 */
public final class PagePath {
    /**
     * Static pages.
     */
    public static final String ABOUT = "/WEB-INF/jsp/common/about.jsp";
    public static final String CONTACT = "/WEB-INF/jsp/common/contact.jsp";

    /**
     * Main pages.
     */
    public static final String INDEX = "index.jsp";
    public static final String MAIN = "/WEB-INF/jsp/main.jsp";

    /**
     * Sign in and sign up pages.
     */
    public static final String SIGN_IN = "/WEB-INF/jsp/sign/sign-in.jsp";
    public static final String SIGN_UP = "/WEB-INF/jsp/sign/sign-up.jsp";

    /**
     * Information message pages.
     */
    public static final String INFO = "/WEB-INF/jsp/sign/info.jsp";
    public static final String INFORMATION_MESSAGE = "/WEB-INF/jsp/info/information_message.jsp";

    /**
     * Error pages.
     */
    public static final String ERROR = "/WEB-INF/jsp/error/error.jsp";
    public static final String ERROR_ACCESS_DENIED = "/WEB-INF/jsp/error/access_denied.jsp";

    /**
     * Offer pages.
     */
    public static final String CREATE_OFFER = "/WEB-INF/jsp/offer/offer_creator.jsp";
    public static final String OFFER_EDITOR = "/WEB-INF/jsp/offer/offer_editor.jsp";
    public static final String OPEN_OFFERS = "/WEB-INF/jsp/offer/open_offers.jsp";

    /**
     * Account pages.
     */
    public static final String ACCOUNT_OFFERS = "/WEB-INF/jsp/account/account_offers.jsp";
    public static final String ACCOUNT_OFFER = "/WEB-INF/jsp/account/account_offer.jsp";
    public static final String ACCOUNT_TRADING = "/WEB-INF/jsp/account/account_trading.jsp";
    public static final String ACCOUNT_TRADINGS = "/WEB-INF/jsp/account/account_tradings.jsp";
    public static final String ACCOUNT_FORWARDERS = "/WEB-INF/jsp/account/account_forwarders.jsp";
    public static final String ACCOUNT_PASSWORD = "/WEB-INF/jsp/account/account_password.jsp";
    public static final String ACCOUNT_SETTINGS = "/WEB-INF/jsp/account/account_settings.jsp";

    /**
     * Information pages.
     */
    public static final String TRADING_CREATE_INFO = "/WEB-INF/jsp/info/trading_create_info.jsp";
    public static final String OFFER_EDIT_INFO = "/WEB-INF/jsp/info/offer_edit_info.jsp";
    public static final String OFFER_DEACTIVATE_INFO = "/WEB-INF/jsp/info/offer_deactivate_info.jsp";
    public static final String FREIGHT_APPLY_INFO = "/WEB-INF/jsp/info/freight_apply_info.jsp";
    public static final String FORWARDER_CREATED_INFO = "/WEB-INF/jsp/info/forwarder_created_info.jsp";
    public static final String OFFER_CREATED_INFO = "/WEB-INF/jsp/info/offer_created_info.jsp";

    /**
     * Company pages.
     */
    public static final String COMPANY_SETTINGS = "/WEB-INF/jsp/company/company_settings.jsp";

    /**
     * Employee pages.
     */
    public static final String FORWARDER_SETTINGS = "/WEB-INF/jsp/manager/forwarder_settings.jsp";
    public static final String FORWARDER_CREATOR = "/WEB-INF/jsp/manager/forwarder_creator.jsp";

    /**
     * Trading pages.
     */
    public static final String TRADING_VIEWER = "/WEB-INF/jsp/trading/trading_viewer.jsp";
    public static final String ACCOUNT_TRADING_VIEWER = "/WEB-INF/jsp/trading/account_trading_viewer.jsp";

    /**
     * Admin pages.
     */
    public static final String ADMIN_ACCOUNT_LIST = "/WEB-INF/jsp/admin/account_list_admin.jsp";
    public static final String ADMIN_ACCOUNT_EDITOR = "/WEB-INF/jsp/admin/account_editor_admin.jsp";

    private PagePath() {
    }
}
