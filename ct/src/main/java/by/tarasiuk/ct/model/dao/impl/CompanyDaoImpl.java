package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.entity.impl.Company;
import by.tarasiuk.ct.entity.Entity;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.model.dao.CompanyDao;
import by.tarasiuk.ct.util.CompanyBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CompanyDaoImpl extends BaseDao<Company> implements CompanyDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final CompanyDaoImpl instance = new CompanyDaoImpl();

    private static final String SQL_PROCEDURE_CREATE_COMPANY = "{CALL create_company (?, ?, ?)}";
    private static final String SQL_PROCEDURE_FIND_COMPANY_BY_NAME = "{CALL find_company_by_name (?)}";

    private CompanyDaoImpl() {
    }

    public static CompanyDaoImpl getInstance() {
        return instance;
    }

    public Optional<Company> findCompanyByName(String name) throws DaoException {
        Connection connection = connectionPool.getConnection();
        Optional<Company> findCompany;

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_COMPANY_BY_NAME)) {
            statement.setString(IndexFind.NAME, name);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Company company = CompanyBuilder.buildCompany(result);
                    findCompany = Optional.of(company);
                } else {
                    findCompany = Optional.empty();
                }
            }

            return findCompany;
        } catch (SQLException e) {
            LOGGER.error("Error when performing company search by name '{}'.", name, e);
            throw new DaoException("Error when performing company search by name '" + name + "'.", e);
        } finally {
            closeConnection(connection);
        }
    }

    public boolean createCompany(Company company) throws DaoException {
        Connection connection = connectionPool.getConnection();

        String name = company.getName();
        String address = company.getAddress();
        String phoneNumber = company.getPhoneNumber();

        try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_CREATE_COMPANY)) {
            statement.setString(IndexCreate.NAME, name);
            statement.setString(IndexCreate.ADDRESS, address);
            statement.setString(IndexCreate.PHONE_NUMBER, phoneNumber);

            statement.executeUpdate();

            LOGGER.info("Company was successfully created in the database: {}.", company);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Failed to create company in the database: {}.", company, e);
            throw new DaoException("Failed to create company in the database: " + company + ".", e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public boolean createEntity(Company entity) throws DaoException {
        return false;
    }

    @Override
    public List<Entity> findAll() throws DaoException {
        return null;
    }

    @Override
    public Company updateEntity(Company entity) throws DaoException {
        return null;
    }

    @Override
    public Optional<Company> findEntityById(int id) throws DaoException {
        return Optional.empty();
    }

    private static final class IndexFind {
        private static final int NAME = 1;
    }

    private static final class IndexCreate {
        private static final int NAME = 1;
        private static final int ADDRESS = 2;
        private static final int PHONE_NUMBER = 3;
    }
}
