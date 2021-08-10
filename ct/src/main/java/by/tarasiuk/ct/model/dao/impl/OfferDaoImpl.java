package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.model.dao.OfferDao;
import by.tarasiuk.ct.model.dao.builder.OfferDaoBuilder;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.entity.impl.Offer.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OfferDaoImpl extends BaseDao<Offer> implements OfferDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final OfferDaoImpl instance = new OfferDaoImpl();

    private static final String SQL_PROCEDURE_CREATE_OFFER = "{CALL create_offer (?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String SQL_PROCEDURE_FIND_ALL_OFFERS = "{CALL find_all_offers ()}";
    private static final String SQL_PROCEDURE_FIND_OPEN_OFFERS = "{CALL find_open_offers ()}";
    private static final String SQL_PROCEDURE_FIND_OFFER_BY_ID = "{CALL find_offer_by_id (?)}";
    private static final String SQL_PROCEDURE_FIND_ALL_OFFERS_BY_ACCOUNT_ID = "{CALL find_all_offers_by_account_id (?)}";

    private OfferDaoImpl() {
    }

    public static OfferDaoImpl getInstance() {
        return instance;
    }

    private static final class IndexCreate {
        private static final int EMPLOYEE_ID = 1;
        private static final int PRODUCT_NAME = 2;
        private static final int PRODUCT_WEIGHT = 3;
        private static final int PRODUCT_VOLUME = 4;
        private static final int ADDRESS_FROM = 5;
        private static final int ADDRESS_TO = 6;
        private static final int FREIGHT = 7;
        private static final int CREATION_DATE = 8;
        private static final int STATUS = 9;
    }

    private static final class IndexFind {
        private static final int OFFER_ID = 1;
    }

    public boolean createEntity(Offer offer) throws DaoException {
        Connection connection = connectionPool.getConnection();

        long employeeId = offer.getEmployeeId();
        String productName = offer.getProductName();
        float productWeight = offer.getProductWeight();
        float productVolume = offer.getProductVolume();
        String addressFrom = offer.getAddressFrom();
        String addressTo = offer.getAddressTo();
        float freight = offer.getFreight();
        LocalDate creationDate = offer.getCreationDate();
        Status status = offer.getStatus();

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_CREATE_OFFER)) {
            statement.setLong(IndexCreate.EMPLOYEE_ID, employeeId);
            statement.setString(IndexCreate.PRODUCT_NAME, productName);
            statement.setFloat(IndexCreate.PRODUCT_WEIGHT, productWeight);
            statement.setFloat(IndexCreate.PRODUCT_VOLUME, productVolume);
            statement.setString(IndexCreate.ADDRESS_FROM, addressFrom);
            statement.setString(IndexCreate.ADDRESS_TO, addressTo);
            statement.setFloat(IndexCreate.FREIGHT, freight);
            statement.setDate(IndexCreate.CREATION_DATE, Date.valueOf(creationDate));
            statement.setString(IndexCreate.STATUS, status.name());

            statement.executeUpdate();

            LOGGER.info("Offer was successfully created in the database: {}.", offer);
            return true;    //fixme -> statement.executeUpdate(); (см. выше).
        } catch (SQLException e) {
            LOGGER.error("Failed to create offer in the database: {}.", offer, e);
            throw new DaoException("Failed to create offer in the database: " + offer + ".", e);
        } finally {
            closeConnection(connection);
        }
    }


    @Override
    public List<Offer> findAll() throws DaoException {
        Connection connection = connectionPool.getConnection();
        List<Offer> offers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_ALL_OFFERS);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                Offer offer = OfferDaoBuilder.build(result);
                offers.add(offer);
            }
        } catch (SQLException e) {
            LOGGER.error("Error when find all offers.", e);
            throw new DaoException("Error when find all offers.", e);
        } finally {
            closeConnection(connection);
        }

        return offers;
    }

    public List<Offer> findOpenOffers() throws DaoException {
        Connection connection = connectionPool.getConnection();
        List<Offer> offers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_OPEN_OFFERS);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                Offer offer = OfferDaoBuilder.build(result);
                offers.add(offer);
            }
        } catch (SQLException e) {
            LOGGER.error("Error when find all offers.", e);
            throw new DaoException("Error when find all offers.", e);
        } finally {
            closeConnection(connection);
        }

        return offers;
    }

    public List<Offer> findListOffersByAccountId(long accountId) throws DaoException {
        Connection connection = connectionPool.getConnection();
        List<Offer> offers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_ALL_OFFERS_BY_ACCOUNT_ID)) {
            statement.setLong(IndexCreate.EMPLOYEE_ID, accountId);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Offer offer = OfferDaoBuilder.build(result);
                    offers.add(offer);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error when performing offers search by account id '{}'.", accountId, e);
            throw new DaoException("Error when performing offers search by account id '" + accountId + "'.", e);
        } finally {
            closeConnection(connection);
        }

        return offers;
    }

    @Override
    public Offer updateEntity(Offer entity) throws DaoException {
        return null;
    }

    @Override
    public Optional<Offer> findEntityById(long id) throws DaoException {
        Connection connection = connectionPool.getConnection();
        Offer offer = null;

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_OFFER_BY_ID)) {
            statement.setLong(IndexFind.OFFER_ID, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    offer = OfferDaoBuilder.build(result);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error when performing offer search by id '{}'.", id, e);
            throw new DaoException("Error when performing offer search by id '" + id + "'.", e);
        } finally {
            closeConnection(connection);
        }

        return Optional.ofNullable(offer);
    }
}
