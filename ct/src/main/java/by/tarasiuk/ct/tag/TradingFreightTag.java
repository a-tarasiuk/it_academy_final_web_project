package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.util.MessageKey;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

import static by.tarasiuk.ct.controller.command.AttributeName.LOCALE;
import static by.tarasiuk.ct.controller.command.AttributeName.OFFER;

public class TradingFreightTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String locale = (String) session.getAttribute(LOCALE);

        String title = MessageManager.findMassage(MessageKey.FREIGHT_OFFER, locale);
        String placeholder = MessageManager.findMassage(MessageKey.PLACEHOLDER_OFFER_FREIGHT, locale);
        String description = MessageManager.findMassage(MessageKey.DESCRIPTION_TRADING_FREIGHT, locale);
        String button = MessageManager.findMassage(MessageKey.BUTTON_SUBMIT, locale);

        Offer offer = (Offer) request.getAttribute(OFFER);
        long offerId = offer.getId();
        long creatorEmployeeId = offer.getEmployeeId();
        long currentEmployeeId = (Long) request.getAttribute(AttributeName.EMPLOYEE_ID);

        if(creatorEmployeeId != currentEmployeeId) {
            StringBuilder table = new StringBuilder("<input type=\"hidden\" name=\"offer_id\" value=\"").append(offerId).append("\" form=\"trading\">")
                    .append("<div class=\"title\">").append(title).append("</div>")
                    .append("<form id=\"trading\" class=\"trading\" action=\"controller\" method=\"get\">")
                    .append("<div class=\"trading-section\">&#x24;</div>")
                    .append("<div class=\"trading-section\"><input type=\"text\" id=\"freight\" name=\"trading_freight\"").append("\" placeholder=\"").append(placeholder).append("\" oninput=\"validateFreight()\" autofocus required></div>")
                    .append("<div class=\"trading-section\"><button class=\"btn-simple btn-green\" id=\"confirm\" name=\"command\" value=\"create_trading\" type=\"submit\">").append(button).append("</button></div>")
                    .append("</form>")
                    .append("<label id=\"description_freight\" class=\"description\">").append(description).append("</label>");
            try {
                JspWriter out = pageContext.getOut();
                out.write(table.toString());
            } catch (IOException e) {
                throw new JspException(e.getMessage()); /// FIXME: 08.08.2021
            }
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
