package by.tarasiuk.ct.controller.command;

public final class AttributeName {
    /**
     * Command attribute from JSP page.
     */
    public static final String COMMAND = "command";

    /**
     * Locale attributes from JSP page.
     */
    public static final String LOCALE = "locale";
    public static final String EN_US = "en_US";
    public static final String RU_RU = "ru_RU";

    /**
     * Administrator attributes
     */
    public static final String SHOW_ADMIN_PANEL = "show_administrator_panel";

    /**
     * Account attributes from JSP page.
     */
    public static final String ACCOUNT = "account";
    public static final String ACCOUNT_ID = "account_id";
    public static final String ACCOUNT_FIRST_NAME = "account_first_name";
    public static final String ACCOUNT_LAST_NAME = "account_last_name";
    public static final String ACCOUNT_LOGIN = "account_login";
    public static final String ACCOUNT_EMAIL = "account_email";
    public static final String ACCOUNT_REGISTRATION_DATE = "account_registration_date";
    public static final String ACCOUNT_ROLE = "account_role";
    public static final String ACCOUNT_STATUS = "account_status";
    public static final String ACCOUNT_PASSWORD = "account_password";
    public static final String ACCOUNT_PASSWORD_ENCODED = "account_password_encoded";
    public static final String ACCOUNT_CONFIRM_PASSWORD = "account_confirm_password";

    /**
     * Company attributes from JSP page.
     */
    public static final String COMPANY = "company";
    public static final String COMPANY_ID = "company_id";
    public static final String COMPANY_NAME = "company_name";
    public static final String COMPANY_ADDRESS = "company_address";
    public static final String COMPANY_PHONE_NUMBER = "company_phone_number";

    /**
     * Token attributes.
     */
    public static final String TOKEN_NUMBER = "token_number";

    /**
     * Offer attributes.
     */
    public static final String OFFER = "offer";
    public static final String OFFER_LIST = "offer_list";
    public static final String OFFER_ID = "offer_id";
    public static final String OFFER_PRODUCT_NAME = "offer_product_name";
    public static final String OFFER_PRODUCT_WEIGHT = "offer_product_weight";
    public static final String OFFER_PRODUCT_VOLUME = "offer_product_volume";
    public static final String OFFER_ADDRESS_FROM = "offer_address_from";
    public static final String OFFER_ADDRESS_TO = "offer_address_to";
    public static final String OFFER_FREIGHT = "offer_freight";
    public static final String OFFER_STATUS = "offer_status";

    /**
     * Employees attributes.
     */
    public static final String EMPLOYEE_ID = "employee_id";

    /**
     * Offer attributes.
     */
    public static final String TRADING = "trading";
    public static final String TRADING_FREIGHT = "trading_freight";
    public static final String TRADING_MAP = "trading_map";
    public static final String TRADING_ID = "trading_id";
    public static final String TRADING_STATUS = "trading_status";

    /**
     * Message attributes from JSP page.
     */
    public static final String SUCCESSFUL_OPERATION = "successful_operation";
    public static final String INVALID_DATA = "invalid_data";
    public static final String INFORMATION_MESSAGE = "information_message";
    public static final String MESSAGE_QUERY_ERROR = "message_query_error";
    public static final String MESSAGE_ERROR_LOGIN_ALREADY_EXIST = "error_login_already_exist";
    public static final String MESSAGE_ERROR_EMAIL_ALREADY_EXIST = "error_email_already_exist";
    public static final String MESSAGE_ERROR_COMPANY_ALREADY_EXIST = "error_company_already_exist";
    public static final String MESSAGE_INCORRECT_SIGN_IN_DATA = "incorrect_sign_in_data";
    public static final String MESSAGE_INCORRECT_SIGN_UP_DATA = "incorrect_sign_up_data";
    public static final String MESSAGE_INCORRECT_OFFER_DATA = "incorrect_offer_data";
    public static final String MESSAGE_INCORRECT_TRADING_FREIGHT = "incorrect_trading_freight";
    public static final String MESSAGE_BANNED_ACCOUNT = "account_banned";
    public static final String MESSAGE_NOT_ACTIVATED_ACCOUNT = "account_not_activated";

    private AttributeName() {
    }
}
