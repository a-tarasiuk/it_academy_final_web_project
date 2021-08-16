package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.exception.ServiceException;
import java.util.Map;
import java.util.Optional;

/**
 * Service that works with company data.
 */
public interface CompanyService {
    /**
     * Validate company data.
     *
     * @param companyData           Company data.
     * @return                      <code>true</code> if the company data is valid.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean validateCompany(Map<String, String> companyData) throws ServiceException;

    /**
     * Trying to create company from company data.
     *
     * @param companyData           Company data.
     * @return                      <code>true</code> if the company was successfully created.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean createCompany(Map<String, String> companyData) throws ServiceException;

    /**
     * Find company entity by company name.
     *
     * @param name                  Company name.
     * @return                      Optional of company entity.
     * @throws ServiceException     Default exception of service layer.
     */
    Optional<Company> findCompanyByName(String name) throws ServiceException;

    /**
     * find company entity by company ID.
     *
     * @param companyId             Company ID.
     * @return                      Optional of company entity.
     * @throws ServiceException     Default exception of service layer.
     */
    Optional<Company> findCompanyById(long companyId) throws ServiceException;
}
