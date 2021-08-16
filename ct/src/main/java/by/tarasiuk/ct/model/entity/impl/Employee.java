package by.tarasiuk.ct.model.entity.impl;

import by.tarasiuk.ct.model.entity.Entity;

/**
 * Class of employee type.
 */
public class Employee implements Entity {
    private static final long serialVersionUID = 44109233988771624L;

    private long id;
    private long accountId;
    private long companyId;

    /**
     * The method for obtaining the employee ID.
     *
     * @return          Employee ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Method for setting the employee ID.
     *
     * @param id        Employee ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * The method for obtaining the account ID.
     *
     * @return          Account ID.
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Method for setting the account ID.
     *
     * @param id        Account ID.
     */
    public void setAccountId(long id) {
        this.accountId = id;
    }

    /**
     * The method for obtaining the company ID.
     *
     * @return          Company ID.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * Method for setting the company ID.
     *
     * @param id        Company ID.
     */
    public void setCompanyId(long id) {
        this.companyId = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Employee)) {
            return false;
        }

        Employee employee = (Employee) obj;

        return id == employee.id && accountId == employee.getAccountId() && companyId == employee.getCompanyId();
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + Long.hashCode(id);
        result = 31 * result + Long.hashCode(accountId);
        result = 31 * result + Long.hashCode(companyId);

        return result;
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
