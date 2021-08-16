package by.tarasiuk.ct.model.entity.impl;

import by.tarasiuk.ct.model.entity.Entity;
import java.time.LocalDate;

/**
 * Class of offer type.
 */
public class Offer implements Entity {
    private static final long serialVersionUID = 44109233988771624L;

    private long id;
    private long employeeId;
    private String productName;
    private float productWeight;
    private float productVolume;
    private String addressFrom;
    private String addressTo;
    private float freight;
    private LocalDate creationDate;
    private Status status;

    /**
     * Offer statuses.
     */
    public enum Status {
        OPEN,
        CLOSED
    }

    /**
     * The method for obtaining the offer ID.
     *
     * @return          Offer ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Method for setting the offer ID.
     *
     * @param id        Offer ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * The method for obtaining the Employee ID.
     *
     * @return          Employee ID.
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * Method for setting the Employee ID.
     *
     * @param employeeId Employee ID.
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * The method for obtaining the product name.
     *
     * @return          Product name.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Method for setting the product name.
     *
     * @param productName Product name.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * The method for obtaining the product weight (ton).
     *
     * @return          Product weight.
     */
    public float getProductWeight() {
        return productWeight;
    }

    /**
     * Method for setting the product weight (ton).
     *
     * @param productWeight Product weight.
     */
    public void setProductWeight(float productWeight) {
        this.productWeight = productWeight;
    }

    /**
     * The method for obtaining the product volume (cubic meter).
     *
     * @return          Product volume.
     */
    public float getProductVolume() {
        return productVolume;
    }

    /**
     * Method for setting the product volume (cubic meter).
     *
     * @param productVolume Product volume.
     */
    public void setProductVolume(float productVolume) {
        this.productVolume = productVolume;
    }

    /**
     * The method for obtaining the address where need to pick up the goods.
     *
     * @return          Address from.
     */
    public String getAddressFrom() {
        return addressFrom;
    }

    /**
     * Method for setting the address where need to pick up the goods.
     *
     * @param addressFrom Address from.
     */
    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    /**
     * The method for obtaining the address where to deliver the goods.
     *
     * @return          Address to.
     */
    public String getAddressTo() {
        return addressTo;
    }

    /**
     * Method for setting the address where need to deliver the goods.
     *
     * @param addressTo Address to.
     */
    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    /**
     * The method for obtaining the product freight (USD).
     *
     * @return          Product freight.
     */
    public float getFreight() {
        return freight;
    }

    /**
     * Method for setting the product freight (USD).
     *
     * @param freight   Product freight.
     */
    public void setFreight(float freight) {
        this.freight = freight;
    }

    /**
     * The method for obtaining the offer creation date.
     *
     * @return          Creation date {@link java.time.LocalDate}
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Method for setting the product creation date.
     *
     * @param creationDate Creation date.
     */
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * The method for obtaining the offer status.
     *
     * @return          Status {@link Offer.Status}
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Method for setting the product status.
     *
     * @param status     Status {@link Offer.Status}
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Offer)) {
            return false;
        }

        Offer offer = (Offer) obj;

        return id == offer.id && employeeId == offer.employeeId && productName.equals(offer.productName)
                && productWeight == offer.productWeight && productVolume == offer.productVolume
                && addressFrom.equals(offer.addressFrom) && addressTo.equals(offer.addressTo)
                && freight == offer.freight && creationDate.equals(offer.creationDate)
                && status.name().equals(offer.status.name());
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + Long.hashCode(id);
        result = 31 * result + Long.hashCode(employeeId);
        result = 31 * result + ((productName != null) ? productName.hashCode() : 0);
        result = 31 * result + Float.hashCode(productWeight);
        result = 31 * result + Float.hashCode(productVolume);
        result = 31 * result + ((addressFrom != null) ? addressFrom.hashCode() : 0);
        result = 31 * result + ((addressTo != null) ? addressTo.hashCode() : 0);
        result = 31 * result + Float.hashCode(freight);
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + ((status != null) ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Offer{");
        sb.append("id=").append(id);
        sb.append(", employeeId=").append(employeeId);
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
