package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.exception.ServiceException;

import java.util.Map;

public interface CompanyService {
    boolean createNewCompany(Map<String, String> companyData) throws ServiceException;
    boolean isExistCompany(String name) throws ServiceException;
}
