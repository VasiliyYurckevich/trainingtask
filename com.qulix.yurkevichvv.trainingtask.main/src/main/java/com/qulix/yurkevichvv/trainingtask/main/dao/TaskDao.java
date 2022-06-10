package com.qulix.yurkevichvv.trainingtask.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.qulix.yurkevichvv.trainingtask.main.connection.DBConnection;
import com.qulix.yurkevichvv.trainingtask.main.entity.Task;
import com.qulix.yurkevichvv.trainingtask.main.exceptions.DaoException;
import com.qulix.yurkevichvv.trainingtask.main.utils.Nums;

/**
 * Содержит методы для работы обьектов класса "Задача" с БД.
 *
 * @author Q-YVV
 * @see IDao
 * @see Task
 */
public class TaskDao implements IDao<Task> {

    private static final String TASK_ID = "id";

    private static final String STATUS = "status";

    private static final String TITLE = "title";

    private static final String PROJECT_ID = "project_id";

    private static final String WORK_TIME = "work_time";

    private static final String BEGIN_DATE = "begin_date";

    private static final String END_DATE = "end_date";

    private static final String EMPLOYEE_ID = "employee_id";

    private static final Logger LOGGER = Logger.getLogger(TaskDao.class.getName());


    private static final String INSERT_TASK_SQL = "INSERT INTO TASK" +
        " (status, title, work_time, begin_date,end_date, project_id, employee_id ) VALUES (?,?,?,?,?,?,?);";

    private static final String SELECT_ALL_TASK = "SELECT * FROM TASK;";

    private static final String SELECT_TASK_BY_ID = "SELECT * FROM TASK WHERE id = ?;";

    private static final String SELECT_TASK_BY_PROJECT = "SELECT * FROM TASK WHERE project_id = ?;";

    private static final String DELETE_TASK_SQL = "DELETE FROM TASK WHERE id = ?;";

    private static final String UPDATE_TASK_SQL = "UPDATE TASK SET status = ?, title = ?, work_time = ?," +
        " begin_date = ?, end_date = ?, project_id = ?, employee_id = ? WHERE id = ?;";



    @Override
    public boolean add(Task task) throws DaoException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TASK_SQL)) {
            setDataInToStatement(task, preparedStatement);
            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при добавлении задачи в БД", e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }


    @Override
    public boolean update(Task task) throws DaoException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK_SQL)) {
            setDataInToStatement(task, preparedStatement);
            preparedStatement.setInt(Nums.EIGHT.getValue(), task.getId());

            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при обновлении задачи в БД", e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Внесение данных о задаче в выражение SQL.
     *
     * @param task обьект класса "Задача".
     * @param preparedStatement выражение SQL.
     * @throws SQLException исключение БД.
     */
    private void setDataInToStatement(Task task, PreparedStatement preparedStatement) throws DaoException {
        try {
            preparedStatement.setString(Nums.ONE.getValue(), task.getStatus());
            preparedStatement.setString(Nums.TWO.getValue(), task.getTitle());
            preparedStatement.setLong(Nums.THREE.getValue(), task.getWorkTime());
            preparedStatement.setString(Nums.FOUR.getValue(), task.getBeginDate().toString());
            preparedStatement.setString(Nums.FIVE.getValue(), task.getEndDate().toString());

            if (task.getProjectId() == null) {
                preparedStatement.setNull(Nums.SIX.getValue(), Nums.ZERO.getValue());
            }
            else {
                preparedStatement.setInt(Nums.SIX.getValue(), task.getProjectId());
            }
            if (task.getEmployeeId() == null) {
                preparedStatement.setNull(Nums.SEVEN.getValue(), Nums.ZERO.getValue());
            }
            else {
                preparedStatement.setInt(Nums.SEVEN.getValue(), task.getEmployeeId());
            }
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при внесении данных о задаче в выражение SQL", e);
        }
    }


    @Override
    public boolean delete(Integer id) throws DaoException {
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TASK_SQL)){
            preparedStatement.setInt(Nums.ONE.getValue(), id);
            return preparedStatement.execute();
        }
        catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при удалении задачи из БД", e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Получение всех задач определенного проекта из БД.
     *
     * @param id идентификатор проекта.
     * @return все задачи проекта.
     * @throws SQLException исключение БД.
     */
    public List<Task> getTasksInProject(Integer id) throws DaoException {

        Connection connection = DBConnection.getConnection();
        List<Task> tasks = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_PROJECT)) {
            preparedStatement.setInt(Nums.ONE.getValue(), id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getList(tasks, resultSet);
        }
        catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении задач проекта из БД", e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    @Override
    public List<Task> getAll() throws DaoException {

        Connection connection = DBConnection.getConnection();

        try( PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TASK)) {
            List<Task> tasks = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            return getList(tasks, resultSet);
        }
        catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении всех задач из БД", e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Получение добавление задачи в лист.
     *
     * @param tasks лист задач.
     * @param resultSet выражение SQL.
     * @return лист задач.
     * @throws SQLException исключение БД.
     */
    private List<Task> getList(List<Task> tasks, ResultSet resultSet) throws DaoException {
        try {
            while (resultSet.next()) {
                Task task = new Task();
                setDataFromDatabase(task, resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении задач из БД", e);
        }
        return tasks;
    }

    @Override
    public Task getById(Integer id) throws DaoException {

        Connection connection = DBConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TASK_BY_ID)) {
            preparedStatement.setInt(Nums.ONE.getValue(), id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Task task = new Task();
            while (resultSet.next()) {
                setDataFromDatabase(task, resultSet);
            }
            return task;
        }
        catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении задачи по идентификатору из БД", e);
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Заполнение обьекта данными из БД.
     *
     * @param resultSet выражение SQL.
     * @throws SQLException исключение БД.
     */
    private void setDataFromDatabase(Task task, ResultSet resultSet) throws DaoException {
        try {
            task.setId(resultSet.getInt(TASK_ID));
            task.setStatus(resultSet.getString(STATUS));
            task.setTitle(resultSet.getString(TITLE));
            task.setWorkTime(resultSet.getInt(WORK_TIME));
            task.setBeginDate(LocalDate.parse(resultSet.getString(BEGIN_DATE)));
            task.setEndDate(LocalDate.parse(resultSet.getString(END_DATE)));
            task.setProjectId(resultSet.getInt(PROJECT_ID));

            if (resultSet.getInt(EMPLOYEE_ID) != 0) {
                task.setEmployeeId(resultSet.getInt(EMPLOYEE_ID));
            }
            else {
                task.setEmployeeId(null);
            }
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            LOGGER.severe("SQLState: " + e.getSQLState());
            LOGGER.severe(Arrays.toString(e.getStackTrace()));
            throw new DaoException("Ошибка при получении данных задачи из БД", e);
        }
    }
}
