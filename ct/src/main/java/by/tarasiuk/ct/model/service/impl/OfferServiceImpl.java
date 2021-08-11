package by.tarasiuk.ct.model.service.impl;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.dao.DaoProvider;
import by.tarasiuk.ct.model.dao.impl.OfferDaoImpl;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.service.OfferService;
import by.tarasiuk.ct.util.OfferValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.OFFER_ADDRESS_FROM;
import static by.tarasiuk.ct.manager.AttributeName.OFFER_ADDRESS_TO;
import static by.tarasiuk.ct.manager.AttributeName.OFFER_FREIGHT;
import static by.tarasiuk.ct.manager.AttributeName.OFFER_PRODUCT_NAME;
import static by.tarasiuk.ct.manager.AttributeName.OFFER_PRODUCT_VOLUME;
import static by.tarasiuk.ct.manager.AttributeName.OFFER_PRODUCT_WEIGHT;
import static by.tarasiuk.ct.model.entity.impl.Offer.Status;

public class OfferServiceImpl implements OfferService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final OfferServiceImpl instance = new OfferServiceImpl();
    private static final OfferDaoImpl offerDao = DaoProvider.getOfferDao();

    private OfferServiceImpl() {
    }

    public static OfferServiceImpl getInstance() {
        return instance;
    }

    public boolean createOffer(long employeeId, HashMap<String, String> offerData) throws ServiceException {
        float productWeight = Float.parseFloat(offerData.get(OFFER_PRODUCT_WEIGHT));
        float productVolume = Float.parseFloat(offerData.get(OFFER_PRODUCT_VOLUME));
        float freight = Float.parseFloat(offerData.get(OFFER_FREIGHT));
        String productName = offerData.get(OFFER_PRODUCT_NAME);
        String addressFrom = offerData.get(OFFER_ADDRESS_FROM);
        String addressTo = offerData.get(OFFER_ADDRESS_TO);
        LocalDate creationDate = LocalDate.now();
        Status status = Status.OPEN;

        Offer offer = new Offer();
        offer.setEmployeeId(employeeId);
        offer.setProductName(productName);
        offer.setProductWeight(productWeight);
        offer.setProductVolume(productVolume);
        offer.setAddressFrom(addressFrom);
        offer.setAddressTo(addressTo);
        offer.setFreight(freight);
        offer.setCreationDate(creationDate);
        offer.setStatus(status);

        try {
            return offerDao.createEntity(offer);
        } catch (DaoException e) {
            LOGGER.error("Error while creating offer '{}'.", offer, e);
            throw new ServiceException("Error while creating offer '" + offer +"'.", e);
        }
    }

    public List<Offer> findAllOfferList() throws ServiceException {
        List<Offer> offers;
        try {
             offers = offerDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Error while find all offers.", e);
            throw new ServiceException("Error while find all offers.", e);
        }
        return offers;
    }

    public List<Offer> findOpenOfferList() throws ServiceException {
        List<Offer> offers;
        try {
            offers = offerDao.findOpenOffers();
        } catch (DaoException e) {
            LOGGER.error("Error while find all opened offers.", e);
            throw new ServiceException("Error while find all opened offers.", e);
        }
        return offers;
    }

    public List<Offer> findOfferListByEmployeeId(long employeeId) throws ServiceException {
        List<Offer> offers;
        try {
            offers = offerDao.findOfferListByEmployeeId(employeeId);
        } catch (DaoException e) {
            LOGGER.error("Error while find all offers by employee id '{}'.", employeeId, e);
            throw new ServiceException("Error while find all offers by employee id '" + employeeId + "'.", e);
        }
        return offers;
    }

    public Optional<Offer> findOfferById(long offerId) throws ServiceException {
        try {
            return offerDao.findEntityById(offerId);
        } catch (DaoException e) {
            LOGGER.error("Error while find offer by id '{}'.", offerId, e);
            throw new ServiceException("Error while find offer by id '" + offerId + "'.", e);
        }
    }

    public boolean isValidOfferData(HashMap<String, String> offerData) throws ServiceException {
        return OfferValidator.isValidOfferData(offerData);
    }
}
