package by.tarasiuk.ct.model.entity.impl;

import by.tarasiuk.ct.model.entity.Entity;

public class Employee implements Entity {
    private static final long serialVersionUID = 44109233988771624L;

    private long id;
    private long accountId;
    private long companyId;

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

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("id=").append(id);
        sb.append(", accountId=").append(accountId);
        sb.append(", companyId=").append(companyId);
        sb.append('}');
        return sb.toString();
    }
}
