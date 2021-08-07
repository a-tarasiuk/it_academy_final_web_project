package by.tarasiuk.ct.tag;

import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.entity.impl.Offer;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import by.tarasiuk.ct.model.service.impl.EmployeeServiceImpl;
import by.tarasiuk.ct.model.service.impl.OfferServiceImpl;
import by.tarasiuk.ct.util.MessageManager;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static by.tarasiuk.ct.manager.AttributeName.LOCALE;
import static by.tarasiuk.ct.manager.MessageKey.*;

public class AllOfferListTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;
    private static final OfferServiceImpl offerService = ServiceProvider.getOfferService();
    private static final EmployeeServiceImpl employeeService = ServiceProvider.getEmployeeService();
    private static final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();

        String locale = (String) session.getAttribute(LOCALE);
        String titleCompany = MessageManager.findMassage(OFFER_COMPANY_NAME, locale);
        String titleProductName = MessageManager.findMassage(OFFER_PRODUCT_NAME, locale);
        String titleProductWeight = MessageManager.findMassage(OFFER_PRODUCT_WEIGHT, locale);
        String titleProductVolume = MessageManager.findMassage(OFFER_PRODUCT_VOLUME, locale);
        String titleAddress = MessageManager.findMassage(OFFER_ADDRESS, locale);
        String titleCreationDate = MessageManager.findMassage(OFFER_CREATION_DATE, locale);
        String titleFreight = MessageManager.findMassage(OFFER_FREIGHT, locale);

        try {
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

            List<Offer> offers = offerService.findListOffers();

            if(offers == null || offers.isEmpty()) {
                String titleOffersDoNotExist = MessageManager.findMassage(OFFERS_DO_NOT_EXIST, locale);
                table.append("<tr>")
                        .append(titleOffersDoNotExist)
                        .append("</tr>");
            } else {
                for(Offer offer: offers) {
                    long accountId = offer.getAccountId();
                    Optional<Employee> findEmployee = employeeService.findEmployeeByAccountId(accountId);

                    if(findEmployee.isPresent()) {
                        Employee employee = findEmployee.get();
                        long companyId = employee.getCompanyId();
                        Optional<Company> findCompany = companyService.findCompanyById(companyId);

                        if(findCompany.isPresent()) {
                            Company company = findCompany.get();
                            String companyName = company.getName();

                            table.append("<tr>")
                                    .append("<td>").append(companyName).append("</td>")
                                    .append("<td>").append(offer.getAddressFrom()).append(" - ").append(offer.getAddressTo()) .append("</td>")
                                    .append("<td>").append(offer.getProductWeight()).append("</td>")
                                    .append("<td>").append(offer.getProductVolume()).append("</td>")
                                    .append("<td>").append(offer.getProductName()).append("</td>")
                                    .append("<td>").append(offer.getCreationDate()).append("</td>")
                                    .append("<td>").append(offer.getFreight()).append("</td>")
                                    .append("</tr>");
                        }
                    }
                }
            }

            table.append("</table>");

            JspWriter out = pageContext.getOut();
            out.write(table.toString());
        } catch (ServiceException e) {
            e.printStackTrace();    //fixme
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
