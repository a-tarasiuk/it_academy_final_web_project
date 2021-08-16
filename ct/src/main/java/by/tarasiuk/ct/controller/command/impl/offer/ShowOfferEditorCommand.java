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
import java.util.Map;
import java.util.Optional;

/**
 * Show offer editor page command
 */
public class ShowOfferEditorCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OfferServiceImpl offerService = ServiceProvider.getOfferService();

    @Override
    public String execute(RequestContent content) {
        String page = PagePath.OFFER_EDITOR;
        Map<String, String> parameters = content.getRequestParameters();
        long offerId = Long.parseLong(parameters.get(AttributeName.OFFER_ID));
        LOGGER.info("Found offer ID '{}' for editing.", offerId);

        try {
            Optional<Offer> findOffer = offerService.findOfferById(offerId);
            if(findOffer.isPresent()) {
                Offer offer = findOffer.get();
                content.putRequestAttribute(AttributeName.OFFER, offer);
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_OFFER_EDITOR, e);
            page = PagePath.ACCOUNT_OFFER;
        }

        return page;
    }
}
