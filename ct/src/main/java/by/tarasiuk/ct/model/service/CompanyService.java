package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.entity.Company;
import by.tarasiuk.ct.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface CompanyService {
    boolean validateCompany(Map<String, String> companyData) throws ServiceException;

    boolean createNewCompany(Map<String, String> companyData) throws ServiceException;
    Optional<Company> findCompanyByName(String name) throws ServiceException;
}
