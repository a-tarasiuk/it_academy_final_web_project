package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.manager.AttributeName;
import by.tarasiuk.ct.util.MessageManager;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class HeaderButtonTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String locale = (String) session.getAttribute(AttributeName.LOCALE);

        String titleSignIn = MessageManager.findMassage("form.signIn.title", locale);
        String titleSignUp = MessageManager.findMassage("form.signUp.title", locale);

        StringBuilder signInButton = new StringBuilder("<a class=\"menu-button button-colour-standard\"")
                .append("href=\"/controller?command=go_to_sign_in_page\">")
                .append("<span class=\"icon icon-sign-in\">&nbsp;</span>")
                .append(titleSignIn)
                .append("</a>");

        StringBuilder signUpButton = new StringBuilder("<a class=\"menu-button button-colour-red\"")
                .append("href=\"/controller?command=go_to_sign_up_page\">")
                .append("<span class=\"icon icon-white icon-sign-up\"></span>&nbsp;")
                .append(titleSignUp)
                .append("</a>");

        JspWriter out = pageContext.getOut();

        try {
            out.write(signInButton.toString());
            out.write(signUpButton.toString());
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