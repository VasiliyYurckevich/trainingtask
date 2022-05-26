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
     */
     boolean add(T t) throws SQLException;

    /**
     * Update entities.
     */
    boolean update(T t) throws SQLException;

    /**
     * Delete entities by id.
     */
    boolean delete(Integer t) throws SQLException;

    /**
     * Get all entities.
     */
    List<T> getAll() throws SQLException;

    /**
     * Get entity by id.
     */
    T getById(Integer id) throws SQLException;

}
