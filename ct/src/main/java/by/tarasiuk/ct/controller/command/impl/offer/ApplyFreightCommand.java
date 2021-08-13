package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.entity.impl.Trading;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import by.tarasiuk.ct.model.service.impl.TradingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class ApplyFreightCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private final TradingServiceImpl tradingService = ServiceProvider.getTradingService();

    @Override
    public String execute(RequestContent content) {
        String page = PagePath.FREIGHT_APPLY;
        Map<String, String> parameters = content.getRequestParameters();
        long tradingId = Long.parseLong(parameters.get(AttributeName.TRADING_ID));

        try {
            Optional<Trading> findTrading = tradingService.findTradingById(tradingId);

            if(findTrading.isPresent()) {
                Trading trading = findTrading.get();
                long offerId = trading.getOfferId();

                Optional<Offer> findOffer = offerService.findOfferById(offerId);
                if(findOffer.isPresent()) {
                    Offer offer = findOffer.get();

                    offerService.closeOfferById(offerId);
                    tradingService.acceptTradingById(tradingId);

                    LOGGER.info("Trading '{}' successfully accepted and offer '{}' was closed.", trading, offer);
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_TRADING_VIEWER, e);
            page = PagePath.ACCOUNT_OFFER;
        }

        return page;
    }
}
