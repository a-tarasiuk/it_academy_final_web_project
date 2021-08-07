package by.tarasiuk.ct.model.dao.builder;

import by.tarasiuk.ct.model.entity.impl.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.tarasiuk.ct.manager.ColumnLabel.ACCOUNT_ID;
import static by.tarasiuk.ct.manager.ColumnLabel.COMPANY_ID;
import static by.tarasiuk.ct.manager.ColumnLabel.EMPLOYEE_ID;

public class EmployeeDaoBuilder {

    private EmployeeDaoBuilder() {
    }

    public static Employee build(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(EMPLOYEE_ID);
        long accountId = resultSet.getLong(ACCOUNT_ID);
        long companyId = resultSet.getLong(COMPANY_ID);

        Employee employee = new Employee();
        employee.setId(id);
        employee.setAccountId(accountId);
        employee.setCompanyId(companyId);

        return employee;
    }
}
