package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.model.dao.CompanyDao;
import by.tarasiuk.ct.model.entity.impl.Company;
import by.tarasiuk.ct.model.dao.builder.CompanyDaoBuilder;
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
    private static final String SQL_PROCEDURE_FIND_COMPANY_BY_ID = "{CALL find_company_by_id (?)}";
    private static final String SQL_PROCEDURE_UPDATE_COMPANY_BY_ID = "{CALL update_company_by_id (?, ?, ?, ?)}";

    private static final class IndexFind {
        private static final int NAME = 1;
        private static final int ID = 1;
    }

    private static final class IndexCreate {
        private static final int NAME = 1;
        private static final int ADDRESS = 2;
        private static final int PHONE_NUMBER = 3;
    }

    private static final class IndexUpdate {
        private static final int COMPANY_ID = 1;
        private static final int COMPANY_NAME = 2;
        private static final int COMPANY_ADDRESS = 3;
        private static final int COMPANY_PHONE_NUMBER = 4;
    }

    private CompanyDaoImpl() {
    }

    public static CompanyDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Company> findCompanyByName(String name) throws DaoException {
        Optional<Company> findCompany = Optional.empty();

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_COMPANY_BY_NAME)) {
                statement.setString(IndexFind.NAME, name);

                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        Company company = CompanyDaoBuilder.buildCompany(result);
                        findCompany = Optional.of(company);
                    }
                }
            }

            return findCompany;
        } catch (SQLException e) {
            LOGGER.error("Error when performing company search by name '{}'.", name, e);
            throw new DaoException("Error when performing company search by name '" + name + "'.", e);
        }
    }

    @Override
    public boolean createEntity(Company entity) throws DaoException {
        boolean result;

        String name = entity.getName();
        String address = entity.getAddress();
        String phoneNumber = entity.getPhoneNumber();

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_CREATE_COMPANY)) {
            statement.setString(IndexCreate.NAME, name);
            statement.setString(IndexCreate.ADDRESS, address);
            statement.setString(IndexCreate.PHONE_NUMBER, phoneNumber);

            int rowCount = statement.executeUpdate();
            result = rowCount == 1;

            LOGGER.info(result ? "Company was successfully created in the database: {}."
                    : "Failed to create company '{}'.", entity);
        } catch (SQLException e) {
            LOGGER.error("Failed to create company in the database: {}.", entity, e);
            throw new DaoException("Failed to create company in the database: " + entity + ".", e);
        }

        return result;
    }

    @Override
    public List<Company> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updateEntity(Company entity) throws DaoException {
        boolean result;

        long companyId = entity.getId();
        String companyName = entity.getName();
        String companyAddress = entity.getAddress();
        String companyPhoneNumber = entity.getPhoneNumber();

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_UPDATE_COMPANY_BY_ID)) {
            statement.setLong(IndexUpdate.COMPANY_ID, companyId);
            statement.setString(IndexUpdate.COMPANY_NAME, companyName);
            statement.setString(IndexUpdate.COMPANY_ADDRESS, companyAddress);
            statement.setString(IndexUpdate.COMPANY_PHONE_NUMBER, companyPhoneNumber);

            int rowCount = statement.executeUpdate();
            result = rowCount == 1;

            LOGGER.info(result ? "Offer '{}' has been successfully updated in the database."
                    : "Failed to update company '{}'.", entity);
        } catch (SQLException e) {
            LOGGER.error("Failed updating company '{}' in the database.", entity, e);
            throw new DaoException("Failed updating company '" + entity + "' in the database.", e);
        }

        return result;
    }

    @Override
    public Optional<Company> findEntityById(long id) throws DaoException {
        Optional<Company> findCompany = Optional.empty();

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQL_PROCEDURE_FIND_COMPANY_BY_ID)) {
                statement.setLong(IndexFind.ID, id);

                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        Company company = CompanyDaoBuilder.buildCompany(result);
                        findCompany = Optional.of(company);
                    }
                }
            }

            return findCompany;
        } catch (SQLException e) {
            LOGGER.error("Error when performing company search by id '{}'.", id, e);
            throw new DaoException("Error when performing company search by id '" + id + "'.", e);
        }
    }
}
