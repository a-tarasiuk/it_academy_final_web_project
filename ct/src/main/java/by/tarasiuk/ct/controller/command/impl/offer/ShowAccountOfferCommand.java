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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Optional;


public class ShowAccountOfferCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();

    @Override
    public String execute(RequestContent content) {
        String page = PagePath.ACCOUNT_OFFERS;
        HashMap<String, String> offerData = content.getRequestParameters();
        HashMap<String, Object> session = content.getSessionAttributes();

        Account account = (Account) session.get(AttributeName.ACCOUNT);
        long currentAccountId = account.getId();
        long offerId = Long.parseLong(offerData.get(AttributeName.OFFER_ID));

        try {
            Optional<Employee> findEmployee = employeeService.findEmployeeByAccountId(currentAccountId);
            if(findEmployee.isPresent()) {
                Employee currentEmployee = findEmployee.get();
                long currentEmployeeId = currentEmployee.getId();

                Optional<Offer> findOffer = offerService.findOfferById(offerId);
                if(findOffer.isPresent()) {
                    Offer offer = findOffer.get();
                    long offerCreatorEmployeeId = offer.getEmployeeId();

                    if(currentEmployeeId == offerCreatorEmployeeId) {
                        content.putRequestAttribute(AttributeName.OFFER, offer);
                        page = PagePath.ACCOUNT_OFFER;
                    }
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_OFFERS, e);
            page = PagePath.ACCOUNT_OFFERS;
        }

        return page;
    }
}
