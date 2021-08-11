package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.entity.impl.Trading;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import by.tarasiuk.ct.model.service.impl.TradingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.manager.AttributeName.OFFER_STATUS;
import static by.tarasiuk.ct.manager.AttributeName.TRADING_MAP;


public class ShowAccountTradingsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private static final TradingServiceImpl tradingService = ServiceProvider.getTradingService();
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();

    @Override
    public String execute(RequestContent content) {
        String page;
        HashMap<String, String> offerData = content.getRequestParameters();
        HashMap<String, Object> session = content.getSessionAttributes();

        Account account = (Account) session.get(ACCOUNT);
        long accountId = account.getId();
        Optional<String> optionalStatus = Optional.ofNullable(offerData.get(OFFER_STATUS));

        try {
            Optional<Employee> findEmployee = employeeService.findEmployeeByAccountId(accountId);

            if(findEmployee.isPresent()) {
                Employee employee = findEmployee.get();
                long employeeId = employee.getId();

                List<Trading> tradingList = tradingService.findListTradingsByEmployeeId(employeeId);

                Map<Offer, Float> tradingMap = new HashMap<>();
                if(tradingList != null && !tradingList.isEmpty()) {
                    if(!optionalStatus.isPresent()) {
                        for(Trading trading: tradingList) {
                            long offerId = trading.getOfferId();
                            Optional<Offer> findOffer = offerService.findOfferById(offerId);

                            if(findOffer.isPresent()) {
                                Offer offer = findOffer.get();
                                float freight = trading.getFreight();
                                tradingMap.put(offer, freight);
                            }
                        }
                    } else {
                        String requestStatus = optionalStatus.get();

                        for (Trading trading : tradingList) {
                            long offerId = trading.getOfferId();
                            Optional<Offer> findOffer = offerService.findOfferById(offerId);

                            if (findOffer.isPresent()) {
                                Offer.Status status;

                                try {
                                    status = Offer.Status.valueOf(requestStatus.toUpperCase());

                                    Offer offer = findOffer.get();
                                    if(offer.getStatus().equals(status)) {
                                        float freight = trading.getFreight();
                                        tradingMap.put(offer, freight);
                                    }
                                } catch (IllegalArgumentException e) {
                                    LOGGER.warn("Status value '{}' doesn't exist in the list of values on '{}'.", optionalStatus, Offer.Status.class);
                                    throw new IllegalArgumentException("Status value '" + optionalStatus + "' doesn't exist in the list of values on '" + Offer.Status.class + "'.", e);    //fixme
                                }
                            }
                        }
                    }

                    content.putRequestAttribute(TRADING_MAP, tradingMap);
                }
            }

            page = PagePath.ACCOUNT_TRADINGS;
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_OFFERS, e);
            page = PagePath.TRADING;
        }

        return page;
    }
}
