package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.util.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.LOCALE;
import static by.tarasiuk.ct.manager.AttributeName.OFFER;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_ADDRESS;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_COMPANY_NAME;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_CREATION_DATE;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_EMPTY;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_FREIGHT;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_NAME;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_VOLUME;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_WEIGHT;

public class OfferViewerTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private static final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();

    @Override
    public int doStartTag() throws JspException {
        String locale = (String) pageContext.getSession().getAttribute(LOCALE);
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        Offer offer = (Offer) request.getAttribute(OFFER);
        String titleCompany = MessageManager.findMassage(OFFER_COMPANY_NAME, locale);
        String titleProductName = MessageManager.findMassage(OFFER_PRODUCT_NAME, locale);
        String titleProductWeight = MessageManager.findMassage(OFFER_PRODUCT_WEIGHT, locale);
        String titleProductVolume = MessageManager.findMassage(OFFER_PRODUCT_VOLUME, locale);
        String titleAddress = MessageManager.findMassage(OFFER_ADDRESS, locale);
        String titleCreationDate = MessageManager.findMassage(OFFER_CREATION_DATE, locale);
        String titleFreight = MessageManager.findMassage(OFFER_FREIGHT, locale);

        StringBuilder table = new StringBuilder("<table>")
                .append("<tr>")
                .append("<th>").append(titleCompany).append("</th>")
                .append("<th>").append(titleAddress).append("</th>")
                .append("<th>").append(titleProductWeight).append("</th>")
                .append("<th>").append(titleProductVolume).append("</th>")
                .append("<th>").append(titleProductName).append("</th>")
                .append("<th>").append(titleCreationDate).append("</th>")
                .append("<th>").append(titleFreight).append("</th>")
                .append("</tr>");
        try {
            if(offer != null) {
                long employeeId = offer.getEmployeeId();

                Optional<Employee> findEmployee = employeeService.findEmployeeById(employeeId);
                if (findEmployee.isPresent()) {
                    Employee employee = findEmployee.get();
                    long companyId = employee.getCompanyId();

                    Optional<Company> findCompany = companyService.findCompanyById(companyId);
                    if (findCompany.isPresent()) {
                        Company company = findCompany.get();
                        String companyName = company.getName();

                        String productName = offer.getProductName();
                        float productWeight = offer.getProductWeight();
                        float productVolume = offer.getProductVolume();
                        String addressFrom = offer.getAddressFrom();
                        String addressTo = offer.getAddressTo();
                        float freight = offer.getFreight();
                        LocalDate creationDate = offer.getCreationDate();

                        table.append("<tr>")
                                .append("<td>").append(companyName).append("</td>")
                                .append("<td>").append(addressFrom).append(" - ").append(addressTo).append("</td>")
                                .append("<td>").append(productWeight).append("</td>")
                                .append("<td>").append(productVolume).append("</td>")
                                .append("<td>").append(productName).append("</td>")
                                .append("<td>").append(creationDate).append("</td>")
                                .append("<td>").append(freight).append("</td>")
                                .append("</tr>");
                    }
                }
            } else {
                String messageEmpty = MessageManager.findMassage(OFFER_EMPTY, locale);
                table.append("<tr>")
                        .append(messageEmpty)
                        .append("</tr>");
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
