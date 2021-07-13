package by.tarasiuk.ct.manager;

public enum DatabaseTableName {
    ACCOUNT_LOGIN("login"),
    ACCOUNT_PASSWORD("password"),
    ACCOUNT_EMAIL("email"),
    ACCOUNT_REGISTRATION_DATE("registration_date"),
    ACCOUNT_PHONE_NUMBER("phone_number"),
    ACCOUNT_ADDRESS("address"),
    ACCOUNT_ROLE_ID("account_role_id"),
    ACCOUNT_STATUS_ID("account_status_is"),

    ACCOUNT_ROLE_NAME("name"),
    ACCOUNT_STATUS_NAME("name");

    private final String columnName;

    DatabaseTableName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return this.columnName;
    }
}
