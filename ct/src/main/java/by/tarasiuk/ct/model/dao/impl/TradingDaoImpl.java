package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.model.dao.TradingDao;
import by.tarasiuk.ct.model.dao.builder.TradingDaoBuilder;
import by.tarasiuk.ct.model.entity.impl.Trading;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TradingDaoImpl extends BaseDao<Trading> implements TradingDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TradingDaoImpl instance = new TradingDaoImpl();

    private static final String SQL_PROCEDURE_CREATE_TRADING = "{CALL create_trading (?, ?, ?, ?)}";
    private static final String SQL_PROCEDURE_FIND_TRADING_BY_ID = "{CALL find_trading_by_id (?)}";
    private static final String SQL_PROCEDURE_FIND_TRADINGS_BY_OFFER_ID = "{CALL find_tradings_by_offer_id (?)}";
    private static final String SQL_PROCEDURE_FIND_TRADINGS_BY_EMPLOYEE_ID = "{CALL find_tradings_by_employee_id (?)}";
    private static final String SQL_PROCEDURE_UPDATE_TRADING_BY_OFFER_ID_AND_EMPLOYEE_ID = "{CALL find_trading_by_offer_id_and_employee_id (?, ?)}";
    private static final String SQL_PROCEDURE_UPDATE_TRADING_STATUS_BY_ID = "{CALL update_trading_status_by_id (?, ?)}";

    private static final class IndexCreate {
        private static final int OFFER_ID = 1;
        private static final int EMPLOYEE_ID = 2;
        private static final int TRADING_FREIGHT = 3;
        private static final int TRADING_STATUS = 4;
    }

    private static final class IndexFind {
        private static final int TRADING_ID = 1;
        private static final int OFFER_ID = 1;
        private static final int EMPLOYEE_ID = 1;
    }

    private static final class IndexUpdate {
        private static final int TRADING_ID = 1;
        private static final int TRADING_STATUS = 2;
    }

    private TradingDaoImpl() {
    }

    public static TradingDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean createEntity(Trading trading) throws DaoException {
        boolean result;

        long offerId = trading.getOfferId();
        long employeeId = trading.getEmployeeId();
        float freight = trading.getFreight();
        Trading.Status status = trading.getStatus();

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_CREATE_TRADING)) {
            statement.setLong(IndexCreate.OFFER_ID, offerId);
            statement.setLong(IndexCreate.EMPLOYEE_ID, employeeId);
            statement.setFloat(IndexCreate.TRADING_FREIGHT, freight);
            statement.setString(IndexCreate.TRADING_STATUS, status.name());

            int rowCount = statement.executeUpdate();
            result = rowCount == 1;

            LOGGER.info(result ? "Trading was successfully created in the database: {}."
                    : "Failed to create trading '{}'.", trading);
        } catch (SQLException e) {
            LOGGER.error("Failed to create trading in the database: {}.", trading, e);
            throw new DaoException("Failed to create trading in the database: " + trading + ".", e);
        }

        return result;
    }

    @Override
    public boolean updateTradingStatusById(long tradingId, Trading.Status status) throws DaoException {
        boolean result;

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_UPDATE_TRADING_STATUS_BY_ID)) {
            statement.setLong(IndexUpdate.TRADING_ID, tradingId);
            statement.setString(IndexUpdate.TRADING_STATUS, status.name());

            int rowCount = statement.executeUpdate();
            result = rowCount == 1;

            LOGGER.info(result ? "Trading with ID '{}' has been successfully status to '{}' in the database."
                    : "Failed to update trading status '{}' for trading with ID '{}'.", tradingId, status);
        } catch (SQLException e) {
            LOGGER.error("Failed updating trading with ID '{}' to status '{}' in the database.", tradingId, status, e);
            throw new DaoException("Failed updating trading with ID '" + tradingId + "' to status '" + status + "' in the database.", e);
        }

        return result;
    }

    @Override
    public List<Trading> findListTradingsByOfferId(long offerId) throws DaoException {
        List<Trading> tradings = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_TRADINGS_BY_OFFER_ID)) {
                statement.setLong(IndexFind.OFFER_ID, offerId);

                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        Trading trading = TradingDaoBuilder.build(result);
                        tradings.add(trading);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error when performing tradings search by offer id '{}'.", offerId, e);
            throw new DaoException("Error when performing tradings search by offer id '" + offerId + "'.", e);
        }

        return tradings;
    }

    @Override
    public List<Trading> findListTradingsByEmployeeId(long employeeId) throws DaoException {
        List<Trading> tradings = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_TRADINGS_BY_EMPLOYEE_ID)) {
                statement.setLong(IndexFind.EMPLOYEE_ID, employeeId);

                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        Trading trading = TradingDaoBuilder.build(result);
                        tradings.add(trading);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error when performing tradings search by employee id '{}'.", employeeId, e);
            throw new DaoException("Error when performing tradings search by employee id '" + employeeId + "'.", e);
        }

        return tradings;
    }

    @Override
    public List<Trading> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateEntity(Trading entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Trading> findTradingByOfferIdAndEmployeeIf(long offerId, long employeeId) throws DaoException {
        Optional<Trading> findTrading = Optional.empty();

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_UPDATE_TRADING_BY_OFFER_ID_AND_EMPLOYEE_ID)) {
                statement.setLong(IndexFind.OFFER_ID, offerId);
                statement.setLong(2, employeeId);

                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        Trading trading = TradingDaoBuilder.build(result);
                        findTrading = Optional.of(trading);
                        LOGGER.debug("Trading for offer ID '{}' and employee ID '{}' was fount successfully in the database.", offerId, employeeId);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error when performing tradings search by offer ID '{}' and employee ID '{}'.", offerId, employeeId, e);
            throw new DaoException("Error when performing tradings search by offer ID '" + offerId + "' and employee ID '" + employeeId + "'.", e);
        }

        return findTrading;
    }

    @Override
    public Optional<Trading> findEntityById(long id) throws DaoException {
        Optional<Trading> findTrading = Optional.empty();

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_TRADING_BY_ID)) {
                statement.setLong(IndexFind.TRADING_ID, id);

                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        Trading trading = TradingDaoBuilder.build(result);
                        findTrading = Optional.of(trading);
                        LOGGER.debug("Trading with ID '{}' was fount successfully in the database.", id);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error when performing tradings search by id '{}'.", id, e);
            throw new DaoException("Error when performing tradings search by id '" + id + "'.", e);
        }

        return findTrading;
    }
}
