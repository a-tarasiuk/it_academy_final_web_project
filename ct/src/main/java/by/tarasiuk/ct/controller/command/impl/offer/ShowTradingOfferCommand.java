package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.controller.command.AttributeName.OFFER;
import static by.tarasiuk.ct.controller.command.AttributeName.OFFER_ID;

/**
 * Show trading offer page command.
 */
public class ShowTradingOfferCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OfferServiceImpl offerService = ServiceProvider.getOfferService();

    /**
     * Search in the database for an offer by ID.
     * If successful - transfer of the offer object to the page.
     *
     * @param content   Request data content.
     * @return          Page path.
     * @see             by.tarasiuk.ct.controller.RequestContent
     */
    @Override
    public String execute(RequestContent content) {
        String page;
        Map<String, String> offerData = content.getRequestParameters();
        long offerId = Long.parseLong(offerData.get(OFFER_ID));

        try {
            Optional<Offer> findOffer = offerService.findOfferById(offerId);

            if(findOffer.isPresent()) {
                Offer offer = findOffer.get();
                content.putRequestAttribute(OFFER, offer);
                page = PagePath.ACCOUNT_TRADING;
            } else {
                page = PagePath.OPEN_OFFERS;
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_TRADING_OFFER, e);
            page = PagePath.OPEN_OFFERS;
        }

        return page;
    }
}
