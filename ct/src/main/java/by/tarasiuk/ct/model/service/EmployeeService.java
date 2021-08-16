package by.tarasiuk.ct.model.service;

import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.entity.impl.Employee;
import java.util.Optional;

/**
 * Service that works with employee data.
 */
public interface EmployeeService {
    /**
     * Trying create employee by account ID and company ID.
     *
     * @param accountId             Account ID.
     * @param companyId             Company ID.
     * @return                      <code>true</code> if the employee was successfully created.
     *                              Otherwise return <code>false</code>.
     * @throws ServiceException     Default exception of service layer.
     */
    boolean createEmployee(long accountId, long companyId) throws ServiceException;

    /**
     * Find employee entity by account ID.
     *
     * @param accountId             Account ID.
     * @return                      Optional of employee entity.
     * @throws ServiceException     Default exception of service layer.
     */
    Optional<Employee> findEmployeeByAccountId(long accountId) throws ServiceException;

    /**
     * Find employee entity by employee ID.
     *
     * @param employeeId            Employee ID.
     * @return                      Optional of employee entity.
     * @throws ServiceException     Default exception of service layer.
     */
    Optional<Employee> findEmployeeById(long employeeId) throws ServiceException;
}
