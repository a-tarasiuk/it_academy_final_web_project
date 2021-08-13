package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Employee;

import java.util.Optional;

public interface EmployeeService {
    boolean createEmployee(long accountId, long companyId) throws ServiceException;
    Optional<Employee> findEmployeeByAccountId(long accountId) throws ServiceException;
    Optional<Employee> findEmployeeById(long employeeId) throws ServiceException;
}
