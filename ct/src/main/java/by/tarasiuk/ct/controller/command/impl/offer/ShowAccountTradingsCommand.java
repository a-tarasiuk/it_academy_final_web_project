package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.entity.impl.Trading;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import by.tarasiuk.ct.model.service.impl.TradingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.*;


public class ShowAccountTradingsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private static final TradingServiceImpl tradingService = ServiceProvider.getTradingService();

    @Override
    public String execute(RequestContent content) {
        String page;
        HashMap<String, String> offerData = content.getRequestParameters();
        HashMap<String, Object> session = content.getSessionAttributes();

        Account account = (Account) session.get(ACCOUNT);
        long accountId = account.getId();
        String findStatus = offerData.get(OFFER_STATUS);

        try {
            List<Trading> tradingList = tradingService.findListTradingsByEmployeeId(accountId); //fixme employee

            if(findStatus == null) {
                content.putRequestAttribute(TRADING_LIST, tradingList);
            } else {
                Map<Offer, Float> findTradingsByOfferStatus = new HashMap<>();
                for (Trading trading : tradingList) {
                    long offerId = trading.getOfferId();
                    Optional<Offer> findOffer = offerService.findOfferById(offerId);
                    if (findOffer.isPresent()) {
                        Offer.Status status;
                        try {
                            status = Offer.Status.valueOf(findStatus.toUpperCase());
                            Offer offer = findOffer.get();
                            if(offer.getStatus().equals(status)) {
                                float freight = trading.getFreight();
                                findTradingsByOfferStatus.put(offer, freight);
                            }
                        } catch (IllegalArgumentException e) {
                            LOGGER.warn("Status value '{}' doesn't exist in the list of values on '{}'.", findStatus, Offer.Status.class);
                            throw new IllegalArgumentException("Status value '" + findStatus + "' doesn't exist in the list of values on '" + Offer.Status.class + "'.", e);    //fixme
                        }
                    }
                }
                content.putRequestAttribute(TRADING_MAP, findTradingsByOfferStatus);
            }

            page = PagePath.ACCOUNT_TRADINGS;
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_OFFERS, e);
            page = PagePath.TRADING;
        }

        return page;
    }
}
