package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.manager.AttributeName.INFORMATION_MESSAGE;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_INCORRECT_OFFER_DATA;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_QUERY_ERROR;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_SUCCESSFULLY_CREATED;

public class CreateOfferCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();

    @Override
    public String execute(RequestContent content) {
        String page;
        Map<String, String> offerData = content.getRequestParameters();
        Map<String, Object> sessionAttributes = content.getSessionAttributes();

        try {
            if(!offerService.isValidOfferData(offerData)) {
                content.putRequestAttributes(offerData);
                content.putRequestAttribute(MESSAGE_INCORRECT_OFFER_DATA, true);
                page = PagePath.CREATE_OFFER;
            } else {
                try {
                    Account account = (Account) sessionAttributes.get(ACCOUNT);
                    long accountId = account.getId();
                    Optional<Employee> findEmployee = employeeService.findEmployeeByAccountId(accountId);
                    if(findEmployee.isPresent()) {
                        Employee employee = findEmployee.get();
                        long employeeId = employee.getId();
                        offerService.createOffer(employeeId, offerData);
                    }
                    content.putRequestAttribute(INFORMATION_MESSAGE, OFFER_SUCCESSFULLY_CREATED);
                    page = PagePath.INFO;
                } catch (ServiceException e) {
                    LOGGER.warn("Can't create offer: '{}'.", offerData);
                    content.putRequestAttributes(offerData);
                    content.putRequestAttribute(MESSAGE_QUERY_ERROR, true);
                    page = PagePath.CREATE_OFFER;
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.CREATE_OFFER, e);
            page = PagePath.CREATE_OFFER;
        }

        return page;
    }
}
