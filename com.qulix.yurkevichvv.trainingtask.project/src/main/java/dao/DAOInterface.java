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
    /**
     * Add entities.
     *
     * @throws SQLException if an SQL error occurs
     */
    boolean add(T t) throws SQLException;
    /**
     * Update entities.
     *
     * @throws SQLException if an SQL error occurs
     */
    boolean update(T t) throws SQLException;

    /**
     * Delete entities by id.
     *
     * @throws SQLException if an SQL error occurs
     */
    boolean delete(Integer t) throws SQLException;

    /**
     * Get all entities.
     *
     * @throws SQLException if an SQL error occurs
     */
    List<T> getAll() throws SQLException;

    /**
     * Get entity by id.
     *
     * @throws SQLException if an SQL error occurs
     */
    T getById(Integer id) throws SQLException;
}
