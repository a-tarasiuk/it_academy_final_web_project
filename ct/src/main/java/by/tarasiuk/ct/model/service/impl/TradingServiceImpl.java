package by.tarasiuk.ct.model.service.impl;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.dao.DaoProvider;
import by.tarasiuk.ct.model.dao.impl.TradingDaoImpl;
import by.tarasiuk.ct.model.entity.impl.Trading;
import by.tarasiuk.ct.model.service.TradingService;
import by.tarasiuk.ct.validator.TradingValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Optional;

public class TradingServiceImpl implements TradingService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TradingServiceImpl instance = new TradingServiceImpl();
    private final TradingDaoImpl tradingDao = DaoProvider.getTradingDao();

    private TradingServiceImpl() {
    }

    public static TradingServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createTrading(long offerId, long employeeId, float freight) throws ServiceException {
        Trading trading = new Trading();
        trading.setOfferId(offerId);
        trading.setEmployeeId(employeeId);
        trading.setFreight(freight);
        trading.setStatus(Trading.Status.NOT_ACCEPTED);

        try {
            return tradingDao.createEntity(trading);
        } catch (DaoException e) {
            LOGGER.error("Error while creating trading '{}'.", trading, e);
            throw new ServiceException("Error while creating trading '" + trading +"'.", e);
        }
    }

    @Override
    public List<Trading> findListTradingsByOfferId(long offerId) throws ServiceException {
        List<Trading> tradings;
        try {
            tradings = tradingDao.findListTradingsByOfferId(offerId);
        } catch (DaoException e) {
            LOGGER.error("Error while find all tradings by offer id '{}'.", offerId, e);
            throw new ServiceException("Error while find all tradings by offer id '" + offerId + "'.", e);
        }
        return tradings;
    }

    public List<Trading> findListTradingsByEmployeeId(long employeeId) throws ServiceException {
        List<Trading> tradings;
        try {
            tradings = tradingDao.findListTradingsByEmployeeId(employeeId);
        } catch (DaoException e) {
            LOGGER.error("Error while find all tradings by employee id '{}'.", employeeId, e);
            throw new ServiceException("Error while find all tradings by employee id '" + employeeId + "'.", e);
        }
        return tradings;
    }

    @Override
    public Optional<Trading> findTradingById(long tradingId) throws ServiceException {
        try {
            return tradingDao.findEntityById(tradingId);
        } catch (DaoException e) {
            LOGGER.error("Error while find trading by id '{}'.", tradingId, e);
            throw new ServiceException("Error while find trading by id '" + tradingId + "'.", e);
        }
    }

    @Override
    public boolean acceptTradingById(long tradingId) throws ServiceException {
        Trading.Status status = Trading.Status.ACCEPTED;

        try {
            return tradingDao.updateTradingStatusById(tradingId, status);
        } catch (DaoException e) {
            LOGGER.error("Error while find trading by id '{}'.", tradingId, e);
            throw new ServiceException("Error while find trading by id '" + tradingId + "'.", e);
        }
    }

    @Override
    public boolean isFoundTradingByOfferIdAndEmployeeId(long offerId, long employeeId) throws ServiceException {
        Optional<Trading> findTrading;
        try {
            findTrading = tradingDao.findTradingByOfferIdAndEmployeeIf (offerId, employeeId);
        } catch (DaoException e) {
            LOGGER.error("Error while find trading by offer ID '{}' and employee ID '{}'.", offerId, employeeId, e);
            throw new ServiceException("Error while find trading by offer ID '" + offerId + "' and employee ID '" + employeeId + "'.", e);
        }

        return findTrading.isPresent();
    }

    @Override
    public boolean isValidFreight(String freight) throws ServiceException {
        return TradingValidator.isValidFreight(freight);
    }
}
