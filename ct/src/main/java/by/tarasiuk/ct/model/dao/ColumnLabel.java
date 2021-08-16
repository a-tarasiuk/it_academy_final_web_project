package by.tarasiuk.ct.model.dao;

/**
 * Database table column names.
 */
public final class ColumnLabel {
    /**
     * Columns of the Accounts database table.
     */
    public static final String ACCOUNT_ID = "account_id";

    /**
     * Columns of the Tokens database table.
     */
    public static final String TOKEN_ID = "token_id";
    public static final String TOKEN_NUMBER = "token_number";
    public static final String TOKEN_STATUS = "token_status";

    /**
     * Columns of the Offers database table.
     */
    public static final String OFFER_ID = "offer_id";
    public static final String OFFER_PRODUCT_NAME = "offer_product_name";
    public static final String OFFER_PRODUCT_WEIGHT = "offer_product_weight";
    public static final String OFFER_PRODUCT_VOLUME = "offer_product_volume";
    public static final String OFFER_ADDRESS_FROM = "offer_address_from";
    public static final String OFFER_ADDRESS_TO = "offer_address_to";
    public static final String OFFER_FREIGHT = "offer_freight";
    public static final String OFFER_CREATION_DATE = "offer_creation_date";
    public static final String OFFER_STATUS = "offer_status";

    /**
     * Columns of the Tradings database table.
     */
    public static final String TRADING_ID = "trading_id";
    public static final String TRADING_FREIGHT = "trading_freight";
    public static final String TRADING_STATUS = "trading_status";

    /**
     * Columns of the Employees database table.
     */
    public static final String EMPLOYEE_ID = "employee_id";

    /**
     * Columns of the Employees database table.
     */
    public static final String COMPANY_ID = "company_id";

    private ColumnLabel() {
    }
}
