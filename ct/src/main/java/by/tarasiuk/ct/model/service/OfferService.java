package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Offer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OfferService {
    boolean createOffer(long employeeId, Map<String, String> offerData) throws ServiceException;
    boolean updateOffer(Map<String, String> offerData) throws ServiceException;
    boolean closeOfferById(long id) throws ServiceException;
    boolean isValidOfferData(Map<String, String> offerData) throws ServiceException;
    List<Offer> findAllOfferList() throws ServiceException;
    List<Offer> findOpenOfferList() throws ServiceException;
    List<Offer> findOfferListByEmployeeId(long employeeId) throws ServiceException;
    Optional<Offer> findOfferById(long offerId) throws ServiceException;
}
