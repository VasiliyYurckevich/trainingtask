package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for DAO classes.
 *
 * <h1>DAOInterface</h1>
 * <h2>Purpose:</h2>
 * <li>Provides a common interface for all DAO classes.</li>
 * <h2>Description:</h2>
 * <li>This interface provides a common interface for all DAO classes.</li>
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
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
