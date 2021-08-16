package by.tarasiuk.ct.model.entity.impl;

import by.tarasiuk.ct.model.entity.Entity;

/**
 * Class of trading type.
 */
public class Trading implements Entity {
    private static final long serialVersionUID = 44109233988771624L;

    long id;
    long offerId;
    long employeeId;
    float freight;
    Status status;

    /**
     * Trading statuses
     */
    public enum Status {
        ACCEPTED,
        NOT_ACCEPTED,
    }

    /**
     * The method for obtaining the trading ID.
     *
     * @return          Trading ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Method for setting the trading ID.
     *
     * @param id        Trading ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * The method for obtaining the offer ID.
     *
     * @return          Offer ID.
     */
    public long getOfferId() {
        return offerId;
    }

    /**
     * Method for setting the offer ID.
     *
     * @param id        Offer ID.
     */
    public void setOfferId(long id) {
        this.offerId = id;
    }

    /**
     * The method for obtaining the employee ID.
     *
     * @return          Employee ID.
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * Method for setting the employee ID.
     *
     * @param id        Employee ID.
     */
    public void setEmployeeId(long id) {
        this.employeeId = id;
    }

    /**
     * The method for obtaining the offer freight (USD).
     *
     * @return          Freight.
     */
    public float getFreight() {
        return freight;
    }

    /**
     * Method for setting the offer freight (USD).
     *
     * @param freight   Freight.
     */
    public void setFreight(float freight) {
        this.freight = freight;
    }

    /**
     * The method for obtaining the token status.
     *
     * @return          Status {@link Trading.Status}.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Method for setting the trading status.
     *
     * @param status    Status {@link Trading.Status}.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Trading)) {
            return false;
        }

        Trading trading = (Trading) obj;

        return id == trading.id && offerId == trading.offerId && employeeId == trading.employeeId
                && freight == trading.freight && status.name().equals(trading.status.name());
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + Long.hashCode(id);
        result = 31 * result + Long.hashCode(offerId);
        result = 31 * result + Long.hashCode(employeeId);
        result = 31 * result + Float.hashCode(freight);
        result = 31 * result + ((status != null) ? status.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Trading{");
        sb.append("id=").append(id);
        sb.append(", offerId=").append(offerId);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", freight=").append(freight);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
