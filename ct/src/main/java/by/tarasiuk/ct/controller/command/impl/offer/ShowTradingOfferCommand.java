package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.AttributeName;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import by.tarasiuk.ct.model.service.impl.TradingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.EMPLOYEE_ID;
import static by.tarasiuk.ct.manager.AttributeName.OFFER;
import static by.tarasiuk.ct.manager.AttributeName.OFFER_ID;


public class ShowTradingOfferCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private static final TradingServiceImpl tradingService = ServiceProvider.getTradingService();

    @Override
    public String execute(RequestContent content) {
        String page;
        HashMap<String, String> offerData = content.getRequestParameters();
        long offerId = Long.parseLong(offerData.get(OFFER_ID));

        try {
            Optional<Offer> findOffer = offerService.findOfferById(offerId);
            if(findOffer.isPresent()) {
                Offer offer = findOffer.get();

                HashMap<String, Object> sessionAttributes = content.getSessionAttributes();
                Account currentAccount = (Account) sessionAttributes.get(AttributeName.ACCOUNT);
                long currentAccountId = currentAccount.getId();
                Optional<Employee> findEmployee = employeeService.findEmployeeByAccountId(currentAccountId);

                if(findEmployee.isPresent()) {
                    Employee employee = findEmployee.get();
                    long currentEmployeeId = employee.getId();
                    content.putRequestAttribute(OFFER, offer);
                    content.putRequestAttribute(EMPLOYEE_ID, currentEmployeeId);
                }

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
