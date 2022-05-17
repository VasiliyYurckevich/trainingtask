package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for DAO classes.
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
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
