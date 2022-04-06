package connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Connection to database.
 *
 *<p> {@link DBConnection} using for control connection to DB</p>
 *
 * <h2>Usage</h2>
 * <pre>
 * {@code  DBConnection dbConnection = new DBConnection();}
 * {@code  dbConnection.getConnection();}
 * {@code  dbConnection.closeConnection();}
 * </pre>
 *
 * <h2>Synchronization</h2>
 * <p>
 * This class is not guaranteed to be thread-safe so it should be synchronized externally.
 * </p>
 *
 * <h2>Known bugs</h2>
 * {@link DBConnection} does not handle overflows.
 *
 * @author Q-YVV
 * @version 1.0
 */
public class DBConnection {
    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    private static final String PATH = "jdbc:hsqldb:hsql://localhost/mydb;ifexists=true";
    private static final String USER = "sa";
    private static final String PASS = "";
    private static Connection connection = null;

    /**
     * Method for getting connection to database.
     *
     * @return connection to database.
     * @throws SQLException if connection is not established
     */
    public static Connection getConnection() throws SQLException {

        try {
            Class.forName(JDBC_DRIVER);
        }
        catch (ClassNotFoundException e) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
        }

        connection = DriverManager.getConnection(PATH, USER, PASS);
        return connection;
    }
    /**
     * Method for closing connection to database.
     *
     * @throws SQLException if connection is not closed
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }


}



