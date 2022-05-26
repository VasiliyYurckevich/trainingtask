import java.sql.SQLException;
import java.util.List;

/**
 * Обобщает основные методы для записи сущностей в БД.
 *
 * @author Q-YVV
 */
public interface DAOInterface<T> {

    /**
    * Добовляет объект в БД.
    */
    boolean add(T t) throws SQLException;

    /**
     * Изменяет объект в БД.
     */
    boolean update(T t) throws SQLException;

    /**
     * Удаляет объект из БД.
     */
    boolean delete(Integer t) throws SQLException;

    /**
     * Получет все записи из таблицы БД.
     */
    List<T> getAll() throws SQLException;

    /**
     * Ищет определенный объект в БД.
     */
    T getById(Integer id) throws SQLException;

}
