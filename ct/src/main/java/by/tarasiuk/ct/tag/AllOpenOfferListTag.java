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
import static by.tarasiuk.ct.manager.MessageKey.OFFERS_DO_NOT_EXIST;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_ADDRESS;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_COMPANY_NAME;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_CREATION_DATE;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_FREIGHT;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_NAME;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_VOLUME;
import static by.tarasiuk.ct.manager.MessageKey.OFFER_PRODUCT_WEIGHT;

public class AllOpenOfferListTag extends TagSupport {
    private static final long serialVersionUID = -5150821270017826128L;
    private static final String UNICODE_INDEX_NUMBER = "&#x2116;";
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
            StringBuilder table = new StringBuilder("<form class=\"table\" action=\"controller\" method=\"get\">")
                    .append("<div class=\"table-row\">")
                        .append("<div class=\"table-header\">").append(UNICODE_INDEX_NUMBER).append("</div>")
                        .append("<div class=\"table-header\">").append(titleCompany).append("</div>")
                        .append("<div class=\"table-header\">").append(titleAddress).append("</div>")
                        .append("<div class=\"table-header\">").append(titleProductWeight).append("</div>")
                        .append("<div class=\"table-header\">").append(titleProductVolume).append("</div>")
                        .append("<div class=\"table-header\">").append(titleProductName).append("</div>")
                        .append("<div class=\"table-header\">").append(titleCreationDate).append("</div>")
                        .append("<div class=\"table-header\">").append(titleFreight).append("</div>")
                    .append("</div>");

            List<Offer> offers = offerService.findAllOfferList();

            if(offers == null || offers.isEmpty()) {
                String titleOffersDoNotExist = MessageManager.findMassage(OFFERS_DO_NOT_EXIST, locale);
                table.append("<div class=\"table-row\">")
                        .append(titleOffersDoNotExist)
                        .append("</div>");
            } else {
                for(int i = 0; i < offers.size(); i++) {
                    Offer offer = offers.get(i);
                    long employeeId = offer.getEmployeeId();
                    Optional<Employee> findEmployee = employeeService.findEmployeeById(employeeId);

                    if(findEmployee.isPresent()) {
                        Employee employee = findEmployee.get();
                        long companyId = employee.getCompanyId();
                        Optional<Company> findCompany = companyService.findCompanyById(companyId);

                        if(findCompany.isPresent()) {
                            long offerId = offer.getId();
                            Company company = findCompany.get();
                            String companyName = company.getName();

                            table.append("<a class=\"table-row\" href=\"/controller?command=show_trading_offer&offer_id=").append(offerId).append("\">")
                                    .append("<div class=\"table-data\">").append(i + 1).append("</div>")
                                    .append("<div class=\"table-data\">").append(companyName).append("</div>")
                                    .append("<div class=\"table-data\">").append(offer.getAddressFrom()).append(" - ").append(offer.getAddressTo()) .append("</div>")
                                    .append("<div class=\"table-data\">").append(offer.getProductWeight()).append("</div>")
                                    .append("<div class=\"table-data\">").append(offer.getProductVolume()).append("</div>")
                                    .append("<div class=\"table-data\">").append(offer.getProductName()).append("</div>")
                                    .append("<div class=\"table-data\">").append(offer.getCreationDate()).append("</div>")
                                    .append("<div class=\"table-data\">").append(offer.getFreight()).append("</div>")
                                    .append("</a>");
                        }
                    }
                }
            }

            table.append("</form>");

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
