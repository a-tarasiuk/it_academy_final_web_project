package by.tarasiuk.ct.controller.command.impl.offer;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.CommandType;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static by.tarasiuk.ct.manager.AttributeName.ACCOUNT;
import static by.tarasiuk.ct.manager.AttributeName.OFFER_LIST;
import static by.tarasiuk.ct.manager.AttributeName.OFFER_STATUS;
import static by.tarasiuk.ct.manager.MessageKey.MESSAGE_WARN;


public class ShowAccountOffersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final OfferServiceImpl offerService = ServiceProvider.getOfferService();

    @Override
    public String execute(RequestContent content) {
        String page;
        HashMap<String, String> offerData = content.getRequestParameters();
        HashMap<String, Object> session = content.getSessionAttributes();

        Account account = (Account) session.get(ACCOUNT);
        long accountId = account.getId();
        String requestStatus = offerData.get(OFFER_STATUS);

        try {
            List<Offer> offerList = offerService.findListOffersByAccountId(accountId);

            if(requestStatus == null) {
                content.putRequestAttribute(OFFER_LIST, offerList);
            } else if (offerList != null && !offerList.isEmpty()){
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

            page = PagePath.ACCOUNT_OFFERS;
        } catch (ServiceException e) {
            LOGGER.error("Failed to process the command '{}'.", CommandType.SHOW_ACCOUNT_OFFERS, e);
            page = PagePath.TRADING;
        }

        return page;
    }
}
