package by.tarasiuk.ct.model.entity.impl;

import by.tarasiuk.ct.model.entity.Entity;

/**
 * Class of company type.
 */
public class Company implements Entity {
    private static final long serialVersionUID = -8258103486004279207L;

    private long id;
    private String name;
    private String address;
    private String phoneNumber;

    /**
     * The method for obtaining the company ID.
     *
     * @return              company ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Method for setting the company ID.
     *
     * @param id            Company ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * The method for obtaining the company name.
     *
     * @return              Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Method for setting the company name.
     *
     * @param name          Company name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The method for obtaining the company address.
     *
     * @return              Address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Method for setting the company address.
     *
     * @param address       Company address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * The method for obtaining the company phone number.
     *
     * @return              Phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Method for setting the company phone number.
     *
     * @param phoneNumber   Phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Company)) {
            return false;
        }

        Company company = (Company) obj;

        return id == company.id && name.equals(company.name) && address.equals(company.address)
                && phoneNumber.equals(company.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + Long.hashCode(id);
        result = 31 * result + ((name != null) ? name.hashCode() : 0);
        result = 31 * result + ((name != null) ? address.hashCode() : 0);
        result = 31 * result + ((name != null) ? phoneNumber.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Company{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
