package by.tarasiuk.ct.model.service.impl;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.exception.ServiceException;
import by.tarasiuk.ct.model.dao.DaoProvider;
import by.tarasiuk.ct.model.dao.impl.EmployeeDaoImpl;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.entity.impl.Employee;
import by.tarasiuk.ct.model.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final EmployeeServiceImpl instance = new EmployeeServiceImpl();
    private final EmployeeDaoImpl employeeDao = DaoProvider.getEmployeeDao();

    private EmployeeServiceImpl() {
    }

    public static EmployeeServiceImpl getInstance() {
        return instance;
    }

    @Override
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

    @Override
    public Optional<Employee> findEmployeeByAccountId(long accountId) throws ServiceException {
        Optional<Employee> findEmployee;

        try {
            findEmployee = employeeDao.findEmployeeByAccountId(accountId);
            LOGGER.info(findEmployee.isPresent()
                    ? "Successfully was find employee by account id '{}'."
                    : "Employee with account id '{}' not found in the database.", accountId);
        } catch (DaoException e) {
            LOGGER.error("Error when searching for an employee by account id '{}'.", accountId, e);
            throw new ServiceException("Error when searching for an employee by account id '" + accountId + "'.", e);
        }

        return findEmployee;
    }

    @Override
    public Optional<Employee> findEmployeeById(long employeeId) throws ServiceException {
        Optional<Employee> findEmployee;

        try {
            findEmployee = employeeDao.findEntityById(employeeId);
            LOGGER.info(findEmployee.isPresent()
                    ? "Successfully was find employee by id '{}'."
                    : "Employee with account id '{}' not found in the database.", employeeId);
        } catch (DaoException e) {
            LOGGER.error("Error when searching for an employee by id '{}'.", employeeId, e);
            throw new ServiceException("Error when searching for an employee by account id '" + employeeId + "'.", e);
        }

        return findEmployee;
    }

    @Override
    public List<Employee> findEmployeeListByCompanyId(long id) throws ServiceException {
        List<Employee> employeeList;

        try {
            employeeList = employeeDao.findEmployeeListByCompanyId(id);
        } catch (DaoException e) {
            LOGGER.error("Error while find employee list by company ID '{}'.", id, e);
            throw new ServiceException("Error while find employee list by company ID '" + id + "'.", e);
        }

        return employeeList;
    }

    @Override
    public Optional<Company> findCompanyByEmployeeId(long id) throws ServiceException {
        Optional<Company> findCompany;

        try {
            findCompany = employeeDao.findCompanyByEmployeeId(id);
            LOGGER.info(findCompany.isPresent()
                    ? "Successfully was find company by employee id '{}'."
                    : "Company for employee with id '{}' not found in the database.", id);
        } catch (DaoException e) {
            LOGGER.error("Error when searching company for employee with id '{}'.", id, e);
            throw new ServiceException("Error when searching company for employee with id '" + id + "'.", e);
        }

        return findCompany;
    }
}
