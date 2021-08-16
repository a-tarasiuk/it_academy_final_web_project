package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Trading;
import java.util.List;
import java.util.Optional;

/**
 * Service that works with trading data.
 *
 * @see by.tarasiuk.ct.model.entity.impl.Trading
 * @see by.tarasiuk.ct.model.dao.TradingDao
 * @see by.tarasiuk.ct.model.dao.impl.TradingDaoImpl
 */
public interface TradingService {
    /**
     * Create trading entity.
     *
     * @param offerId               Offer ID.
     * @param employeeId            Employee ID.
     * @param freight               Offer freight.
     * @return                      <code>true</code> if the trading entity has been created.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean createTrading(long offerId, long employeeId, float freight) throws ServiceException;

    /**
     * Set trading entity flag to <code>ACCEPTED</code> by trading ID.
     *
     * @param tradingId             Trading ID.
     * @return                      <code>true</code> if the trading status has been updated.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     * @see                         Trading.Status
     */
    boolean acceptTradingById(long tradingId) throws ServiceException;

    /**
     * Validate offer freight value.
     *
     * @param freight               Offer freight.
     * @return                      <code>true</code> if the freight value is valid.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean isValidFreight(String freight) throws ServiceException;

    /**
     * Find trading list by offer with ID.
     *
     * @param offerId               Offer ID.
     * @return                      List of trading entity.
     * @throws ServiceException     Default exception of service layer.
     */
    List<Trading> findListTradingsByOfferId(long offerId) throws ServiceException;

    /**
     * Find trading entity by trading ID.
     *
     * @param tradingId             Trading ID.
     * @return                      Optional of trading.
     * @throws ServiceException     Default exception of service layer.
     */
    Optional<Trading> findTradingById(long tradingId) throws ServiceException;
}
