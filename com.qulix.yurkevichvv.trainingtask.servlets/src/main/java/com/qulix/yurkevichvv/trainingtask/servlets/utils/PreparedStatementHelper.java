package com.qulix.yurkevichvv.trainingtask.servlets.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.exceptions.DaoException;

/**
 * Позволяет заполнять PreparedStatement с помощью имени переменной.
 *
 * @author Q-YVV
 */
public class PreparedStatementHelper implements AutoCloseable {

    /**
     * Regex для поиска имени переменной.
     */
    private static final String REGEX = "(:(\\w+))";

    /**
     * Сообщение ошибки при создании PreparedStatementHelper.
     */
    public static final String ERROR_WHEN_TRY_CREATE_NEW_PREPARED_STATEMENT_HELPER =
        "Error when try create new PreparedStatementHelper";

    /**
     * SQL-запрос.
     */
    private final String sqlStatement;

    /**
     * Соединение с БД.
     */
    private Connection connection;

    /**
     * Предварительно скомпилированный оператор SQL.
     */
    private PreparedStatement preparedStatement;

    /**
     * Map хранящаяся индекс параметра в запросе и его имя.
     */
    private Map<String, Integer> parametersMap;

    /**
     * Логгер для ведения журнала действий.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectDao.class.getName());

    /**
     * Конструктор.
     *
     * @param sqlStatement SQL-запрос
     * @param connection соединение с БД
     * @throws DaoException если произошла ошибка при записи/получении данных из БД
     */
    public PreparedStatementHelper(String sqlStatement, Connection connection) throws DaoException {
        try {
            this.connection = connection;
            this.sqlStatement = sqlStatement;
            this.parametersMap = new HashMap<>();
            this.preparedStatement = fillPreparedStatement();
        }
        catch (Throwable e) {
            LOGGER.log(Level.SEVERE, "PreparedStatementHelper creating error", e);
            throw new DaoException(ERROR_WHEN_TRY_CREATE_NEW_PREPARED_STATEMENT_HELPER, e);
        }
    }

    /**
     * Приводит строку SQL-запроса в соответствии с требованиями PreparedStatement.
     *
     * @param sqlStatement SQL-запрос
     * @return SQL-запрос для PreparedStatement
     */
    public String parseSqlStatement(String sqlStatement) {
        Pattern pattern = Pattern.compile(REGEX);
        int paramIndex = 1;
        Matcher matcher = pattern.matcher(sqlStatement);
        while (matcher.find()) {
            this.parametersMap.put(matcher.group(), paramIndex);
            paramIndex++;
        }
        return sqlStatement.replaceAll(REGEX, "?");
    }

    /**
     * Создает PreparedStatement.
     *
     * @return предварительно скомпилированный оператор SQL
     */
    public PreparedStatement fillPreparedStatement() {
        try {
            String result = parseSqlStatement(sqlStatement);
            return connection.prepareStatement(result);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception creating PrepareStatement", e);
            throw new DaoException(ERROR_WHEN_TRY_CREATE_NEW_PREPARED_STATEMENT_HELPER, e);
        }
    }

    /**
     * Устанавливает указанный параметр в заданное значение Integer.
     *
     * @param name имя параметра
     * @param value значение параметра
     */
    public void setInt(String name, Integer value) {
        try {
            if (value == null) {
                this.preparedStatement.setNull(getIndex(name), Types.INTEGER);
            }
            else {
                this.preparedStatement.setInt(getIndex(name), value);
            }
        }
        catch (Throwable e) {
            LOGGER.log(Level.SEVERE, "PreparedStatementHelper set Integer in PreparedStatement error", e);
            throw new DaoException("setInt in PreparedStatement error", e);
        }
    }

    /**
     * Устанавливает указанный параметр в заданное значение String.
     *
     * @param name имя параметра
     * @param value значение параметра
     */
    public void setString(String name, String value) {
        try {
            if (value == null || value.isEmpty()) {
                this.preparedStatement.setNull(getIndex(name), Types.VARCHAR);
            }
            else {
                this.preparedStatement.setString(getIndex(name), value);
            }
        }
        catch (Throwable e) {
            LOGGER.log(Level.SEVERE, "PreparedStatementHelper set string in PreparedStatement error", e);
            throw new DaoException("setString in PreparedStatement error", e);
        }
    }

    /**
     * Устанавливает указанный параметр в заданное значение Date.
     *
     * @param name имя параметра
     * @param value значение параметра
     */
    public void setDate(String name, LocalDate value) {
        try {
            if (value == null) {
                this.preparedStatement.setNull(getIndex(name), Types.DATE);
            }
            else {
                this.preparedStatement.setDate(getIndex(name), Date.valueOf(value));
            }
        }
        catch (Throwable e) {
            LOGGER.log(Level.SEVERE, "PreparedStatementHelper set date in PreparedStatement error", e);
            throw new DaoException("setDate in PreparedStatement error", e);
        }
    }

    /**
     * Получает индекс параметра для SQL-запросов по имени.
     *
     * @param name имя параметра
     * @return индекс параметра
     */
    private Integer getIndex(String name) {
        return this.parametersMap.get(name);
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    /**
     * Выполняет SQL-запрос.
     */
    public void execute() {
        try {
            this.preparedStatement.execute();
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception creating PrepareStatementHelper", e);
            throw new DaoException("Error when try execute PrepareStatement", e);
        }
    }

    @Override
    public void close() {
        try {
            this.preparedStatement.close();
        }
        catch (Throwable e) {
            LOGGER.log(Level.SEVERE, "Exception close() PrepareStatementHelper", e);
            throw new DaoException("Error when try close PrepareStatement in PrepareStatementHelper", e);
        }
    }
}

