package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.controller.command.AttributeName;
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
        JspWriter out = pageContext.getOut();

        String locale = (String) session.getAttribute(AttributeName.LOCALE);
        Account account = (Account) session.getAttribute(AttributeName.ACCOUNT);

        if(account == null) {
            String labelSignIn = MessageManager.findMassage("form.signIn.title", locale);
            String labelSignUp = MessageManager.findMassage("form.signUp.title", locale);

            StringBuilder signInButton = new StringBuilder("<a class=\"menu-button button-colour-standard\"")
                    .append("href=\"/controller?command=go_to_sign_in_page\">")
                    .append("<span class=\"icon icon-sign-in\">&nbsp;</span>")
                    .append(labelSignIn)
                    .append("</a>");

            StringBuilder signUpButton = new StringBuilder("<a class=\"menu-button button-colour-red\"")
                    .append("href=\"/controller?command=go_to_sign_up_page\">")
                    .append("<span class=\"icon icon-white icon-sign-up\"></span>&nbsp;")
                    .append(labelSignUp)
                    .append("</a>");

            try {
                out.write(signInButton.toString());
                out.write(signUpButton.toString());
            } catch (IOException e) {
                throw new JspException(e.getMessage());
            }
        } else {
            String labelFirstName = account.getFirstName();
            String labelSetting = MessageManager.findMassage("button.account.label.settings", locale);
            String labelLogout = MessageManager.findMassage("button.account.label.signOut", locale);

            StringBuilder accountButton = new StringBuilder("<div class=\"dropdown-wrapper\">")
                    .append("<button class=\"dropdown-button button-colour-standard\">")
                    .append("<span class=\"icon icon-user-circle-o\">&nbsp;</span>")
                    .append(labelFirstName)
                    .append("&nbsp;&nbsp;")
                    .append("<span class=\"icon icon-caret-down\"></span>")
                    .append("</button>")
                    .append("<div class=\"content-wrapper\">")
                    .append("<form class=\"dropdown-content\" method=\"get\" action=\"controller\">")
                    .append("<button type=\"submit\" name=\"command\" value=\"show_account_settings\">")
                    .append("<span class=\"icon icon-cog\">&nbsp;</span>")
                    .append(labelSetting)
                    .append("</button>")
                    .append("<button type=\"submit\" name=\"command\" value=\"logout\">")
                    .append("<span class=\"icon icon-sign-out\">&nbsp;</span>")
                    .append(labelLogout)
                    .append("</button>")
                    .append("</form>")
                    .append("</div>")
                    .append("</div>");

            try {
                out.write(accountButton.toString());
            } catch (IOException e) {
                throw new JspException(e.getMessage());
            }
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
