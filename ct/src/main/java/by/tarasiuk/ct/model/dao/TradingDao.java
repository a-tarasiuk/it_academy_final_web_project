package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.entity.impl.Trading;
import java.util.List;

/**
 * Interface for operations with the data of the trading,
 * which are contained in the in the database table <code>tradings</code>.
 */
public interface TradingDao {

    /**
     * Update trading entity status by trading ID in the database.
     *
     * @param tradingId         Trading ID.
     * @param status            Trading status.
     * @return                  <code>true</code> if the password is successfully updated in the database.
     *                          Otherwise return <code>false</code>.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement.
     */
    boolean updateTradingStatusById(long tradingId, Trading.Status status) throws DaoException;

    /**
     * Find trading list by offer ID in the database..
     *
     * @param offerId           Offer ID.
     * @return                  List of the tradings.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or an argument is supplied to this method.
     */
    List<Trading> findListTradingsByOfferId(long offerId) throws DaoException;

    /**
     * Find trading list by employee ID in the database.
     *
     * @param employeeId        Employee ID.
     * @return                  List of the tradings.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or an argument is supplied to this method.
     */
    List<Trading> findListTradingsByEmployeeId(long employeeId) throws DaoException;
}
