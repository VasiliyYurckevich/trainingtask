package com.qulix.yurkevichvv.trainingtask.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.entity.Status;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;

/**
 * Содержит методы для работы объектов класса "Задача" с БД.
 *
 * @author Q-YVV
 * @see IDao
 * @see Task
 */
public class TaskDao implements IDao<Task> {

    /**
     * Имя колонки ID проекта в БД.
     */
    private static final String ID = "task.id";

    /**
     * Статус проекта в БД.
     */
    private static final String STATUS = "task.status";

    /**
     * Название задачи в БД.
     */
    private static final String TITLE = "task.title";

    /**
     * Проект, в который входит задача, в БД.
     */
    private static final String PROJECT_ID = "task.project_id";

    /**
     * Время работы над задачей в БД.
     */
    private static final String WORK_TIME = "task.work_time";

    /**
     * Дата начала работы над задачей в БД.
     */
    private static final String BEGIN_DATE = "task.begin_date";

    /**
     * Дата конца работы над задачей в БД.
     */
    private static final String END_DATE = "task.end_date";

    /**
     * Привязанный к задаче сотрудника в БД.
     */
    private static final String EMPLOYEE_ID = "task.employee_id";

    /**
     * Логгер для записи логов.
     */
    private static final Logger LOGGER = Logger.getLogger(TaskDao.class.getName());

    /**
     * Запрос добавления задачи в БД.
     */
    private static final String INSERT_TASK_SQL = "INSERT INTO TASK" +
        " (status, title, work_time, begin_date,end_date, project_id, employee_id)" +
        " VALUES (:task.status, :task.title, :task.work_time, :task.begin_date, :task.end_date," +
        " :task.project_id, :task.employee_id);";

    /**
     * Объединяет таблицы для получения данных задачи.
     */
    public static final String SELECT_ALL_INFO = "SELECT task.*, project.* , employee.* FROM task " +
        "LEFT JOIN project ON project.id = project_id LEFT JOIN EMPLOYEE ON employee.id = task.employee_id";

    /**
     * Все задачи из БД.
     */
    private static final String SELECT_ALL_TASK = SELECT_ALL_INFO + ";";

    /**
     * Задача из БД по идентификатору.
     */
    private static final String SELECT_TASK_BY_ID = SELECT_ALL_INFO + " WHERE task.id = :task.id;";

    /**
     * Задачи из БД по проекту.
     */
    private static final String SELECT_TASK_BY_PROJECT = SELECT_ALL_INFO + " WHERE task.project_id = :task.project_id;";

    /**
     * Запрос удаления задачи из БД по идентификатору.
     */
    private static final String DELETE_TASK_SQL = "DELETE FROM TASK WHERE task.id = :task.id;";

    /**
     * Запрос обновления задачи в БД.
     */
    private static final String UPDATE_TASK_SQL = "UPDATE TASK SET status = :task.status, title = :task.title," +
        " work_time = :task.work_time, begin_date = :task.begin_date, end_date = :task.end_date, project_id = :task.project_id," +
        " employee_id = :task.employee_id WHERE id = :task.id;";

    @Override
    public Integer add(Task task, Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(INSERT_TASK_SQL, connection)) {
            setDataInToStatement(task, preparedStatementHelper);

            Integer generatedKey = preparedStatementHelper.executeUpdate();
            if (generatedKey > 0) {
                LOGGER.log(Level.INFO, "Created new task");
                return generatedKey;
            }
            else {
                throw new DaoException("Error when adding a task to the database");
            }
        }
    }

    @Override
    public void update(Task task, Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(UPDATE_TASK_SQL, connection)) {
            setDataInToStatement(task, preparedStatementHelper);
            preparedStatementHelper.setInt(ID, task.getId());
            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Task with id {0} was updated", task.getId());
            }
            else {
                throw new DaoException("Error when updating a task in the database");
            }
        }
    }

    /**
     * Внесение данных о задаче в выражение SQL.
     *
     * @param task объект класса "Задача"
     * @param preparedStatementHelper объект {@link PreparedStatementHelper}, для обращения к БД
     */
    private void setDataInToStatement(Task task, PreparedStatementHelper preparedStatementHelper) {
        preparedStatementHelper.setInt(STATUS, task.getStatus().getId());
        preparedStatementHelper.setString(TITLE, task.getTitle());
        preparedStatementHelper.setInt(WORK_TIME, task.getWorkTime());
        preparedStatementHelper.setDate(BEGIN_DATE, task.getBeginDate());
        preparedStatementHelper.setDate(END_DATE, task.getEndDate());
        preparedStatementHelper.setInt(PROJECT_ID, task.getProject().getId());
        preparedStatementHelper.setInt(EMPLOYEE_ID, Optional.ofNullable(task.getEmployee()).map(Employee::getId).orElse(null));
    }

    @Override
    public void delete(Integer id, Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(DELETE_TASK_SQL, connection)) {
            preparedStatementHelper.setInt(ID, id);

            if (preparedStatementHelper.executeUpdate() > 0) {
                LOGGER.log(Level.INFO, "Task with id {0} was deleted", id);
            }
            else {
                LOGGER.log(Level.INFO, "Task with id {0} deleting failed", id);
            }
        }
    }

    /**
     * Получение всех задач определенного проекта из БД.
     *
     * @param id идентификатор проекта
     * @return все задачи проекта
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    public List<Task> getProjectTasks(Integer id, Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_TASK_BY_PROJECT, connection)) {
            preparedStatementHelper.setInt(PROJECT_ID, id);

            try (ResultSet resultSet = preparedStatementHelper.executeQuery()) {
                return getList(resultSet);
            }
            catch (SQLException e) {
                throw new DaoException("Error when getting project tasks from the database", e);
            }
        }
    }

    @Override
    public List<Task> getAll(Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_ALL_TASK, connection);
            ResultSet resultSet = preparedStatementHelper.executeQuery()) {
            return getList(resultSet);
        }
        catch (SQLException e) {
            throw new DaoException("Error when getting all tasks from the database", e);
        }
    }

    /**
     * Получение добавление задачи в лист.
     *
     * @param resultSet выражение SQL.
     * @return лист задач.
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private List<Task> getList(ResultSet resultSet) throws DaoException {

        List<Task> tasks = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Task task = new Task();
                setDataFromDatabase(task, resultSet);
                tasks.add(task);
            }
        }
        catch (SQLException e) {
            throw new DaoException("Error when getting tasks from the database", e);
        }
        return tasks;
    }

    @Override
    public Task getById(Integer id, Connection connection) throws DaoException {

        try (PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper(SELECT_TASK_BY_ID, connection)) {
            preparedStatementHelper.setInt(ID, id);

            try (ResultSet resultSet = preparedStatementHelper.executeQuery()) {
                if (resultSet.next()) {
                    Task task = new Task();
                    setDataFromDatabase(task, resultSet);
                    return task;
                }
                else {
                    throw new DaoException("An employee with such data was not found");
                }
            }
            catch (SQLException e) {
                throw new DaoException("Error when working with ResultSet", e);
            }
        }
    }

    /**
     * Заполнение объекта Task данными из БД.
     *
     * @param resultSet результат выполнения SQL-запроса
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    private void setDataFromDatabase(Task task, ResultSet resultSet) throws DaoException {
        try {
            task.setId(resultSet.getInt(ID));
            task.setStatus(Status.getStatusById(Integer.parseInt(resultSet.getString(STATUS))));
            task.setTitle(resultSet.getString(TITLE));
            task.setWorkTime(resultSet.getInt(WORK_TIME));
            task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
            task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
            task.setProject(ProjectDao.getProjectFromDB(resultSet));
            task.setEmployee(resultSet.getString(EMPLOYEE_ID) != null ?
                EmployeeDao.getEmployeeFromDB(resultSet) : null);
        }
        catch (SQLException e) {
            throw new DaoException("Error when retrieving task data from the database", e);
        }
    }
}
