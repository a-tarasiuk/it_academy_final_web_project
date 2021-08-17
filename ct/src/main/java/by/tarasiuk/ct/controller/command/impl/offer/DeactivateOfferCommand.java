package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Optional;

/**
 * Deactivate offer command.
 */
public class DeactivateOfferCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OfferServiceImpl offerService = ServiceProvider.getOfferService();

    /**
     * Deactivating an offer by offer ID.
     *
     * @param content   Request data content.
     * @return          Page path.
     * @see             by.tarasiuk.ct.controller.RequestContent
     */
    @Override
    public String execute(RequestContent content) {
        HashMap<String, String> parameters = content.getRequestParameters();

        long offerId = Long.parseLong(parameters.get(AttributeName.OFFER_ID));

        try {
            Optional<Offer> findOffer = offerService.findOfferById(offerId);
            if(findOffer.isPresent()) {
                offerService.closeOfferById(offerId);
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.DEACTIVATE_OFFER, e);
        }

        return PagePath.OFFER_DEACTIVATE_INFO;
    }
}
