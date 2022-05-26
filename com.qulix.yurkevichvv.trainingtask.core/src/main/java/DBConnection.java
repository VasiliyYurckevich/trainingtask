import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Устанавливет контакт между приложением и БД.
 *
 * @author Q-YVV
 */
public class DBConnection {


    private static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    private static final String PATH = "jdbc:hsqldb:hsql://localhost/mydb;ifexists=true;sql.syntax_mys=true";

    private static final String USER = "SA";

    private static final String PASS = "";

    private static Connection connection = null;


    /**
     * Method for getting connection to database.
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
     */
    public static void closeConnection(PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement != null && !preparedStatement.isClosed()) {
            preparedStatement.close();
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}



