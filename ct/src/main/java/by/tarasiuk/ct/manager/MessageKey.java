package by.tarasiuk.ct.manager;

public final class MessageKey {
    /**
     * For all queries
     */
    public static final String MESSAGE_QUERY_ERROR = "message.query.error";

    /**
     * Confirm sign up message
     */
    public static final String CONFIRM_MESSAGE = "form.signUp.confirmEmail";

    /**
     * Activate account
     */
    public static final String ACCOUNT_EMAIL_NOT_EXIST = "message.notExist.email";
    public static final String ACCOUNT_ALREADY_ACTIVATED = "message.account.alreadyActivated";
    public static final String ACCOUNT_SUCCESSFULLY_ACTIVATED = "message.activated.successfullyActivated";

    /**
     * Token
     */
    public static final String TOKEN_NOT_EXIST = "message.notExist.token";
    public static final String TOKEN_INCORRECT = "message.incorrect.token";

    /**
     * Create offer
     */
    public static final String OFFER_SUCCESSFULLY_CREATED = "message.offer.create.successfully";

    /**
     * Create trading
     */
    public static final String TRADING_SUCCESSFULLY_CREATED = "message.trading.create.successfully";

    /**
     * Activation email
     */
    public static final String EMAIL_SUBJECT_COMPLETION_REGISTRATION = "email.signUp.completionRegistration.subject";
    public static final String EMAIL_TEXT_COMPLETION_REGISTRATION = "email.signUp.completionRegistration.text";

    /**
     * Offer table
     */
    public static final String OFFER_COMPANY_NAME = "offer.table.companyName";
    public static final String OFFER_PRODUCT_NAME = "offer.table.productName";
    public static final String OFFER_PRODUCT_WEIGHT = "offer.table.productWeight";
    public static final String OFFER_PRODUCT_VOLUME = "offer.table.productVolume";
    public static final String OFFER_ADDRESS = "offer.table.address";
    public static final String OFFER_ADDRESS_FROM = "offer.addressFrom";
    public static final String OFFER_ADDRESS_TO = "offer.addressTo";
    public static final String OFFER_CREATION_DATE = "offer.table.creationDate";
    public static final String OFFER_FREIGHT = "offer.table.freight";
    public static final String OFFER_MY_FREIGHT = "offer.table.myFreight";
    public static final String OFFERS_DO_NOT_EXIST = "offer.table.doNotExist";
    public static final String OFFER_STATUS = "offer.status";
    public static final String OFFER_TON = "offer.ton";
    public static final String OFFER_EMPTY = "offer.table.empty";
    public static final String OFFER_ALL = "offer.all";
    public static final String OFFER_MY = "offer.my";

    /**
     * Account table
     */
    public static final String EMPLOYEE_FIRST_NAME = "employee.table.firstName";
    public static final String EMPLOYEE_LAST_NAME = "employee.table.lastName";
    public static final String EMPLOYEE_LOGIN = "employee.table.login";
    public static final String EMPLOYEE_EMAIL = "employee.table.email";
    public static final String EMPLOYEE_REGISTRATION_DATE = "employee.table.registrationDate";
    public static final String EMPLOYEE_ROLE = "employee.table.role";
    public static final String EMPLOYEE_STATUS = "employee.table.status";
    public static final String EMPLOYEES_MY = "employees.my";

    /**
     * Freight offer
     */
    public static final String FREIGHT_OFFER = "freight.offer";

    /**
     * Trading
     */
    public static final String MY_TRADINGS = "tradings.my";
    public static final String TRADING_HISTORY = "trading.history";
    public static final String TRADINGS_EMPTY = "tradings.list.empty";
    public static final String TRADINGS_LIMITATION = "tradings.limitation";
    public static final String TRADINGS_DO_NOT_EXIST = "tradings.table.doNotExist";

    /**
     * Placeholders
     */
    public static final String PLACEHOLDER_OFFER_FREIGHT = "offer.freight";

    /**
     * Descriptions
     */
    public static final String DESCRIPTION_TRADING_FREIGHT = "description.tradingFreight";

    /**
     * Descriptions
     */
    public static final String MESSAGE_WARN = "message.code.tamperingDetected";

    /**
     * Headers
     */
    public static final String HEADER_ACCOUNT_INFO = "header.accountInfo";
    public static final String HEADER_MENU = "header.menu";

    private MessageKey() {
    }
}
