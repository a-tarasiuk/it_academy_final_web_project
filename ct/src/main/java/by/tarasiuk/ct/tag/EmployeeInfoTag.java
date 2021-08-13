package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.model.entity.impl.Account;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.AccountServiceImpl;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.util.MessageManager;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.util.Optional;

import static by.tarasiuk.ct.controller.command.AttributeName.LOCALE;
import static by.tarasiuk.ct.util.MessageKey.HEADER_ACCOUNT_INFO;
import static by.tarasiuk.ct.util.MessageKey.HEADER_MENU;

public class EmployeeInfoTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;
    private static final AccountServiceImpl accountService = ServiceProvider.getAccountService();
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private static final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        Account sessionAccount = (Account) session.getAttribute(AttributeName.ACCOUNT);
        String locale = (String) session.getAttribute(LOCALE);

        String firstName = sessionAccount.getFirstName();
        String email = sessionAccount.getEmail();
        String role = sessionAccount.getRole().toString();
        String titleAccountInfo = MessageManager.findMassage(HEADER_ACCOUNT_INFO, locale);
        String titleMenu = MessageManager.findMassage(HEADER_MENU, locale);

        try {
            Optional<Account> findAccount = accountService.findAccountByEmail(email);
            if(findAccount.isPresent()) {
                Account account = findAccount.get();
                long accountId = account.getId();

                Optional<Employee> findEmployee = employeeService.findEmployeeByAccountId(accountId);
                if(findEmployee.isPresent()) {
                    Employee employee = findEmployee.get();
                    long companyId = employee.getCompanyId();

                    Optional<Company> findCompany = companyService.findCompanyById(companyId);
                    if(findCompany.isPresent()) {
                        Company company = findCompany.get();
                        String companyName = company.getName();

                        StringBuilder employeeInfo = new StringBuilder("<div class=\"ml-header\">").append(titleAccountInfo).append("</div>")
                                .append("<div id=\"ml-company-info\">")
                                .append("<div class=\"ml-ci-row\">")
                                .append("<span class=\"icon icon-user\">&nbsp;</span>")
                                .append("<span><b>").append(firstName).append("</b></span>")
                                .append("</div>")
                                .append("<div class=\"ml-ci-row\">")
                                .append("<span class=\"icon icon-building\">&nbsp;</span>")
                                .append("<span><b>").append(companyName).append("</b></span>")
                                .append("</div>")
                                .append("<div class=\"ml-ci-row\">")
                                .append("<span class=\"icon icon-briefcase\">&nbsp;</span>")
                                .append("<span><b>").append(role).append("</b></span>")
                                .append("</div>").append("</div>")
                                .append("<div class=\"ml-header\">").append(titleMenu).append("</div>");

                        JspWriter out = pageContext.getOut();
                        out.write(employeeInfo.toString());
                    }
                }
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        } catch (ServiceException e) {
            e.printStackTrace();    //fixme
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
