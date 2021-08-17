package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.entity.impl.Trading;
import by.tarasiuk.ct.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static by.tarasiuk.ct.controller.command.AttributeName.LOCALE;
import static by.tarasiuk.ct.util.MessageKey.OFFER_ADDRESS;
import static by.tarasiuk.ct.util.MessageKey.OFFER_COMPANY_NAME;
import static by.tarasiuk.ct.util.MessageKey.OFFER_CREATION_DATE;
import static by.tarasiuk.ct.util.MessageKey.OFFER_FREIGHT;
import static by.tarasiuk.ct.util.MessageKey.OFFER_MY_FREIGHT;
import static by.tarasiuk.ct.util.MessageKey.OFFER_PRODUCT_NAME;
import static by.tarasiuk.ct.util.MessageKey.OFFER_PRODUCT_VOLUME;
import static by.tarasiuk.ct.util.MessageKey.OFFER_PRODUCT_WEIGHT;
import static by.tarasiuk.ct.util.MessageKey.TRADINGS_DO_NOT_EXIST;
import static by.tarasiuk.ct.util.MessageKey.TRADINGS_STATUS;

/**
 * Tag for displaying a list of tradings.
 */
public class AccountTradingListTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String UNICODE_INDEX_NUMBER = "&#x2116;";

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        String locale = (String) session.getAttribute(LOCALE);
        Map<Offer, Trading> tradingList = (Map<Offer, Trading>) request.getAttribute(AttributeName.TRADING_MAP);

        String titleProductName = MessageManager.findMassage(OFFER_PRODUCT_NAME, locale);
        String titleCompanyName = MessageManager.findMassage(OFFER_COMPANY_NAME, locale);
        String titleProductWeight = MessageManager.findMassage(OFFER_PRODUCT_WEIGHT, locale);
        String titleProductVolume = MessageManager.findMassage(OFFER_PRODUCT_VOLUME, locale);
        String titleAddress = MessageManager.findMassage(OFFER_ADDRESS, locale);
        String titleCreationDate = MessageManager.findMassage(OFFER_CREATION_DATE, locale);
        String titleFreight = MessageManager.findMassage(OFFER_FREIGHT, locale);
        String titleMyFreight = MessageManager.findMassage(OFFER_MY_FREIGHT, locale);
        String titleTradingStatus = MessageManager.findMassage(TRADINGS_STATUS, locale);

        try {
            StringBuilder table = new StringBuilder("<table>")
                    .append("<tr>")
                    .append("<th>").append(UNICODE_INDEX_NUMBER).append("</th>")
                    .append("<th>").append(titleCompanyName).append("</th>")
                    .append("<th>").append(titleAddress).append("</th>")
                    .append("<th>").append(titleProductWeight).append("</th>")
                    .append("<th>").append(titleProductVolume).append("</th>")
                    .append("<th>").append(titleProductName).append("</th>")
                    .append("<th>").append(titleCreationDate).append("</th>")
                    .append("<th>").append(titleFreight).append("\n&#36;").append("</th>")
                    .append("<th>").append(titleMyFreight).append("\n&#36;").append("</th>")
                    .append("<th>").append(titleTradingStatus).append("</th>")
                    .append("</tr>");

            if(tradingList == null || tradingList.isEmpty()) {
                String titleTradingsDoNotExist = MessageManager.findMassage(TRADINGS_DO_NOT_EXIST, locale);

                table.append("<tr><td colspan=\"10\">")
                        .append(titleTradingsDoNotExist)
                        .append("</td></tr>");
            } else {
                Set<Map.Entry<Offer, Trading>> set = tradingList.entrySet();

                int counter = 0;
                for (Map.Entry<Offer, Trading> current : set) {
                    Offer offer = current.getKey();
                    Trading trading = current.getValue();
                    float tradingFreight = trading.getFreight();
                    Trading.Status tradingStatus = trading.getStatus();

                    table.append("<tr>")
                            .append("<td>").append(++counter).append("</td>")
                            .append("<td>").append("вставить сюда компанию").append("</td>")
                            .append("<td>").append(offer.getAddressFrom()).append(" - ").append(offer.getAddressTo()) .append("</td>")
                            .append("<td>").append(offer.getProductWeight()).append("</td>")
                            .append("<td>").append(offer.getProductVolume()).append("</td>")
                            .append("<td>").append(offer.getProductName()).append("</td>")
                            .append("<td>").append(offer.getCreationDate()).append("</td>")
                            .append("<td>").append(offer.getFreight()).append("</td>")
                            .append("<td class=\"block-colour-grey\"><b>").append(tradingFreight).append("</b></td>");

                    switch (tradingStatus) {
                        case ACCEPTED:
                            table.append("<td class=\"bc-green\"><b>");
                            break;
                        case NOT_ACCEPTED:
                            table.append("<td class=\"bc-red\"><b>");
                            break;
                        default:
                            LOGGER.warn("Nonexistent constant '{}' in '{}'.", tradingStatus, tradingStatus.getDeclaringClass());
                            throw new EnumConstantNotPresentException(tradingStatus.getClass(), tradingStatus.toString());
                    }

                    table.append(tradingStatus).append("</b></td>")
                            .append("</tr>");
                }
            }

            table.append("</table>");

            JspWriter out = pageContext.getOut();
            out.write(table.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
