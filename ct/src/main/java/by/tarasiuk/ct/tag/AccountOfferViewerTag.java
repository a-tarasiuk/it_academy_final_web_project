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

import static by.tarasiuk.ct.manager.AttributeName.LOCALE;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_ADDRESS_FROM;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_ADDRESS_TO;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_CREATION_DATE;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_FREIGHT;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_NAME;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_VOLUME;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_WEIGHT;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_STATUS;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_TON;

public class AccountOfferViewerTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        String locale = (String) session.getAttribute(LOCALE);
        Offer offer = (Offer) request.getAttribute(AttributeName.OFFER);

        String titleProductName = MessageManager.findMassage(OFFER_PRODUCT_NAME, locale);
        String titleProductWeight = MessageManager.findMassage(OFFER_PRODUCT_WEIGHT, locale);
        String titleProductVolume = MessageManager.findMassage(OFFER_PRODUCT_VOLUME, locale);
        String titleAddressFrom = MessageManager.findMassage(OFFER_ADDRESS_FROM, locale);
        String titleAddressTo = MessageManager.findMassage(OFFER_ADDRESS_TO, locale);
        String titleCreationDate = MessageManager.findMassage(OFFER_CREATION_DATE, locale);
        String titleFreight = MessageManager.findMassage(OFFER_FREIGHT, locale);
        String titleStatus = MessageManager.findMassage(OFFER_STATUS, locale);
        String titleTon = MessageManager.findMassage(OFFER_TON, locale);

        try {
            StringBuilder table = new StringBuilder("<table>")
                    .append("<input type=\"hidden\" name=\"command\" value=\"show_account_offer\">")
                    .append("<tr>")
                    .append("<th>").append(titleAddressFrom).append("</th>")
                    .append("<th>").append(titleAddressTo).append("</th>")
                    .append("<th>").append(titleProductWeight).append("&nbsp(").append(titleTon).append(")").append("</th>")
                    .append("<th>").append(titleProductVolume).append("</th>")
                    .append("<th>").append(titleProductName).append("</th>")
                    .append("<th>").append(titleCreationDate).append("</th>")
                    .append("<th>").append(titleFreight).append("\n&#36;").append("</th>")
                    .append("<th>").append(titleStatus).append("</th>")
                    .append("</tr>")
                    .append("<form class=\"tr\" method=\"post\">")
                    .append("<td><input name=\"offer_address_from\" value=\"").append(offer.getAddressFrom()).append("\"></td>")
                    .append("<td><input name=\"offer_address_to\" value=\"").append(offer.getAddressTo()) .append("\"></td>")
                    .append("<td><input name=\"offer_product_weight\" value=\"").append(offer.getProductWeight()).append("\"></td>")
                    .append("<td><input name=\"offer_product_volume\" value=\"").append(offer.getProductVolume()).append("\"></td>")
                    .append("<td><input name=\"offer_product_name\" value=\"").append(offer.getProductName()).append("\"></td>")
                    .append("<td class=\"disabled\">").append(offer.getCreationDate()).append("</td>")
                    .append("<td><input name=\"offer_freight\" value=\"").append(offer.getFreight()).append("\"></td>");

            Offer.Status offerStatus = offer.getStatus();

            switch (offerStatus) {
                case OPEN:
                    table.append("<td class=\"disabled bc-green\">");
                    break;
                case CLOSED:
                    table.append("<td class=\"disabled bc-red\">");
                    break;
                default:
                    LOGGER.warn("Nonexistent constant '{}' in '{}'.", offerStatus, offerStatus.getDeclaringClass());
                    throw new EnumConstantNotPresentException(offerStatus.getClass(), offerStatus.toString()); //fixme Need an exception?
            }

            table.append(offer.getStatus()).append("</td>")
                    .append("</form>");

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
