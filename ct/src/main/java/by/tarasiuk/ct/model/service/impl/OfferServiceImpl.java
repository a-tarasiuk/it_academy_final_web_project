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

    public boolean createOffer(long accountId, HashMap<String, String> offerData) throws ServiceException {
        String productName = offerData.get(OFFER_PRODUCT_NAME);
        float productWeight = Float.parseFloat(offerData.get(OFFER_PRODUCT_WEIGHT));
        float productVolume = Float.parseFloat(offerData.get(OFFER_PRODUCT_VOLUME));
        String addressFrom = offerData.get(OFFER_ADDRESS_FROM);
        String addressTo = offerData.get(OFFER_ADDRESS_TO);
        float freight = Float.parseFloat(offerData.get(OFFER_FREIGHT));
        LocalDate creationDate = LocalDate.now();
        Status status = Status.OPEN;

        Offer offer = new Offer();
        offer.setAccountId(accountId);
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

    public List<Offer> findListOffers() throws ServiceException {
        List<Offer> offers;
        try {
             offers = offerDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Error while find all offers.", e);
            throw new ServiceException("Error while find all offers.", e);
        }
        return offers;
    }

    public List<Offer> findListOffersByAccountId(long accountId) throws ServiceException {
        List<Offer> offers;
        try {
            offers = offerDao.findListOffersByAccountId(accountId);
        } catch (DaoException e) {
            LOGGER.error("Error while find all offers by account id '{}'.", accountId, e);
            throw new ServiceException("Error while find all offers by account id '" + accountId + "'.", e);
        }
        return offers;
    }

    public boolean isValidOfferData(HashMap<String, String> offerData) throws ServiceException {
        return OfferValidator.isValidOfferData(offerData);
    }
}
