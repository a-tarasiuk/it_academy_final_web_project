package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static by.tarasiuk.ct.controller.command.AttributeName.OFFER_LIST;


public class ShowOpenOffersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OfferServiceImpl offerService = ServiceProvider.getOfferService();

    @Override
    public String execute(RequestContent content) {
        String page = PagePath.ACCOUNT_TRADING;

        try {
            List<Offer> offerList = offerService.findOpenOfferList();
            List<Offer> openOfferList = offerList.stream()
                    .filter(offer -> offer.getStatus().equals(Offer.Status.OPEN))
                    .collect(Collectors.toList());

            content.putRequestAttribute(OFFER_LIST, openOfferList);
            page = PagePath.OPEN_OFFERS;
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_OFFERS, e);
        }

        return page;
    }
}
