package by.tarasiuk.ct.model.dao.impl;

import by.tarasiuk.ct.entity.AccountRole;
import by.tarasiuk.ct.entity.Entity;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.dao.BaseDao;
import by.tarasiuk.ct.manager.DatabaseTableName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AccountRoleDao extends BaseDao<AccountRole> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AccountRoleDao instance = new AccountRoleDao();

    private final String SQL_PROCEDURE_GET_ACCOUNT_ROLE_NAME_BY_ID = "{call get_account_role_name_by_id (?)";

    private AccountRoleDao(){
    }

    public static AccountRoleDao getInstance() {
        return instance;
    }

    @Override
    public Optional<AccountRole> findEntityById(int id) throws DaoException {
        Connection connection = connectionPool.getConnection();
        CallableStatement statement = null;

        try {
            statement = connection.prepareCall(SQL_PROCEDURE_GET_ACCOUNT_ROLE_NAME_BY_ID);
            statement.setString(ParameterIndex.ID, String.valueOf(id));
            statement.registerOutParameter(DatabaseTableName.ACCOUNT_ROLE_NAME.getColumnName(), Types.VARCHAR);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String accountRoleName = result.getString(DatabaseTableName.ACCOUNT_ROLE_NAME.getColumnName());
                AccountRole accountRole = AccountRole.valueOf(accountRoleName);

                return Optional.of(accountRole);
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

    @Override
    public List<Entity> findAll() throws DaoException {
        return null;
    }

    @Override
    public AccountRole update(AccountRole entity) throws DaoException {
        return null;
    }

    private static class ParameterIndex {
        private static final int ID = 1;
    }
}
