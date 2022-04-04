package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for DAO classes.
 *
 * @param <T>   type of entity
 */
public interface DAOInterface<T> {
    boolean add(T t) throws SQLException;

    boolean update(T t) throws SQLException;

    boolean delete(Integer t) throws SQLException;

    List<T> getAll() throws SQLException;

    T getById(Integer id) throws SQLException;
}
