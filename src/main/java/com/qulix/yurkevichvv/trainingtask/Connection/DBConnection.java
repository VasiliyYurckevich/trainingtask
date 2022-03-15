package com.qulix.yurkevichvv.trainingtask.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    private static final String PATH = "jdbc:hsqldb:hsql://localhost/mydb;ifexists=true";
    private static final String USER = "sa";
    private static final String PASS = "";
    static final String dbLocation = "/home/yurkevichvv/IdeaProjects/hsqldb/hsqldb-2.6.1/hsqldb/";
    private static Connection connection = null;
  //  static org.hsqldb.server.Server sonicServer;
   /* static {
        HsqlProperties props = new HsqlProperties();
        props.setProperty("server.database.0","mydb");
        props.setProperty("server.dbname.0", "mydb");
        sonicServer = new org.hsqldb.Server();
        try {
            sonicServer.setProperties(props);
        } catch (Exception e) {
        }
        sonicServer.start();
    }
*/  /*public void stopDBServer() {
        sonicServer.shutdown();
    }*/
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(PATH, USER, PASS);
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }


}



