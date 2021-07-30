package by.tarasiuk.ct.entity.impl;

import by.tarasiuk.ct.entity.Entity;

public class Company implements Entity {
    private static final long serialVersionUID = -8258103486004279207L;

    private long id;
    private String name;
    private String address;
    private String phoneNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return new StringBuilder("Company{")
                .append("id='").append(id).append('\'')
                .append("name='").append(name).append('\'')
                .append(", address='").append(address).append('\'')
                .append(", phoneNumber='").append(phoneNumber).append('\'')
                .append('}').toString();
    }
}
