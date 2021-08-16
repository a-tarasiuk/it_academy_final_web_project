package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;

/**
 * SUpdate offer information command
 */
public class UpdateOfferCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OfferServiceImpl offerService = ServiceProvider.getOfferService();

    @Override
    public String execute(RequestContent content) {
        String page = PagePath.OFFER_EDIT_INFO;
        Map<String, String> offerData = content.getRequestParameters();

        try {
            if(!offerService.isValidOfferData(offerData)) {
                content.putRequestAttributes(offerData);
                page = PagePath.OFFER_EDITOR;
            } else {
                offerService.updateOffer(offerData);
                long offerId = Long.parseLong(offerData.get(AttributeName.OFFER_ID));
                content.putRequestAttribute(AttributeName.OFFER_ID, offerId);
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.UPDATE_OFFER, e);
            page = PagePath.OFFER_EDITOR;
        }

        return page;
    }
}
