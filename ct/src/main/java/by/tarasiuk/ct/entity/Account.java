package by.tarasiuk.ct.entity;

import java.time.LocalDate;

public class Account implements Entity {
    private static final long serialVersionUID = 44109233988771624L;
    private String login;
    private String email;
    private LocalDate registrationDate;
    private String phoneNumber;
    private String address;
    private RoleName roleName;
    private StatusName statusName;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RoleName getAccountRole() {
        return roleName;
    }

    public void setAccountRole(RoleName accountRole) {
        this.roleName = accountRole;
    }

    public StatusName getAccountStatus() {
        return statusName;
    }

    public void setAccountStatus(StatusName accountStatus) {
        this.statusName = accountStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Account)) {
            return false;
        }

        Account account = (Account) obj;

        return login != null && login.equals(account.login);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();

        result = 31 * result + (email != null ? email.hashCode() : 0 );
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (statusName != null ? statusName.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder("Account{")
                .append("login: ").append(this.login)
                .append("; email: ").append(this.email)
                .append("; registration date: ").append(this.registrationDate)
                .append("; phone number: ").append(this.phoneNumber)
                .append("; address: ").append(this.address)
                .append("; account role: ").append(this.roleName)
                .append("; account status: ").append(this.statusName)
                .append("}\n").toString();
    }

    public enum Role {
        GUEST,
        ADMINISTRATOR,
        COMPANY_MANAGER,
        FORWARDER
    }

    public enum Status {
        ACTIVATED,
        NOT_ACTIVATED,
        BANNED
    }

    // todo передалать вместо enum на public static class, т.к. использование ENUM'ов в этой ситуации - это использование не по назначению
    public static class ColumnName {
        public static final String LOGIN = "ACCOUNT_LOGIN";
        public static final String PASSWORD = "ACCOUNT_PASSWORD";
        public static final String EMAIL = "ACCOUNT_EMAIL";
        public static final String REGISTRATION_DATE = "ACCOUNT_REGISTRATION_DATE";
        public static final String PHONE_NUMBER = "ACCOUNT_PHONE_NUMBER";
        public static final String ADDRESS = "ACCOUNT_ADDRESS";
        public static final String ROLE_ID = "ACCOUNT_ROLE_ID";
        public static final String STATUS_ID = "ACCOUNT_STATUS_ID";
    }
}
