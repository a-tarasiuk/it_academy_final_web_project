package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.manager.AttributeName;
import by.tarasiuk.ct.util.MessageManager;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class LocaleButtonTag extends TagSupport {
    private static final long serialVersionUID = 2074798491300174808L;

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String locale = (String) session.getAttribute(AttributeName.LOCALE);

        String currentLocale = MessageManager.findMassage("header.label.locale", locale);
        String ruRuLocale = MessageManager.findMassage("header.option.label.ruRU", locale);
        String enUsLocale = MessageManager.findMassage("header.option.label.enUS", locale);

        StringBuilder button = new StringBuilder("<div class=\"dropdown-wrapper\">")
                .append("<button class=\"dropdown-button button-colour-standard\">")
                .append("<span class=\"icon icon-globe\">&nbsp;</span>")
                .append(currentLocale)
                .append("&nbsp;&nbsp;")
                .append("<span class=\"icon icon-caret-down\"></span>")
                .append("</button>")
                .append("<div class=\"content-wrapper\">")
                .append("<form class=\"dropdown-content\" method=\"post\" action=\"controller\">")
                .append("<input type=\"hidden\" name=\"command\" value=\"change_locale_page\">")
                .append("<button type=\"submit\" name=\"locale\" value=\"ru_RU\">")
                .append(ruRuLocale)
                .append("</button>")
                .append("<button type=\"submit\" name=\"locale\" value=\"en_US\">")
                .append(enUsLocale)
                .append("</button>")
                .append("</form>")
                .append("</div>")
                .append("</div>");

        JspWriter out = pageContext.getOut();

        try {
            out.write(button.toString());
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
