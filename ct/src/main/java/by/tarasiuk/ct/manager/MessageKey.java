package by.tarasiuk.ct.manager;

public final class MessageKey {
    /**
     * For all queries
     */
    public static final String QUERY_ERROR = "message.query.error";

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
    public static final String OFFER_CREATION_DATE = "offer.table.creationDate";
    public static final String OFFER_FREIGHT = "offer.table.freight";
    public static final String OFFERS_DO_NOT_EXIST = "offer.table.doNotExist";

    private MessageKey() {
    }
}
