package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.entity.impl.Offer;

import java.util.List;

public interface OfferDao {
    boolean updateOfferStatusById(long id, Offer.Status status) throws DaoException;
    List<Offer> findOpenOffers() throws DaoException;
    List<Offer> findOfferListByEmployeeId(long employeeId) throws DaoException;
}
