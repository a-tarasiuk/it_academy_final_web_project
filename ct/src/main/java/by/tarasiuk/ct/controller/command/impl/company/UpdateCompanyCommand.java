package by.tarasiuk.ct.controller.command.impl.company;

import by.tarasiuk.ct.controller.RequestContent;
import by.tarasiuk.ct.controller.command.AttributeName;
import by.tarasiuk.ct.controller.command.Command;
import by.tarasiuk.ct.controller.command.PagePath;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.service.ServiceProvider;
import by.tarasiuk.ct.model.service.impl.CompanyServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
import java.util.Optional;

import static by.tarasiuk.ct.controller.command.AttributeName.INFORMATION_MESSAGE;
import static by.tarasiuk.ct.controller.command.AttributeName.INVALID_DATA;
import static by.tarasiuk.ct.controller.command.AttributeName.MESSAGE_QUERY_ERROR;
import static by.tarasiuk.ct.controller.command.AttributeName.SUCCESSFUL_OPERATION;

/**
 * Update company information command.
 */
public class UpdateCompanyCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CompanyServiceImpl companyService = ServiceProvider.getCompanyService();

    /**
     * Get the company ID from the request.
     * First, the address and phone number of the company are validated.
     * If successful, the information about the company is updated in the database
     * and returned to the page with a successful message.
     * Otherwise, redirection to the information page.
     *
     * @param content   Request data content.
     * @return          Page path.
     * @see             by.tarasiuk.ct.controller.RequestContent
     */
    @Override
    public String execute(RequestContent content) {
        String page = PagePath.COMPANY_SETTINGS;
        Map<String, String> companyData = content.getRequestParameters();

        long companyId = Long.parseLong(companyData.get(AttributeName.COMPANY_ID));

        try {
            String companyAddress = companyData.get(AttributeName.COMPANY_ADDRESS);
            String companyPhoneNumber = companyData.get(AttributeName.COMPANY_PHONE_NUMBER);

            if(!companyService.validateCompanyData(companyAddress, companyPhoneNumber)) {
                LOGGER.debug("Invalid company data '{}' for updating company with ID '{}'.", companyData, companyId);
                content.putRequestAttribute(INVALID_DATA, true);
            } else {
                if(companyService.updateCompanyDataById(companyId, companyAddress, companyPhoneNumber)) {
                    content.putSessionAttribute(SUCCESSFUL_OPERATION, true);
                } else {
                    content.putSessionAttribute(MESSAGE_QUERY_ERROR, true);
                }
            }

            Optional<Company> findCompany = companyService.findCompanyById(companyId);
            if(findCompany.isPresent()) {
                Company company = findCompany.get();
                content.putRequestAttribute(AttributeName.COMPANY, company);
            }
        } catch (ServiceException e) {
            LOGGER.warn("Can't update company data '{}' for company with ID '{}'.", companyData, companyId, e);
            content.putRequestAttribute(INFORMATION_MESSAGE, true);
            page = PagePath.ACCOUNT_SETTINGS;   //fixme (сделать перенаправление на страницу ошибок)
        }

        return page;
    }
}
