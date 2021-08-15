package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

import static by.tarasiuk.ct.controller.command.AttributeName.LOCALE;

public class AccountEmployeeListTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;
    private static final String UNICODE_INDEX_NUMBER = "&#x2116;";
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private static final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();

        String locale = (String) session.getAttribute(LOCALE);

        /*

        try {


            JspWriter out = pageContext.getOut();
            out.write(table.toString());
        } catch (ServiceException e) {
            e.printStackTrace();    //fixme
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

         */

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
