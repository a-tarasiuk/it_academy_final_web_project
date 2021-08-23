package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.entity.impl.Trading;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import by.tarasiuk.ct.model.service.impl.TradingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Show account offer command.
 */
public class ShowAccountOfferCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private final TradingServiceImpl tradingService = ServiceProvider.getTradingService();
    private final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();

    /**
     * There is a search for a company and its freight for the offer.
     *
     * @param content   Request data content.
     * @return          Page path.
     * @see             by.tarasiuk.ct.controller.RequestContent
     */
    @Override
    public String execute(RequestContent content) {
        String page = PagePath.ACCOUNT_OFFERS;
        HashMap<String, String> offerData = content.getRequestParameters();
        HashMap<String, Object> session = content.getSessionAttributes();

        Account account = (Account) session.get(AttributeName.ACCOUNT);
        long currentAccountId = account.getId();
        long offerId = Long.parseLong(offerData.get(AttributeName.OFFER_ID));

        try {
            Optional<Employee> findEmployeeViewer = employeeService.findEmployeeByAccountId(currentAccountId);
            if(findEmployeeViewer.isPresent()) {
                Employee employeeViewer = findEmployeeViewer.get();
                long viewerEmployeeId = employeeViewer.getId();

                Optional<Offer> findOffer = offerService.findOfferById(offerId);
                if(findOffer.isPresent()) {
                    Offer offer = findOffer.get();
                    long creatorEmployeeId = offer.getEmployeeId();

                    if(viewerEmployeeId == creatorEmployeeId) {
                        Map<Trading, String> tradingMap = new HashMap<>();
                        List<Trading> tradings = tradingService.findListTradingsByOfferId(offerId);

                        if(tradings != null && !tradings.isEmpty()) {
                            for (Trading trading : tradings) {
                                long employeeId = trading.getEmployeeId();
                                Optional<Company> findTradingCreatorCompany = employeeService.findCompanyByEmployeeId(employeeId);

                                if(findTradingCreatorCompany.isPresent()) {
                                    Company company = findTradingCreatorCompany.get();
                                    String companyName = company.getName();
                                    tradingMap.put(trading, companyName);
                                }
                            }
                        }

                        content.putRequestAttribute(AttributeName.OFFER, offer);
                        content.putRequestAttribute(AttributeName.TRADING_MAP, tradingMap);

                        page = PagePath.ACCOUNT_OFFER;
                    }
                } else {
                    page = PagePath.INDEX;
                }
            } else {
                page = PagePath.INDEX;
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_OFFERS, e);
            page = PagePath.ACCOUNT_OFFERS;
        }

        return page;
    }
}
