package by.tarasiuk.ct.model.entity.impl;

import by.tarasiuk.ct.model.entity.Entity;

public class Trading implements Entity {
    private static final long serialVersionUID = 44109233988771624L;

    long id;
    long offerId;
    long employeeId;
    float freight;
    Status status;

    public enum Status {
        ACCEPTED,
        NOT_ACCEPTED,
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOfferId() {
        return offerId;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public float getFreight() {
        return freight;
    }

    public void setFreight(float freight) {
        this.freight = freight;
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
