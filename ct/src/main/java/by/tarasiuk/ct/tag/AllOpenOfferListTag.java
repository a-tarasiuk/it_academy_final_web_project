package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.util.MessageManager;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.util.List;

import static by.tarasiuk.ct.controller.command.AttributeName.LOCALE;
import static by.tarasiuk.ct.util.MessageKey.OFFERS_DO_NOT_EXIST;
import static by.tarasiuk.ct.util.MessageKey.OFFER_ADDRESS;
import static by.tarasiuk.ct.util.MessageKey.OFFER_CREATION_DATE;
import static by.tarasiuk.ct.util.MessageKey.OFFER_FREIGHT;
import static by.tarasiuk.ct.util.MessageKey.OFFER_PRODUCT_NAME;
import static by.tarasiuk.ct.util.MessageKey.OFFER_PRODUCT_VOLUME;
import static by.tarasiuk.ct.util.MessageKey.OFFER_PRODUCT_WEIGHT;

/**
 * Tag for displaying a list of all site offers with the status <code>OPEN</code>.
 */
public class AllOpenOfferListTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;
    private static final String UNICODE_INDEX_NUMBER = "&#x2116;";

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();

        String locale = (String) session.getAttribute(LOCALE);
        List<Offer> openOfferList = (List<Offer>) pageContext.getRequest().getAttribute(AttributeName.OFFER_LIST);
        String titleProductName = MessageManager.findMassage(OFFER_PRODUCT_NAME, locale);
        String titleProductWeight = MessageManager.findMassage(OFFER_PRODUCT_WEIGHT, locale);
        String titleProductVolume = MessageManager.findMassage(OFFER_PRODUCT_VOLUME, locale);
        String titleAddress = MessageManager.findMassage(OFFER_ADDRESS, locale);
        String titleCreationDate = MessageManager.findMassage(OFFER_CREATION_DATE, locale);
        String titleFreight = MessageManager.findMassage(OFFER_FREIGHT, locale);

        try {
            StringBuilder table = new StringBuilder("<form class=\"table\" action=\"controller\" method=\"get\">")
                    .append("<div class=\"table-row\">")
                        .append("<div class=\"table-header\">").append(UNICODE_INDEX_NUMBER).append("</div>")
                        .append("<div class=\"table-header\">").append(titleAddress).append("</div>")
                        .append("<div class=\"table-header\">").append(titleProductWeight).append("</div>")
                        .append("<div class=\"table-header\">").append(titleProductVolume).append("</div>")
                        .append("<div class=\"table-header\">").append(titleProductName).append("</div>")
                        .append("<div class=\"table-header\">").append(titleCreationDate).append("</div>")
                        .append("<div class=\"table-header\">").append(titleFreight).append("</div>")
                    .append("</div>");

            if(openOfferList == null || openOfferList.isEmpty()) {
                String titleOffersDoNotExist = MessageManager.findMassage(OFFERS_DO_NOT_EXIST, locale);
                table.append(titleOffersDoNotExist)
                        .append("</form>");
            } else {
                for(int i = 0; i < openOfferList.size(); i++) {
                    Offer offer = openOfferList.get(i);
                    table.append("<a class=\"table-row\" href=\"/controller?command=show_trading_offer&offer_id=").append(offer.getId()).append("\">")
                            .append("<div class=\"table-data\">").append(i + 1).append("</div>")
                            .append("<div class=\"table-data\">").append(offer.getAddressFrom()).append(" - ").append(offer.getAddressTo()) .append("</div>")
                            .append("<div class=\"table-data\">").append(offer.getProductWeight()).append("</div>")
                            .append("<div class=\"table-data\">").append(offer.getProductVolume()).append("</div>")
                            .append("<div class=\"table-data\">").append(offer.getProductName()).append("</div>")
                            .append("<div class=\"table-data\">").append(offer.getCreationDate()).append("</div>")
                            .append("<div class=\"table-data\">").append(offer.getFreight()).append("</div>")
                            .append("</a>");
                }
            }

            table.append("</form>");

            JspWriter out = pageContext.getOut();
            out.write(table.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
