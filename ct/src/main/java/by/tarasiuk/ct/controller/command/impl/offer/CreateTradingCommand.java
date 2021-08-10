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
import by.tarasiuk.ct.model.service.impl.TradingServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.manager.AttributeName.INFORMATION_MESSAGE;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_INCORRECT_TRADING_FREIGHT;
import static by.tarasiuk.ct.manager.AttributeName.MESSAGE_QUERY_ERROR;
import static by.tarasiuk.ct.manager.AttributeName.OFFER_ID;
import static by.tarasiuk.ct.manager.AttributeName.TRADING_FREIGHT;
import static by.tarasiuk.ct.manager.MessageKey.TRADING_SUCCESSFULLY_CREATED;

public class CreateTradingCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TradingServiceImpl tradingService = ServiceProvider.getTradingService();
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();

    @Override
    public String execute(RequestContent content) {
        String page;
        HashMap<String, String> requestParameters = content.getRequestParameters();
        HashMap<String, Object> sessionAttributes = content.getSessionAttributes();

        String tradingFreight = requestParameters.get(TRADING_FREIGHT);

        try {
            if(!tradingService.isValidFreight(tradingFreight)) {
                content.putRequestAttribute(TRADING_FREIGHT, tradingFreight);
                content.putRequestAttribute(MESSAGE_INCORRECT_TRADING_FREIGHT, true);
                page = PagePath.TRADING;
            } else {
                try {
                    Account account = (Account) sessionAttributes.get(ACCOUNT);
                    long offerId = Long.parseLong(requestParameters.get(OFFER_ID));
                    long accountId = account.getId();
                    Optional<Employee> findEmployee = employeeService.findEmployeeByAccountId(accountId);

                    if(findEmployee.isPresent()) {
                        Employee employee = findEmployee.get();
                        long employeeId = employee.getId();
                        long freight = Long.parseLong(tradingFreight);
                        tradingService.createTrading(offerId, employeeId, freight);
                        content.putRequestAttribute(INFORMATION_MESSAGE, TRADING_SUCCESSFULLY_CREATED);
                    }

                    page = PagePath.INFO;
                } catch (ServiceException e) {
                    LOGGER.warn("Can't create trading: '{}'.", requestParameters);
                    content.putRequestAttributes(requestParameters);
                    content.putRequestAttribute(MESSAGE_QUERY_ERROR, true);
                    page = PagePath.TRADING;
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.CREATE_TRADING, e);
            page = PagePath.TRADING;
        }

        return page;
    }
}
