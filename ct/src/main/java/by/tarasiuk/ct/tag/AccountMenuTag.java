package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.manager.MessageKey;
import by.tarasiuk.ct.util.MessageManager;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

import static by.tarasiuk.ct.manager.AttributeName.LOCALE;

public class AccountMenuTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;

    @Override
    public int doStartTag() throws JspException {
        String locale = (String) pageContext.getSession().getAttribute(LOCALE);
        String titleOfferAll = MessageManager.findMassage(MessageKey.OFFER_ALL, locale);
        String titleOfferMy = MessageManager.findMassage(MessageKey.OFFER_MY, locale);
        String titleEmployeesMy = MessageManager.findMassage(MessageKey.EMPLOYEES_MY, locale);
        String titleTradingsHistory = MessageManager.findMassage(MessageKey.MY_TRADINGS, locale);

        StringBuilder menu = new StringBuilder("<a class=\"ml-item\" href=\"/controller?command=show_open_offers\">")
                .append("<div class=\"icon-block\">").append("<span class=\"icon icon-car\"></span>").append("</div>")
                .append(titleOfferAll)
                .append("</a>")
                .append("<a class=\"ml-item\" href=\"/controller?command=show_account_offers\">")
                .append("<div class=\"icon-block\">").append("<span class=\"icon icon-archive\"></span>").append("</div>")
                .append(titleOfferMy)
                .append("</a>")
                .append("<a class=\"ml-item\" href=\"/controller?command=show_account_tradings\">")
                .append("<div class=\"icon-block\">").append("<span class=\"icon icon-history\"></span>").append("</div>")
                .append(titleTradingsHistory)
                .append("</a>")
                .append("<a class=\"ml-item\" href=\"/controller?command=go_to_account_employees_page\">")
                .append("<div class=\"icon-block\">").append("<span class=\"icon icon-users\"></span>").append("</div>")
                .append(titleEmployeesMy)
                .append("</a>");

        JspWriter out = pageContext.getOut();

        try {
            out.write(menu.toString());
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