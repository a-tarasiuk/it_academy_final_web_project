package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.entity.AccountStatus;
import by.tarasiuk.ct.entity.Entity;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.manager.DatabaseTableName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AccountStatusDao extends BaseDao<AccountStatus> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AccountStatusDao instance = new AccountStatusDao();

    private final String SQL_PROCEDURE_GET_ACCOUNT_STATUS_NAME_BY_ID = "{call get_account_status_name_by_id (?)";

    private AccountStatusDao(){
    }

    public static AccountStatusDao getInstance() {
        return instance;
    }

    @Override
    public List<Entity> findAll() throws DaoException {
        return null;
    }

    @Override
    public AccountStatus update(AccountStatus accountStatus) throws DaoException {
        return null;
    }

    @Override
    public Optional<AccountStatus> findEntityById(int id) throws DaoException {
        Connection connection = connectionPool.getConnection();
        CallableStatement statement = null;

        try {
            statement = connection.prepareCall(SQL_PROCEDURE_GET_ACCOUNT_STATUS_NAME_BY_ID);
            statement.setString(ParameterIndex.ID, String.valueOf(id));
            statement.registerOutParameter(DatabaseTableName.ACCOUNT_STATUS_NAME.getColumnName(), Types.VARCHAR);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String accountStatusName = result.getString(DatabaseTableName.ACCOUNT_STATUS_NAME.getColumnName());
                AccountStatus accountStatus = AccountStatus.valueOf(accountStatusName);

                return Optional.of(accountStatus);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("Database access error occurs or this method is called on a closed connection: {}.", connection, e);
            throw new DaoException("database access error occurs or this method is called on a closed connection: " + connection, e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Database access error occurs when trying to close Statement object.", e);
                throw new DaoException("Database access error occurs when trying to close Statement object." + e);
            }
        }
    }

    private static class ParameterIndex {
        private static final int ID = 1;
    }
}
