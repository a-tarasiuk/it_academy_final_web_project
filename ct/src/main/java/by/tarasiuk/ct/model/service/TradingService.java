package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Trading;

import java.util.List;
import java.util.Optional;

public interface TradingService {
    boolean createTrading(long offerId, long employeeId, float freight) throws ServiceException;
    boolean acceptTradingById(long tradingId) throws ServiceException;
    boolean isValidFreight(String freight) throws ServiceException;
    List<Trading> findListTradingsByOfferId(long offerId) throws ServiceException;
    Optional<Trading> findTradingById(long tradingId) throws ServiceException;
}
