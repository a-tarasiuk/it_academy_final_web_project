package by.tarasiuk.ct.model.service.impl;

import by.tarasiuk.ct.entity.Company;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.dao.DaoProvider;
import by.tarasiuk.ct.model.dao.impl.CompanyDaoImpl;
import by.tarasiuk.ct.model.service.CompanyService;
import by.tarasiuk.ct.util.CompanyBuilder;
import by.tarasiuk.ct.util.CompanyValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final CompanyDaoImpl companyDao = DaoProvider.getInstance().getCompanyDao();

    public boolean validateCompany(Map<String, String> companyData) {
        return CompanyValidator.isValidCompany(companyData);
    }

    @Override
    public boolean createNewCompany(Map<String, String> companyData) throws ServiceException {
        Company company = CompanyBuilder.buildCompany(companyData);

        try {
            return companyDao.createCompany(company);
        } catch (DaoException e) {
            LOGGER.error("Error while creating: '{}'.", company);
            throw new ServiceException("Error while creating: '" + company + "'.", e);
        }
    }

    @Override
    public boolean isExistCompany(String name) throws ServiceException {
        boolean result;

        try {
            Optional<Company> company = companyDao.findCompanyByName(name);
            result = company.isPresent();
            LOGGER.info(result ? "{} '{}' is exist in the database." : "{} '{}' not found in the database.", "Company with name", name);
        } catch (DaoException e) {
            LOGGER.error("Failed to perform company search operation by name: '{}'.", name);
            throw new ServiceException("Failed to perform company search operation by name: " + name, e);
        }

        return result;
    }
}
