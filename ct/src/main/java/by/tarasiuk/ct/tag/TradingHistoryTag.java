package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.manager.AttributeName;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.entity.impl.Trading;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.TradingServiceImpl;
import by.tarasiuk.ct.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.LOCALE;
import static by.tarasiuk.ct.manager.MessageKey.*;

public class TradingHistoryTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;
    private static final String UNICODE_INDEX_NUMBER = "&#x2116;";
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private static final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();
    private static final TradingServiceImpl tradingService = ServiceProvider.getTradingService();

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        String locale = (String) session.getAttribute(LOCALE);
        String titleHeader = MessageManager.findMassage(TRADING_HISTORY, locale);
        String titleCompany = MessageManager.findMassage(OFFER_COMPANY_NAME, locale);
        String titleFreight = MessageManager.findMassage(OFFER_FREIGHT, locale);

        StringBuilder table = new StringBuilder("<div class=\"title\">").append(titleHeader).append("</div>")
                .append("<table>")
                .append("<tr>")
                .append("<th>").append(UNICODE_INDEX_NUMBER).append("</th>")
                .append("<th>").append(titleCompany).append("</th>")
                .append("<th>").append(titleFreight).append("</th>")
                .append("</tr>");
        try {
            Offer offer = (Offer) request.getAttribute(AttributeName.OFFER);

            if(offer != null) {
                long offerId = offer.getId();
                List<Trading> tradings = tradingService.findListTradingsByOfferId(offerId);

                if(tradings != null && !tradings.isEmpty()) {
                    for (int i = 0; i < tradings.size(); i++) {
                        Trading trading = tradings.get(i);
                        long employeeId = trading.getEmployeeId();

                        Optional<Employee> findEmployee = employeeService.findEmployeeById(employeeId);
                        if (findEmployee.isPresent()) {
                            Employee employee = findEmployee.get();
                            long companyId = employee.getCompanyId();

                            Optional<Company> findCompany = companyService.findCompanyById(companyId);
                            if (findCompany.isPresent()) {
                                Company company = findCompany.get();
                                String companyName = company.getName();

                                float freight = trading.getFreight();

                                table.append("<tr>")
                                        .append("<td>").append(i + 1).append("</td>")
                                        .append("<td>").append(companyName).append("</td>")
                                        .append("<td>").append(freight).append("</td>")
                                        .append("</tr>");
                            }
                        }
                    }
                } else {
                    String messageEmpty = MessageManager.findMassage(TRADINGS_EMPTY, locale);
                    table.append("<tr><td colspan=\"3\" align=\"center\">")
                            .append(messageEmpty)
                            .append("</td></tr>");
                }
            }

            table.append("</table>");

            JspWriter out = pageContext.getOut();
            out.write(table.toString());
        } catch (IOException e) {
            throw new JspException(e.getMessage()); /// FIXME: 08.08.2021
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
