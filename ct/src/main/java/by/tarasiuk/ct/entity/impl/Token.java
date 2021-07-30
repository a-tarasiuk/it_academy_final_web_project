package by.tarasiuk.ct.entity.impl;

public class Token {
    private long id;
    private long accountId;
    private String number;
    private Status status;

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
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", number='" + number + '\'' +
                ", status=" + status +
                '}';
    }

    public enum Status {
        CONFIRMED,
        UNCONFIRMED;
    }
}
