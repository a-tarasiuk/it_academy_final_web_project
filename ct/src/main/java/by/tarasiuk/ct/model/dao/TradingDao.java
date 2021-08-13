package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.entity.impl.Trading;

import java.util.List;

public interface TradingDao {
    boolean updateTradingStatusById(long tradingId, Trading.Status status) throws DaoException;
    List<Trading> findListTradingsByOfferId(long offerId) throws DaoException;
    List<Trading> findListTradingsByEmployeeId(long employeeId) throws DaoException;
}
