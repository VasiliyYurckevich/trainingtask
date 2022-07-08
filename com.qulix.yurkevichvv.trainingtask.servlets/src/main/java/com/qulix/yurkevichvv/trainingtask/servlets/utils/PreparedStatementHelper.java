package com.qulix.yurkevichvv.trainingtask.servlets.utils;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionManipulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreparedStatementHelper {
    private static final String REGEX = "(:(\\w+))";
    private static final Pattern pattern = Pattern.compile(REGEX);

    private PreparedStatement preparedStatement = null;

    public PreparedStatementHelper(String sqlStatement) {
    }
    public String parseSqlStatement(String sqlStatement, Map<String, Integer> paramsMap) {
        Matcher matcher = pattern.matcher(sqlStatement);
        int parameterIndex = 1;
        while (matcher.find()) {
            paramsMap.put(matcher.group(parameterIndex),parameterIndex);
        }
        String result = sqlStatement.replaceAll(REGEX, "?");
        System.out.println(result);
        return result;
    }

    public void setParameters(Connection connection, String sqlStatement) throws SQLException {
        Map<String,Integer> parametersMap =  new HashMap<>();
        String result = parseSqlStatement(sqlStatement, parametersMap);
        PreparedStatement preparedStatement = connection.prepareStatement(result);
        for (Map.Entry<String, Integer> entry : parametersMap.entrySet()) {
            System.out.println("Key " + entry.getKey() + " Value " + entry.getValue());
            preparedStatement.setInt(entry.getValue(),1);

        }
        preparedStatement.execute();
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionManipulator.getConnection();
        String SELECT_PROJECT_BY_ID = "DELETE FROM PROJECT WHERE id = :id;";
        PreparedStatementHelper preparedStatementHelper = new PreparedStatementHelper();
        preparedStatementHelper.setParameters(connection,SELECT_PROJECT_BY_ID);
    }

}

