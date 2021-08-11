package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.model.dao.EmployeeDao;
import by.tarasiuk.ct.model.dao.builder.EmployeeDaoBuilder;
import by.tarasiuk.ct.model.entity.impl.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl extends BaseDao<Employee> implements EmployeeDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();

    private static final String SQL_PROCEDURE_CREATE_EMPLOYEE = "{CALL create_employee (?, ?)}";
    private static final String SQL_PROCEDURE_FIND_EMPLOYEE_BY_ACCOUNT_ID = "{CALL find_employee_by_account_id (?)}";
    private static final String SQL_PROCEDURE_FIND_EMPLOYEE_BY_ID = "{CALL find_employee_by_id (?)}";

    private static final class IndexCreate {
        private static final int ACCOUNT_ID = 1;
        private static final int COMPANY_ID = 2;
    }

    private static final class IndexFind {
        private static final int EMPLOYEE_D = 1;
        private static final int ACCOUNT_ID = 1;
    }

    private EmployeeDaoImpl() {
    }

    public static EmployeeDaoImpl getInstance() {
        return instance;
    }

    public Optional<Employee> findEntityByAccountId(long accountId) throws DaoException {
        Connection connection = connectionPool.getConnection();
        Optional<Employee> findEmployee;

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_EMPLOYEE_BY_ACCOUNT_ID)) {
            statement.setLong(IndexFind.ACCOUNT_ID, accountId);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Employee employee = EmployeeDaoBuilder.build(result);
                    findEmployee = Optional.of(employee);
                } else {
                    findEmployee = Optional.empty();
                }
            }

            return findEmployee;
        } catch (SQLException e) {
            LOGGER.error("Error when performing employee search by account id '{}'.", accountId, e);
            throw new DaoException("Error when performing employee search by account id '" + accountId + "'.", e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<Employee> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean createEntity(Employee entity) throws DaoException {
        Connection connection = connectionPool.getConnection();

        long accountId = entity.getAccountId();
        long companyId = entity.getCompanyId();


    try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_CREATE_EMPLOYEE)) {
            statement.setLong(IndexCreate.ACCOUNT_ID, accountId);
            statement.setLong(IndexCreate.COMPANY_ID, companyId);

            statement.executeUpdate();

            LOGGER.info("Employee was successfully created in the database: {}.", entity);
            return true;    //fixme -> statement.executeUpdate(); (см. выше).
        } catch (SQLException e) {
            LOGGER.error("Failed to create employee in the database: {}.", entity, e);
            throw new DaoException("Failed to create employee in the database: " + entity + ".", e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Employee updateEntity(Employee entity) throws DaoException {
        return null;
    }

    @Override
    public Optional<Employee> findEntityById(long id) throws DaoException {
        Connection connection = connectionPool.getConnection();
        Optional<Employee> findEmployee;

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_EMPLOYEE_BY_ID)) {
            statement.setLong(IndexFind.EMPLOYEE_D, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Employee employee = EmployeeDaoBuilder.build(result);
                    findEmployee = Optional.of(employee);
                } else {
                    findEmployee = Optional.empty();
                }
            }

            return findEmployee;
        } catch (SQLException e) {
            LOGGER.error("Error when performing employee search by id '{}'.", id, e);
            throw new DaoException("Error when performing employee search by id '" + id + "'.", e);
        } finally {
            closeConnection(connection);
        }
    }
}