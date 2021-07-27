package by.tarasiuk.ct.manager;

public class RequestAttribute {
    private RequestAttribute() {
    }

    /**
     * Command attribute from JSP page.
     */
    public static final String COMMAND = "command";

    /**
     * Locale attributes from JSP page.
     */
    public static final String LOCALE_PAGE = "locale_page";
    public static final String LOCALE_EN = "en";
    public static final String LOCALE_RU = "ru";

    /**
     * Administrator attributes
     */
    public static final String ACTIVATE_ADMIN_PANEL = "activate_administrator_panel";

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
    public static final String COMPANY_ID = "company_id";
    public static final String COMPANY_NAME = "company_name";
    public static final String COMPANY_ADDRESS = "company_address";
    public static final String COMPANY_PHONE_NUMBER = "company_phone_number";

    /**
     * Message attributes from JSP page.
     */
    public static final String MESSAGE_QUERY_ERROR = "message_query_error";
    public static final String MESSAGE_ERROR_LOGIN_ALREADY_EXIST = "error_login_already_exist";
    public static final String MESSAGE_ERROR_EMAIL_ALREADY_EXIST = "error_email_already_exist";
    public static final String MESSAGE_ERROR_COMPANY_ALREADY_EXIST = "error_company_already_exist";
    public static final String MESSAGE_INCORRECT_SIGN_IN_DATA = "incorrect_sign_in_data";
    public static final String MESSAGE_INCORRECT_SIGN_UP_DATA = "incorrect_sign_up_data";

    /**
     * Messages
     */
    public static final String MESSAGE = "message";
}
