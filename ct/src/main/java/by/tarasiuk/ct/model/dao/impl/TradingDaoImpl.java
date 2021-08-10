package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.model.dao.OfferDao;
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

public class TradingDaoImpl extends BaseDao<Trading> implements OfferDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TradingDaoImpl instance = new TradingDaoImpl();

    private static final String SQL_PROCEDURE_CREATE_TRADING = "{CALL create_trading (?, ?, ?)}";
    private static final String SQL_PROCEDURE_FIND_TRADINGS_BY_OFFER_ID = "{CALL find_tradings_by_offer_id (?)}";
    private static final String SQL_PROCEDURE_FIND_TRADINGS_BY_EMPLOYEE_ID = "{CALL find_tradings_by_employee_id (?)}";

    private static final class IndexCreate {
        private static final int OFFER_ID = 1;
        private static final int EMPLOYEE_ID = 2;
        private static final int FREIGHT = 3;
    }

    private static final class IndexFind {
        private static final int OFFER_ID = 1;
        private static final int EMPLOYEE_ID = 1;
    }

    private TradingDaoImpl() {
    }

    public static TradingDaoImpl getInstance() {
        return instance;
    }

    public boolean createEntity(Trading trading) throws DaoException {
        Connection connection = connectionPool.getConnection();

        long offerId = trading.getOfferId();
        long employeeId = trading.getEmployeeId();
        float freight = trading.getFreight();

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_CREATE_TRADING)) {
            statement.setLong(IndexCreate.OFFER_ID, offerId);
            statement.setLong(IndexCreate.EMPLOYEE_ID, employeeId);
            statement.setFloat(IndexCreate.FREIGHT, freight);

            statement.executeUpdate();

            LOGGER.info("Trading was successfully created in the database: {}.", trading);
            return true;    //fixme -> statement.executeUpdate(); (см. выше).
        } catch (SQLException e) {
            LOGGER.error("Failed to create trading in the database: {}.", trading, e);
            throw new DaoException("Failed to create trading in the database: " + trading + ".", e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<Trading> findListTradingsByOfferId(long offerId) throws DaoException {
        Connection connection = connectionPool.getConnection();
        List<Trading> tradings = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_TRADINGS_BY_OFFER_ID)) {
            statement.setLong(IndexFind.OFFER_ID, offerId);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Trading trading = TradingDaoBuilder.build(result);
                    tradings.add(trading);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error when performing tradings search by offer id '{}'.", offerId, e);
            throw new DaoException("Error when performing tradings search by offer id '" + offerId + "'.", e);
        } finally {
            closeConnection(connection);
        }

        return tradings;
    }

    public List<Trading> findListTradingsByEmployeeId(long employeeId) throws DaoException {
        Connection connection = connectionPool.getConnection();
        List<Trading> tradings = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_TRADINGS_BY_EMPLOYEE_ID)) {
            statement.setLong(IndexFind.EMPLOYEE_ID, employeeId);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Trading trading = TradingDaoBuilder.build(result);
                    tradings.add(trading);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error when performing tradings search by employee id '{}'.", employeeId, e);
            throw new DaoException("Error when performing tradings search by employee id '" + employeeId + "'.", e);
        } finally {
            closeConnection(connection);
        }

        return tradings;
    }

    @Override
    public List<Trading> findAll() throws DaoException {
        return null;
    }

    @Override
    public Trading updateEntity(Trading entity) throws DaoException {
        return null;
    }

    @Override
    public Optional<Trading> findEntityById(long id) throws DaoException {
        return Optional.empty();
    }
}
