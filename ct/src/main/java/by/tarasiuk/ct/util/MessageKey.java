package by.tarasiuk.ct.util;

/**
 * The class represents named constants, the contents of the key names in the proper locale file.
 */
public final class MessageKey {
    /**
     * For all queries.
     */
    public static final String MESSAGE_QUERY_ERROR = "message.query.error";

    /**
     * Confirm sign up message.
     */
    public static final String CONFIRM_MESSAGE = "form.signUp.confirmEmail";

    /**
     * Activate account messages.
     */
    public static final String ACCOUNT_EMAIL_NOT_EXIST = "message.notExist.email";
    public static final String ACCOUNT_ALREADY_ACTIVATED = "message.account.alreadyActivated";
    public static final String ACCOUNT_SUCCESSFULLY_ACTIVATED = "message.activated.successfullyActivated";

    /**
     * Token messages.
     */
    public static final String TOKEN_NOT_EXIST = "message.notExist.token";
    public static final String TOKEN_INCORRECT = "message.incorrect.token";

    /**
     * Create offer messages.
     */
    public static final String OFFER_SUCCESSFULLY_CREATED = "message.offer.create.successfully";

    /**
     * Create trading messages.
     */
    public static final String TRADING_SUCCESSFULLY_CREATED = "message.trading.create.successfully";

    /**
     * Activation email messages.
     */
    public static final String EMAIL_SUBJECT_COMPLETION_REGISTRATION = "email.signUp.completionRegistration.subject";
    public static final String EMAIL_TEXT_COMPLETION_REGISTRATION = "email.signUp.completionRegistration.text";

    /**
     * Offer table messages.
     */
    public static final String OFFER_COMPANY_NAME = "offer.table.companyName";
    public static final String OFFER_PRODUCT_NAME = "offer.table.productName";
    public static final String OFFER_PRODUCT_WEIGHT = "offer.table.productWeight";
    public static final String OFFER_PRODUCT_VOLUME = "offer.table.productVolume";
    public static final String OFFER_ADDRESS = "offer.table.address";
    public static final String OFFER_ADDRESS_FROM = "offer.addressFrom";
    public static final String OFFER_ADDRESS_TO = "offer.addressTo";
    public static final String OFFER_CREATION_DATE = "offer.table.creationDate";
    public static final String OFFER_FREIGHT = "offer.freight";
    public static final String OFFER_MY_FREIGHT = "offer.table.myFreight";
    public static final String OFFERS_DO_NOT_EXIST = "offer.table.doNotExist";
    public static final String OFFER_STATUS = "offer.status";
    public static final String OFFER_EMPTY = "offer.table.empty";

    /**
     * Trading messages.
     */
    public static final String TRADING_HISTORY = "trading.history";
    public static final String TRADINGS_EMPTY = "tradings.list.empty";
    public static final String TRADINGS_DO_NOT_EXIST = "tradings.table.doNotExist";
    public static final String TRADINGS_STATUS = "tradings.table.status";

    /**
     * Descriptions messages.
     */
    public static final String MESSAGE_WARN = "message.code.tamperingDetected";

    private MessageKey() {
    }
}
