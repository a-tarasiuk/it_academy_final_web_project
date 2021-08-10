package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

import static by.tarasiuk.ct.manager.AttributeName.OFFER_LIST;


public class ShowOpenOffersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final OfferServiceImpl offerService = ServiceProvider.getOfferService();

    @Override
    public String execute(RequestContent content) {
        String page;
        HashMap<String, Object> session = content.getSessionAttributes();

        try {
            List<Offer> openOfferList = offerService.findOpenOfferList();
            content.putRequestAttribute(OFFER_LIST, openOfferList);
            page = PagePath.OPEN_OFFERS;
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_OFFERS, e);
            page = PagePath.TRADING;
        }

        return page;
    }
}
