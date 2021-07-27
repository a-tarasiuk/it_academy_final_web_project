package by.tarasiuk.ct.entity;


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

        return login != null && login.equals(account.login);
    }

    public enum Role {
        ADMINISTRATOR,
        MODERATOR,
        MANAGER,
        FORWARDER;

        @Override
        public String toString() {
            return name().toUpperCase();
        }
    }

    public enum Status {
        ACTIVATED,
        NOT_ACTIVATED,
        BANNED;

        @Override
        public String toString() {
            return name().toUpperCase();
        }
    }

    @Override
    public String toString() {
        return new StringBuilder("Account{")
                .append("id='").append(this.id).append('\'')
                .append(",first name='").append(this.firstName).append('\'')
                .append(",last name='").append(this.lastName).append('\'')
                .append(",login='").append(this.login).append('\'')
                .append(",email='").append(this.email).append('\'')
                .append(",registration date='").append(this.registrationDate).append('\'')
                .append(",account role='").append(this.role).append('\'')
                .append(",account status='").append(this.status).append('\'')
                .append("}").toString();
    }
}
