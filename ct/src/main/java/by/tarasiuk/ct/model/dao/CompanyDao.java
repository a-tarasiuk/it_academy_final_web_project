package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.entity.impl.Company;
import java.util.Optional;

/**
 * Interface for operations with the data of the company,
 * which are contained in the in the database table <code>companies</code>.
 */
public interface CompanyDao {
    /**
     * Find company entity by company name in the database.
     * @param name              Company name.
     * @return                  Optional of String if existing Account with ID in the database.
     *                          Otherwise return an empty Optional instance. No value is present for this Optional.
     * @throws DaoException     If a database access error occurs; this method is called on a closed PreparedStatement
     *                          or an argument is supplied to this method.
     */
    Optional<Company> findCompanyByName(String name) throws DaoException;
}
