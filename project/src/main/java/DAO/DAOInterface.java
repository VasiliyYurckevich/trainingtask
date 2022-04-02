package DAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for DAO classes.
 *
 * @param <T>   type of entity
 */
public interface DAOInterface<T> {
    boolean add(T t) throws SQLException;//add new object to table

    boolean update(T t) throws SQLException;// update record in table

    boolean delete(Integer t) throws SQLException;//delete record by id

    List<T> getAll() throws SQLException;// get all records from table

    T getById(Integer id) throws SQLException;// get object by id
}
