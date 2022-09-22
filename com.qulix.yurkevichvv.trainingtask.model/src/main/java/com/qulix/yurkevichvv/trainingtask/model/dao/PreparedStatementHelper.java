package com.qulix.yurkevichvv.trainingtask.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Позволяет заполнять PreparedStatement с помощью имени переменной.
 *
 * @author Q-YVV
 */
public class PreparedStatementHelper implements AutoCloseable {

    /**
     * Двоеточие.
     */
    private static final String COLON = ":";
    
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
    private final Connection connection;

    /**
     * Предварительно скомпилированный оператор SQL.
     */
    private final PreparedStatement preparedStatement;

    /**
     * Map хранящаяся индекс параметра в запросе и его имя.
     */
    private final Map<String, Integer> parametersMap;

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
        catch (DaoException e) {
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
            return connection.prepareStatement(result, Statement.RETURN_GENERATED_KEYS);
        }
        catch (SQLException e) {
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
            if (value == null || value == 0) {
                this.preparedStatement.setNull(getIndex(name), Types.INTEGER);
            }
            else {
                this.preparedStatement.setInt(getIndex(name), value);
            }
        }
        catch (SQLException e) {
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
        catch (SQLException e) {
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
        catch (SQLException e) {
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
        return this.parametersMap.get(COLON + name);
    }

    /**
     * Выполняет SQL-запрос по получению данных из БД.
     *
     * @return объект результирующего набора, содержащий данные, полученные в результате запроса
     */
    public ResultSet executeQuery() {
        try {
            return this.preparedStatement.executeQuery();
        }
        catch (SQLException e) {
            throw new DaoException("Error when try execute PrepareStatement", e);
        }
    }

    /**
     * Выполняет SQL-оператор по изменению записей.
     *
     * @return количество обновленных строк
     */
    public Integer executeUpdate() {
        try {
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new DaoException("Error when try executeUpdate() PrepareStatement", e);
        }
    }

    /**
     * Возвращает сгенерированный идентификатор.
     *
     * @return сгенерированный идентификатор
     */
    public Integer getGeneratedKey() {
        try {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        }
        catch (SQLException e) {
            throw new DaoException("Error when try get generated keys of PrepareStatement", e);
        }
    }


    @Override
    public void close() {
        try {
            this.preparedStatement.close();
        }
        catch (SQLException e) {
            throw new DaoException("Error when try close PrepareStatement in PrepareStatementHelper", e);
        }
    }
}
