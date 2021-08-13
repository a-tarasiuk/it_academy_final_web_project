package by.tarasiuk.ct.model.dao.builder;

import by.tarasiuk.ct.model.entity.impl.Offer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static by.tarasiuk.ct.model.dao.ColumnLabel.EMPLOYEE_ID;
import static by.tarasiuk.ct.model.dao.ColumnLabel.OFFER_ADDRESS_FROM;
import static by.tarasiuk.ct.model.dao.ColumnLabel.OFFER_ADDRESS_TO;
import static by.tarasiuk.ct.model.dao.ColumnLabel.OFFER_CREATION_DATE;
import static by.tarasiuk.ct.model.dao.ColumnLabel.OFFER_FREIGHT;
import static by.tarasiuk.ct.model.dao.ColumnLabel.OFFER_ID;
import static by.tarasiuk.ct.model.dao.ColumnLabel.OFFER_PRODUCT_NAME;
import static by.tarasiuk.ct.model.dao.ColumnLabel.OFFER_PRODUCT_VOLUME;
import static by.tarasiuk.ct.model.dao.ColumnLabel.OFFER_PRODUCT_WEIGHT;
import static by.tarasiuk.ct.model.dao.ColumnLabel.OFFER_STATUS;

public class OfferDaoBuilder {

    private OfferDaoBuilder() {
    }

    public static Offer build(ResultSet resultSet) throws SQLException {
        long offerId = resultSet.getLong(OFFER_ID);
        long employeeId = resultSet.getLong(EMPLOYEE_ID);
        String productName = resultSet.getString(OFFER_PRODUCT_NAME);
        float productWeight = resultSet.getFloat(OFFER_PRODUCT_WEIGHT);
        float productVolume = resultSet.getFloat(OFFER_PRODUCT_VOLUME);
        String addressFrom = resultSet.getString(OFFER_ADDRESS_FROM);
        String addressTo = resultSet.getString(OFFER_ADDRESS_TO);
        float freight = resultSet.getFloat(OFFER_FREIGHT);
        LocalDate creationDate = resultSet.getDate(OFFER_CREATION_DATE).toLocalDate();
        Offer.Status status = Offer.Status.valueOf(resultSet.getString(OFFER_STATUS));

        Offer offer = new Offer();
        offer.setId(offerId);
        offer.setEmployeeId(employeeId);
        offer.setProductName(productName);
        offer.setProductWeight(productWeight);
        offer.setProductVolume(productVolume);
        offer.setAddressFrom(addressFrom);
        offer.setAddressTo(addressTo);
        offer.setFreight(freight);
        offer.setCreationDate(creationDate);
        offer.setStatus(status);

        return offer;
    }
}
