package by.tarasiuk.ct.model.dao;

import by.tarasiuk.ct.exception.DaoException;
import by.tarasiuk.ct.model.entity.impl.Company;

import java.util.Optional;

public interface CompanyDao {
    Optional<Company> findEntityByName(String name) throws DaoException;
}
