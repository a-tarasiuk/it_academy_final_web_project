package by.tarasiuk.ct.entity;

import java.time.LocalDate;

public class Account implements Entity {
    private static final long serialVersionUID = 44109233988771624L;
    private String login;
    private String email;
    private LocalDate registrationDate;
    private String phoneNumber;
    private String address;
    private AccountRole accountRole;
    private AccountStatus accountStatus;

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

    public AccountRole getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
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
        result = 31 * result + (accountRole != null ? accountRole.hashCode() : 0);
        result = 31 * result + (accountStatus != null ? accountStatus.hashCode() : 0);

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
                .append("; account role: ").append(this.accountRole)
                .append("; account status: ").append(this.accountStatus)
                .append("}\n").toString();
    }
}
