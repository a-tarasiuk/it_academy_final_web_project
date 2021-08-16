package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.model.entity.Entity;
import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * Dao layer main class.
 *
 * @param <E>       Object extends {@link by.tarasiuk.ct.model.entity.Entity}
 */
public abstract class BaseDao <E extends Entity> {
    private static final Logger LOGGER = LogManager.getLogger();
    protected ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Create entity in the database.
     *
     * @param entity            Entity.
     * @return                  <code>true</code> if the password is successfully updated in the database.
     *                          Otherwise return <code>false</code>.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement.
     */
    public abstract boolean createEntity(E entity) throws DaoException;

    /**
     * Update entity in the database.
     *
     * @param entity            Entity.
     * @return                  <code>true</code> if the password is successfully updated in the database.
     *                          Otherwise return <code>false</code>.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement.
     */
    public abstract boolean updateEntity(E entity) throws DaoException;

    /**
     * Find all entities in the database.
     *
     * @return                  List of entities.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or an argument is supplied to this method.
     */
    public abstract List<E> findAll() throws DaoException;

    /**
     * Find entity by it's ID.
     *
     * @param id                Entity ID.
     * @return                  Optional of entity.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or an argument is supplied to this method.
     */
    public abstract Optional<E> findEntityById(long id) throws DaoException;


    /**
     * Close current connection.
     *
     * @param connection        Class instance of {@link by.tarasiuk.ct.model.connection.ProxyConnection}
     */
    public void closeConnection(Connection connection) {
        if(connection != null) {
            try {
                if(!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }

                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close connection '{}'.", connection, e);
            }
        }
    }

    /**
     * Close current statement.
     *
     * @param statement        Class instance of {@link java.sql.Statement}
     */
    public void closeStatement(Statement statement) {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Can't close statement '{}'.", statement, e);
            }
        }
    }
}
