package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.entity.impl.Employee;

import java.util.Optional;

public interface EmployeeDao {
    Optional<Employee> findEntityByAccountId(long accountId) throws DaoException;
}
