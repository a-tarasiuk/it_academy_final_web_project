package by.tarasiuk.ct.model.entity.impl;

import by.tarasiuk.ct.model.entity.Entity;
import java.time.LocalDate;

/**
 * Class of account type.
 */
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

    /**
     * Account roles.
     */
    public enum Role {
        GUEST,
        MANAGER,
        FORWARDER,
        ADMINISTRATOR;
    }

    /**
     * Account statuses.
     */
    public enum Status {
        ACTIVATED,
        NOT_ACTIVATED,
        BANNED;
    }

    /**
     * The method for obtaining the account ID.
     *
     * @return          Account ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Method for setting the account ID.
     *
     * @param id        Account ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * The method for obtaining the first name.
     *
     * @return          First name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method for setting the first name.
     *
     * @param firstName First name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * The method for obtaining the last name.
     *
     * @return          Last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method for setting the first name.
     *
     * @param lastName Last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * The method for obtaining the login.
     *
     * @return          Login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Method for setting the login.
     *
     * @param login     Login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * The method for obtaining the email.
     *
     * @return          Email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method for setting the email.
     *
     * @param email     Email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * The method for obtaining the account registration date.
     *
     * @return          Registration date.
     */
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Method for setting the account registration date.
     *
     * @param registrationDate Registration date.
     */
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * The method for obtaining the account role.
     *
     * @return      Account role {@link Account.Role}
     */
    public Role getRole() {
        return role;
    }

    /**
     * Method for setting the account role.
     *
     * @param role Account role {@link Account.Role}
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * The method for obtaining the account status.
     *
     * @return      Account status {@link Account.Status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Method for setting the account status.
     *
     * @param status Account status {@link Account.Status}
     */
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
