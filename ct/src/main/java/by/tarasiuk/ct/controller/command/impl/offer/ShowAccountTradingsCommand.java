package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.controller.command.PagePath;
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

import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.controller.command.AttributeName.TRADING_MAP;
import static by.tarasiuk.ct.controller.command.AttributeName.TRADING_STATUS;

/**
 * Show account list trading command
 */
public class ShowAccountTradingsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private final TradingServiceImpl tradingService = ServiceProvider.getTradingService();
    private final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();

    @Override
    public String execute(RequestContent content) {
        String page;
        HashMap<String, String> offerData = content.getRequestParameters();
        HashMap<String, Object> session = content.getSessionAttributes();

        Account account = (Account) session.get(ACCOUNT);
        long accountId = account.getId();
        Optional<String> optionalStatus = Optional.ofNullable(offerData.get(TRADING_STATUS));

        try {
            Optional<Employee> findEmployee = employeeService.findEmployeeByAccountId(accountId);

            if(findEmployee.isPresent()) {
                Employee employee = findEmployee.get();
                long employeeId = employee.getId();

                List<Trading> tradingList = tradingService.findListTradingsByEmployeeId(employeeId);

                Map<Offer, Trading> tradingMap = new HashMap<>();
                if(tradingList != null && !tradingList.isEmpty()) {
                    if(!optionalStatus.isPresent()) {
                        for(Trading trading: tradingList) {
                            long offerId = trading.getOfferId();
                            Optional<Offer> findOffer = offerService.findOfferById(offerId);

                            if(findOffer.isPresent()) {
                                Offer offer = findOffer.get();
                                tradingMap.put(offer, trading);
                            }
                        }
                    } else {
                        String requestStatus = optionalStatus.get();

                        for (Trading trading : tradingList) {
                            long offerId = trading.getOfferId();
                            Optional<Offer> findOffer = offerService.findOfferById(offerId);

                            if (findOffer.isPresent()) {
                                Offer offer = findOffer.get();
                                Trading.Status status = trading.getStatus();

                                if(status.name().equalsIgnoreCase(requestStatus)) {
                                    tradingMap.put(offer, trading);
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
            page = PagePath.ACCOUNT_TRADING;
        }

        return page;
    }
}
