package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.manager.AttributeName;
import by.tarasiuk.ct.model.entity.impl.Offer;
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

import static by.tarasiuk.ct.manager.AttributeName.LOCALE;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_ADDRESS;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_COMPANY_NAME;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_CREATION_DATE;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_FREIGHT;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_MY_FREIGHT;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_NAME;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_VOLUME;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_WEIGHT;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_STATUS;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_TON;
import static by.tarasiuk.ct.manager.MessageKey.TRADINGS_DO_NOT_EXIST;

public class AccountTradingListTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String UNICODE_INDEX_NUMBER = "&#x2116;";

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        String locale = (String) session.getAttribute(LOCALE);
        Map<Offer, Float> tradingList = (Map<Offer, Float>) request.getAttribute(AttributeName.TRADING_MAP);

        String titleProductName = MessageManager.findMassage(OFFER_PRODUCT_NAME, locale);
        String titleCompanyName = MessageManager.findMassage(OFFER_COMPANY_NAME, locale);
        String titleProductWeight = MessageManager.findMassage(OFFER_PRODUCT_WEIGHT, locale);
        String titleProductVolume = MessageManager.findMassage(OFFER_PRODUCT_VOLUME, locale);
        String titleAddress = MessageManager.findMassage(OFFER_ADDRESS, locale);
        String titleCreationDate = MessageManager.findMassage(OFFER_CREATION_DATE, locale);
        String titleFreight = MessageManager.findMassage(OFFER_FREIGHT, locale);
        String titleMyFreight = MessageManager.findMassage(OFFER_MY_FREIGHT, locale);
        String titleStatus = MessageManager.findMassage(OFFER_STATUS, locale);
        String titleTon = MessageManager.findMassage(OFFER_TON, locale);

        try {
            StringBuilder table = new StringBuilder("<table>")
                    .append("<tr>")
                    .append("<th>").append(UNICODE_INDEX_NUMBER).append("</th>")
                    .append("<th>").append(titleCompanyName).append("</th>")
                    .append("<th>").append(titleAddress).append("</th>")
                    .append("<th>").append(titleProductWeight).append("&nbsp(").append(titleTon).append(")").append("</th>")
                    .append("<th>").append(titleProductVolume).append("</th>")
                    .append("<th>").append(titleProductName).append("</th>")
                    .append("<th>").append(titleCreationDate).append("</th>")
                    .append("<th>").append(titleFreight).append("\n&#36;").append("</th>")
                    .append("<th>").append(titleMyFreight).append("\n&#36;").append("</th>")
                    .append("<th>").append(titleStatus).append("</th>")
                    .append("</tr>");

            if(tradingList == null || tradingList.isEmpty()) {
                String titleTradingsDoNotExist = MessageManager.findMassage(TRADINGS_DO_NOT_EXIST, locale);

                table.append("<tr><td colspan=\"10\">")
                        .append(titleTradingsDoNotExist)
                        .append("</td></tr>");
            } else {
                Set<Map.Entry<Offer, Float>> set = tradingList.entrySet();

                int counter = 0;
                for (Map.Entry<Offer, Float> current : set) {
                    Offer offer = current.getKey();
                    float freight = current.getValue();

                    table.append("<tr>")
                            .append("<td>").append(++counter).append("</td>")
                            .append("<td>").append("вставить сюда компанию").append("</td>")
                            .append("<td>").append(offer.getAddressFrom()).append(" - ").append(offer.getAddressTo()) .append("</td>")
                            .append("<td>").append(offer.getProductWeight()).append("</td>")
                            .append("<td>").append(offer.getProductVolume()).append("</td>")
                            .append("<td>").append(offer.getProductName()).append("</td>")
                            .append("<td>").append(offer.getCreationDate()).append("</td>")
                            .append("<td>").append(offer.getFreight()).append("</td>")
                            .append("<td class=\"block-colour-grey\"><b>").append(freight).append("</b></td>");

                    Offer.Status offerStatus = offer.getStatus();

                    switch (offerStatus) {
                        case OPEN:
                            table.append("<td class=\"bc-green\">");
                            break;
                        case CLOSED:
                            table.append("<td class=\"bc-red\">");
                            break;
                        default:
                            LOGGER.warn("Nonexistent constant '{}' in '{}'.", offerStatus, offerStatus.getDeclaringClass());
                            throw new EnumConstantNotPresentException(offerStatus.getClass(), offerStatus.toString()); //fixme Need an exception?
                    }

                    table.append(offer.getStatus()).append("</td>")
                            .append("</tr>");

                }
            }

            table.append("</table>");

            JspWriter out = pageContext.getOut();
            out.write(table.toString());
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
