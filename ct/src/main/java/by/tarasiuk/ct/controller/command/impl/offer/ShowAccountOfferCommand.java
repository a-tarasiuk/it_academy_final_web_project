package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.AttributeName;
import by.tarasiuk.ct.manager.PagePath;
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


public class ShowAccountOfferCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private static final TradingServiceImpl tradingService = ServiceProvider.getTradingService();
    private static final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();

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
                        Map<String, Trading> tradingInfo = new HashMap<>();
                        List<Trading> tradings = tradingService.findListTradingsByOfferId(offerId);

                        if(tradings != null && !tradings.isEmpty()) {
                            for (int i = 0; i < tradings.size(); i++) {
                                Trading trading = tradings.get(i);
                                long employeeId = trading.getEmployeeId();

                                Optional<Employee> findEmployeeCreator = employeeService.findEmployeeById(employeeId);
                                if (findEmployeeCreator.isPresent()) {
                                    Employee employeeCreator = findEmployeeCreator.get();
                                    long companyId = employeeCreator.getCompanyId();

                                    Optional<Company> findCompany = companyService.findCompanyById(companyId);
                                    if (findCompany.isPresent()) {
                                        Company company = findCompany.get();
                                        String companyName = company.getName();

                                        tradingInfo.put(companyName, trading);
                                    }
                                }
                            }
                        }
                        content.putRequestAttribute(AttributeName.OFFER, offer);
                        content.putRequestAttribute(AttributeName.TRADING_MAP, tradingInfo);

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
