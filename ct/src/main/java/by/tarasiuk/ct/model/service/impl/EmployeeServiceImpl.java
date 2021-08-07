package by.tarasiuk.ct.model.service.impl;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.dao.DaoProvider;
import by.tarasiuk.ct.model.dao.impl.EmployeeDaoImpl;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final EmployeeServiceImpl instance = new EmployeeServiceImpl();
    private static final EmployeeDaoImpl employeeDao = DaoProvider.getEmployeeDao();

    private EmployeeServiceImpl() {
    }

    public static EmployeeServiceImpl getInstance() {
        return instance;
    }

    public boolean createEmployee(long accountId, long companyId) throws ServiceException {
        Employee employee = new Employee();
        employee.setAccountId(accountId);
        employee.setCompanyId(companyId);

        try {
            return employeeDao.createEntity(employee);
        } catch (DaoException e) {
            LOGGER.error("Error while creating employee '{}'.", employee, e);
            throw new ServiceException("Error while creating employee '" + employee +"'.", e);
        }
    }

    public Optional<Employee> findEmployeeByAccountId(long accountId) throws ServiceException {
        Optional<Employee> findEmployee;

        try {
            findEmployee = employeeDao.findEntityByAccountId(accountId);
            LOGGER.info(findEmployee.isPresent()
                    ? "Successfully was find employee by account id '{}'."
                    : "Employee with account id '{}' not found in the database.", accountId);
        } catch (DaoException e) {
            LOGGER.error("Error when searching for an employee by account id '{}'.", accountId, e);
            throw new ServiceException("Error when searching for an employee by account id '" + accountId + "'.", e);
        }

        return findEmployee;
    }
}
