import java.sql.SQLException;
import java.util.List;

/**
 * Обобщает основные методы для записи сущностей в БД.
 *
 * @author Q-YVV
 */
public interface DAOInterface<T> {

    /**
     * Добавление новой сущности в БД.
     *
     * @param t Сущность для добавления.
     * @return успешность операции.
     * @throws SQLException ошибка при выполнении запроса.
     */
    boolean add(T t) throws SQLException;

    /**
     * Обновление сущности в БД.
     *
     * @param t Сущность для обновления.
     * @return успешность операции.
     * @throws SQLException ошибка при выполнении запроса.
     */
    boolean update(T t) throws SQLException;

    /**
     * Удаление сущности из БД.
     *
     * @param t Сущность для удаления.
     * @return успешность операции.
     * @throws SQLException ошибка при выполнении запроса.
     */
    boolean delete(Integer t) throws SQLException;

    /**
     * Получение всех сущностей определеного класса из БД.
     *
     * @return Список сущностей.
     * @throws SQLException  ошибка при выполнении запроса.
     */
    List<T> getAll() throws SQLException;

    /**
     * Нахождение сущности по ее идентификатору.
     *
     * @param id Идентификатор сущности.
     * @return Сущность.
     * @throws SQLException ошибка при выполнении запроса.
     */
    T getById(Integer id) throws SQLException;

}
