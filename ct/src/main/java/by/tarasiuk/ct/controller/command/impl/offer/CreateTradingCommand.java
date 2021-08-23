package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import by.tarasiuk.ct.model.service.impl.TradingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.controller.command.AttributeName.MESSAGE_CAN_NOT_TRADING_ON_OFFER_YOUR_COMPANY;
import static by.tarasiuk.ct.controller.command.AttributeName.MESSAGE_EMPLOYEE_ALREADY_CREATED_TRADING;
import static by.tarasiuk.ct.controller.command.AttributeName.MESSAGE_INCORRECT_TRADING_FREIGHT;
import static by.tarasiuk.ct.controller.command.AttributeName.MESSAGE_QUERY_ERROR;
import static by.tarasiuk.ct.controller.command.AttributeName.OFFER;
import static by.tarasiuk.ct.controller.command.AttributeName.OFFER_ID;
import static by.tarasiuk.ct.controller.command.AttributeName.TRADING_FREIGHT;

/**
 * Create a new trading command.
 */
public class CreateTradingCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private final TradingServiceImpl tradingService = ServiceProvider.getTradingService();
    private final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();

    /**
     * The validity of the trading data is checked.
     * If successful, create an trading in the database.
     * Otherwise, it displays the corresponding message and returns to the interrupting page.
     *
     * @param content   Request data content.
     * @return          Page path.
     * @see             by.tarasiuk.ct.controller.RequestContent
     */
    @Override
    public String execute(RequestContent content) {
        String page = PagePath.TRADING_CREATE_INFO;
        Map<String, String> requestParameters = content.getRequestParameters();
        Map<String, Object> sessionAttributes = content.getSessionAttributes();

        String tradingFreight = requestParameters.get(TRADING_FREIGHT);

        try {
            if(!tradingService.isValidFreight(tradingFreight)) {
                content.putRequestAttribute(TRADING_FREIGHT, tradingFreight);
                content.putRequestAttribute(OFFER, tradingFreight);
                content.putRequestAttribute(MESSAGE_INCORRECT_TRADING_FREIGHT, true);
                page = PagePath.ACCOUNT_TRADING;
            } else {
                long offerId = Long.parseLong(requestParameters.get(OFFER_ID));
                Optional<Offer> findOffer = offerService.findOfferById(offerId);
                if(findOffer.isPresent()) {
                    Offer offer = findOffer.get();
                    long creatorOfferEmployeeId = offer.getEmployeeId();

                    Optional<Company> findCreatorOfferCompany = employeeService.findCompanyByEmployeeId(creatorOfferEmployeeId);
                    if(findCreatorOfferCompany.isPresent()) {
                        Company creatorOfferCompany = findCreatorOfferCompany.get();
                        String creatorOfferCompanyName = creatorOfferCompany.getName();

                        Employee currentEmployee = (Employee) sessionAttributes.get(AttributeName.EMPLOYEE);
                        long currentEmployeeId = currentEmployee.getId();

                        Optional<Company> findCurrentEmployeeCompany = employeeService.findCompanyByEmployeeId(currentEmployeeId);
                        if(findCurrentEmployeeCompany.isPresent()) {
                            Company currentEmployeeCompany = findCurrentEmployeeCompany.get();
                            String currentEmployeeCompanyName = currentEmployeeCompany.getName();

                            if (currentEmployeeCompanyName.equals(creatorOfferCompanyName)) {
                                content.putRequestAttribute(OFFER, offer);
                                content.putRequestAttribute(MESSAGE_CAN_NOT_TRADING_ON_OFFER_YOUR_COMPANY, true);
                                page = PagePath.ACCOUNT_TRADING;
                            } else {
                                boolean isFoundTrading = tradingService.isFoundTradingByOfferIdAndEmployeeId(offerId, currentEmployeeId);

                                if(isFoundTrading) {
                                    content.putRequestAttribute(MESSAGE_EMPLOYEE_ALREADY_CREATED_TRADING, true);
                                    content.putRequestAttribute(OFFER, offer);
                                    page = PagePath.ACCOUNT_TRADING;
                                } else {
                                    long freight = Long.parseLong(tradingFreight);
                                    tradingService.createTrading(offerId, currentEmployeeId, freight);
                                    content.putRequestAttribute(AttributeName.OFFER_ID, offerId);
                                    page = PagePath.TRADING_CREATE_INFO;
                                }
                            }
                        }
                    }
                }
            }
        } catch (ServiceException e) {
            content.putRequestAttribute(MESSAGE_QUERY_ERROR, true);
            page = PagePath.ACCOUNT_TRADING;

            LOGGER.error("Failed to process the command '{}'.", CommandType.CREATE_TRADING, e);
        }

        return page;
    }
}
