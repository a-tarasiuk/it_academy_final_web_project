package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

import static by.tarasiuk.ct.manager.AttributeName.*;
import static by.tarasiuk.ct.manager.MessageKey.*;

public class InformationMessageTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        String locale = (String) session.getAttribute(LOCALE);
        String keyMessage = (String) request.getAttribute(INFORMATION_MESSAGE);
        String valueMessage = MessageManager.findMassage(keyMessage, locale);

        StringBuilder block = new StringBuilder("<div id=\"info-header\">");

        switch (keyMessage) {
            case CONFIRM_MESSAGE:
                block.append("<span class=\"icon icon-info-circle x2 icon-green\"></span>");
                valueMessage = String.format(valueMessage, request.getParameter(ACCOUNT_FIRST_NAME), request.getParameter(ACCOUNT_EMAIL));
                break;
            case OFFER_SUCCESSFULLY_CREATED:
                block.append("<span class=\"icon icon-check-circle x2 icon-green\"></span>");
                break;
            case TOKEN_NOT_EXIST:
            case TOKEN_INCORRECT:
                block.append("<span class=\"icon icon-exclamation-circle x2 icon-red\"></span>");
                valueMessage = String.format(valueMessage, request.getParameter(TOKEN_NUMBER), request.getParameter(ACCOUNT_EMAIL));
                break;
            case ACCOUNT_ALREADY_ACTIVATED:
            case ACCOUNT_EMAIL_NOT_EXIST:
                block.append("<span class=\"icon icon-exclamation-circle x2 icon-red\"></span>");
                valueMessage = String.format(valueMessage, request.getParameter(ACCOUNT_EMAIL));
                break;
            case ACCOUNT_SUCCESSFULLY_ACTIVATED:
                block.append("<span class=\"icon icon-check-circle x2 icon-green\"></span>");
                valueMessage = String.format(valueMessage, request.getParameter(ACCOUNT_EMAIL));
            default:
                break;  //todo
        }

        block.append("</div>")
                .append("<div id=\"info-message\">")
                .append(valueMessage)
                .append("</div>");

        JspWriter out = pageContext.getOut();

        try {
            out.write(block.toString());
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
