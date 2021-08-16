package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.entity.impl.Offer;

import java.util.List;

/**
 * Interface for operations with the data of the offer,
 * which are contained in the in the database table <code>offers</code>.
 */
public interface OfferDao {

    /**
     * Update offer status by offer ID.
     *
     * @param id                Offer ID.
     * @param status            New offer status.
     * @return                  <code>true</code> if the offer status is successfully updated in the database.
     *                          Otherwise return <code>false</code>.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement.
     */
    boolean updateOfferStatusById(long id, Offer.Status status) throws DaoException;

    /**
     * Find offer list with <code>OPEN</code> status.
     *
     * @return                  List of the offers.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or the SQL statement does not return a ResultSet object.
     */
    List<Offer> findOpenOffers() throws DaoException;

    /**
     * Find offer list with the employee by ID.
     *
     * @param employeeId        Employee ID.
     * @return                  List of the employees.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or the SQL statement does not return a ResultSet object.
     */
    List<Offer> findOfferListByEmployeeId(long employeeId) throws DaoException;
}
