package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.entity.impl.Employee;
import java.util.List;
import java.util.Optional;

/**
 * Interface for operations with the data of the employee,
 * which are contained in the in the database table <code>employees</code>.
 */
public interface EmployeeDao {

    /**
     * Find employee entity by account ID.
     *
     * @param accountId         Account ID.
     * @return                  Optional of Employee entity if existing id in the database.
     *                          Otherwise return an empty Optional instance. No value is present for this Optional.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or the SQL statement does not return a ResultSet object.
     */
    Optional<Employee> findEmployeeByAccountId(long accountId) throws DaoException;


    /**
     * Find employee list by company ID in the database.
     *
     * @param id                Company ID.
     * @return                  List of the employees.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or the SQL statement does not return a ResultSet object.
     */
    List<Employee> findEmployeeListByCompanyId(long id) throws DaoException;
}
