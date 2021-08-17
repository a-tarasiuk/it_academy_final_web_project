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

import java.io.IOException;
import java.util.Map;

import static by.tarasiuk.ct.controller.command.AttributeName.LOCALE;
import static by.tarasiuk.ct.util.MessageKey.OFFER_COMPANY_NAME;
import static by.tarasiuk.ct.util.MessageKey.OFFER_FREIGHT;
import static by.tarasiuk.ct.util.MessageKey.TRADINGS_EMPTY;

public class TradingActionTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;
    private static final String UNICODE_INDEX_NUMBER = "&#x2116;";

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        Map<String, Trading> tradings = (Map<String, Trading>) request.getAttribute(AttributeName.TRADING_MAP);
        Offer offer = (Offer) request.getAttribute(AttributeName.OFFER);

        String locale = (String) session.getAttribute(LOCALE);
        String titleCompany = MessageManager.findMassage(OFFER_COMPANY_NAME, locale);
        String titleFreight = MessageManager.findMassage(OFFER_FREIGHT, locale);

        StringBuilder table = new StringBuilder("<div class=\"table\">")
                .append("<div class=\"table-row\">")
                .append("<div class=\"table-header\">").append(UNICODE_INDEX_NUMBER).append("</div>")
                .append("<div class=\"table-header\">").append(titleCompany).append("</div>")
                .append("<div class=\"table-header\">").append(titleFreight).append("</div>")
                .append("</div>");
        try {
            if(offer != null) {
                if(tradings != null && !tradings.isEmpty()) {
                    int counter = 0;

                    for(Map.Entry<String, Trading> entry: tradings.entrySet()) {
                        String companyName = entry.getKey();
                        Trading trading = entry.getValue();
                        float freight = trading.getFreight();

                        table.append("<a class=\"table-row\" href=\"/controller?command=show_trading_viewer&trading_id=").append(trading.getId())
                                .append("&offer_freight=").append(offer.getFreight()).append("\">")
                                .append("<div class=\"table-data\">").append(++counter).append("</div>")
                                .append("<div class=\"table-data\">").append(companyName).append("</div>")
                                .append("<div class=\"table-data\">").append(freight).append("</div>")
                                .append("</a>");
                    }
                } else {
                    String messageEmpty = MessageManager.findMassage(TRADINGS_EMPTY, locale);
                    table.append("<tr><td colspan=\"3\" align=\"center\">")
                            .append(messageEmpty)
                            .append("</td></tr>");
                }
            }

            table.append("</div>");

            JspWriter out = pageContext.getOut();
            out.write(table.toString());
        } catch (IOException e) {
            throw new JspException(e.getMessage()); /// FIXME: 08.08.2021
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
