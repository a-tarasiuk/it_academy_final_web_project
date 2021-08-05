package by.tarasiuk.ct.entity.impl;


import by.tarasiuk.ct.entity.Entity;

import java.time.LocalDate;

public class Offer implements Entity {
    private static final long serialVersionUID = 44109233988771624L;

    private long id;
    private long accountId;
    private String productName;
    private float productWeight;
    private float productVolume;
    private String addressFrom;
    private String addressTo;
    private float freight;
    private LocalDate creationDate;
    private Status status;

    public enum Status {
        OPEN,
        CLOSED
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductFreight() {
        return productWeight;
    }

    public void setProductWeight(float productWeight) {
        this.productWeight = productWeight;
    }

    public float getProductVolume() {
        return productVolume;
    }

    public void setProductVolume(float productVolume) {
        this.productVolume = productVolume;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public float getFreight() {
        return freight;
    }

    public void setFreight(float freight) {
        this.freight = freight;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Offer{");
        sb.append("id=").append(id);
        sb.append(", accountId=").append(accountId);
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", productWeight=").append(productWeight);
        sb.append(", productVolume=").append(productVolume);
        sb.append(", addressFrom='").append(addressFrom).append('\'');
        sb.append(", addressTo='").append(addressTo).append('\'');
        sb.append(", freight=").append(freight);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
