package by.tarasiuk.ct.model.entity.impl;

import by.tarasiuk.ct.model.entity.Entity;

public class Token implements Entity {
    private static final long serialVersionUID = -3694202731606343552L;

    private long id;
    private long accountId;
    private String number;
    private Status status;

    public enum Status {
        CONFIRMED,
        UNCONFIRMED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

        if (!(obj instanceof Token)) {
            return false;
        }

        Token token = (Token) obj;

        return id == token.id && accountId == token.accountId && number.equals(token.number)
                && status.name().equals(token.status.name());
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + Long.hashCode(id);
        result = 31 * result + Long.hashCode(accountId);
        result = 31 * result + ((number != null) ? number.hashCode() : 0);
        result = 31 * result + ((status != null) ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Token{");
        sb.append("id=").append(id);
        sb.append(", accountId=").append(accountId);
        sb.append(", number='").append(number).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
