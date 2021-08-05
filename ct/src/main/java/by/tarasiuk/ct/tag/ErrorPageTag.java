package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.manager.AttributeName;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.ErrorData;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class ErrorPageTag extends TagSupport {
    private static final long serialVersionUID = 2074798491300174808L;
    private static final int STATUS_CODE_404 = 404;

    @Override
    public int doStartTag() throws JspException {
        ErrorData errorData = pageContext.getErrorData();
        HttpSession session = pageContext.getSession();

        int statusCode = errorData.getStatusCode();

        switch (statusCode) {
            case STATUS_CODE_404:
                break;
            default:
                break;
        }

        String servletName = errorData.getServletName();
        String locale = (String) session.getAttribute(AttributeName.LOCALE);

        StringBuilder errorMessage = new StringBuilder("");

        JspWriter out = pageContext.getOut();

        try {
            out.write("sss");
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
