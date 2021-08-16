package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.tarasiuk.ct.controller.command.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.controller.command.AttributeName.OFFER_LIST;
import static by.tarasiuk.ct.controller.command.AttributeName.OFFER_STATUS;
import static by.tarasiuk.ct.util.MessageKey.MESSAGE_WARN;

/**
 * Show account list offer command
 */
public class ShowAccountListOfferCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();

    /**
     * Search for a list of offers by employee ID.
     * @param content - RequestContent
     * @return account offers page
     */
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

                List<Offer> offerList = offerService.findOfferListByEmployeeId(employeeId);

                if(!optionalStatus.isPresent()) {
                    content.putRequestAttribute(OFFER_LIST, offerList);
                } else if (offerList != null && !offerList.isEmpty()){
                    String requestStatus = optionalStatus.get();
                    Offer.Status status = Offer.Status.valueOf(requestStatus.toUpperCase());

                    switch (status) {
                        case OPEN:
                        case CLOSED:
                            List<Offer> offersByStatus = offerList.stream()
                                    .filter(offer -> offer.getStatus().name().equalsIgnoreCase(requestStatus))
                                    .collect(Collectors.toList());

                            content.putRequestAttribute(OFFER_LIST, offersByStatus);
                            break;
                        default:
                            content.putRequestAttribute(MESSAGE_WARN, true);
                            throw new EnumConstantNotPresentException(status.getClass(), status.toString()); //fixme Need an exception?
                    }
                }
            }

            page = PagePath.ACCOUNT_OFFERS;
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_OFFERS, e);
            page = PagePath.ACCOUNT_TRADING;
        }

        return page;
    }
}
