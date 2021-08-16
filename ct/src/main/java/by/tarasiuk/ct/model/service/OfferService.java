package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Offer;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service that works with offer data.
 */
public interface OfferService {
    /**
     * Create offer entity.
     *
     * @param employeeId            Employee ID.
     * @param offerData             Offer data.
     * @return                      <code>true</code> if the offer has been created.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean createOffer(long employeeId, Map<String, String> offerData) throws ServiceException;

    /**
     * Update offer entity.
     *
     * @param offerData             Offer data.
     * @return                      <code>true</code> if the offer has been updated.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean updateOffer(Map<String, String> offerData) throws ServiceException;

    /**
     * Close offer by offer ID.
     *
     * @param id                    Offer ID.
     * @return                      <code>true</code> if the offer has been closed.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean closeOfferById(long id) throws ServiceException;

    /**
     * Validate offer data.
     *
     * @param offerData             Offer data.
     * @return                      <code>true</code> if the is valid offer data.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean isValidOfferData(Map<String, String> offerData) throws ServiceException;

    /**
     * Find all offers.
     *
     * @return                      List of offer.
     * @throws ServiceException     Default exception of service layer.
     */
    List<Offer> findAllOfferList() throws ServiceException;

    /**
     * Find offers with status is OPEN.
     *
     * @return                      List of offer.
     * @throws ServiceException     Default exception of service layer.
     * @see                         Offer.Status
     */
    List<Offer> findOpenOfferList() throws ServiceException;

    /**
     * Find offer list with status is OPEN by employee ID.
     *
     * @param employeeId            Employee ID.
     * @return                      List of offer.
     * @throws ServiceException     Default exception of service layer.
     * @see                         Offer.Status
     */
    List<Offer> findOfferListByEmployeeId(long employeeId) throws ServiceException;

    /**
     * Find offer entity by offer ID.
     *
     * @param offerId               Offer ID.
     * @return                      Optional of offer.
     * @throws ServiceException     Default exception of service layer.
     */
    Optional<Offer> findOfferById(long offerId) throws ServiceException;
}
