package by.tarasiuk.ct.entity;

public class SignIn implements Entity {
    private String login;
    private String password;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SignIn)) {
            return false;
        }

        SignIn signIn = (SignIn) o;

        return login.equals(signIn.login) && password.equals(signIn.password);
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);

        return result;
    }
}
