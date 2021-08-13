package by.tarasiuk.ct.model.entity.impl;

import by.tarasiuk.ct.model.entity.Entity;

import java.time.LocalDate;

public class Account implements Entity {
    private static final long serialVersionUID = 44109233988771624L;

    private long id;
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private LocalDate registrationDate;
    private Role role;
    private Status status;

    public enum Role {
        ADMINISTRATOR,
        MANAGER,
        FORWARDER;
    }

    public enum Status {
        ACTIVATED,
        NOT_ACTIVATED,
        BANNED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

        return id == account.id && firstName.equals(account.firstName) && lastName.equals(account.lastName)
                && login.equals(account.login) && email.equals(account.email)
                && registrationDate.toString().equals(account.registrationDate.toString())
                && role.name().equals(account.role.name()) && status.name().equals(account.status.name());
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + Long.hashCode(id);
        result = 31 * result + ((firstName != null) ? firstName.hashCode() : 0);
        result = 31 * result + ((lastName != null) ? lastName.hashCode() : 0);
        result = 31 * result + ((login != null) ? login.hashCode() : 0);
        result = 31 * result + ((email != null) ? email.hashCode() : 0);
        result = 31 * result + ((registrationDate != null) ? registrationDate.hashCode() : 0);
        result = 31 * result + ((role != null) ? role.hashCode() : 0);
        result = 31 * result + ((status != null) ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", registrationDate=").append(registrationDate);
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
