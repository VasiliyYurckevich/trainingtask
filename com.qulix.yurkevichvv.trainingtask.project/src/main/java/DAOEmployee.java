import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for working with database table "employee".
 *<p> {@link DAOEmployee} using for write data about employee in database.</p>
 *
 *
 * @author  Q-YVV
 * @version 1.0
 * @since   1.0
 */
public class DAOEmployee implements DAOInterface<Employee> {

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final int FOUR = 4;

    private static final int FIVE = 5;


    private static final String EMPLOYEE_ID = "employee_id";
    private static final String SURNAME = "surname";

    private static final String FIRST_NAME = "first_name";

    private static final String PATRONYMIC = "patronymic";

    private static final String POST = "post";


    private static final  String INSERT_EMPLOYEE_SQL = "INSERT INTO EMPLOYEE (surname, first_name, patronymic, post)" +
        " VALUES (?,?,?,?);";

    private static final String SELECT_ALL_CLIENT = "SELECT * FROM EMPLOYEE;";

    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM EMPLOYEE WHERE employee_id = ?;";

    private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM EMPLOYEE WHERE employee_id = ?;";

    private static final String UPDATE_CLIENT_SQL = "UPDATE EMPLOYEE SET surname = ?, first_name = ?, patronymic = ?, post = ?" +
        " WHERE employee_id = ?;";


    /**
     * Method for writing new employee to database.
     */
    @Override
    public boolean add(Employee employee) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL);
            preparedStatement.setString(ONE, employee.getSurname());
            preparedStatement.setString(TWO, employee.getFirstName());
            preparedStatement.setString(THREE, employee.getPatronymic());
            preparedStatement.setString(FOUR, employee.getPost());

            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method for rewriting  employee to database.
     */
    @Override
    public boolean update(Employee employee) throws SQLException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_SQL);
            preparedStatement.setString(ONE, employee.getSurname());
            preparedStatement.setString(TWO, employee.getFirstName());
            preparedStatement.setString(THREE, employee.getPatronymic());
            preparedStatement.setString(FOUR, employee.getPost());
            preparedStatement.setInt(FIVE, employee.getId());
            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method for deleting employee from database.
     */

    @Override
    public boolean delete(Integer id) throws SQLException {

        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_SQL);
            preparedStatement.setInt(ONE, id);

            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method for getting all employees from database.
     */
    @Override
    public List<Employee> getAll() throws SQLException {

        Connection connection = DBConnection.getConnection();

        try {
            List<Employee> employees = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(SELECT_ALL_CLIENT);

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt(EMPLOYEE_ID));
                employee.setSurname(resultSet.getString(SURNAME));
                employee.setFirstName(resultSet.getString(FIRST_NAME));
                employee.setPatronymic(resultSet.getString(PATRONYMIC));
                employee.setPost(resultSet.getString(POST));
                employees.add(employee);
            }
            return employees;
        }
        finally {
            DBConnection.closeConnection();
        }
    }

    /**
     * Method for getting employee by id.
     */
    @Override
    public Employee getById(Integer  id) throws SQLException {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);
            try {
                preparedStatement.setInt(ONE, id);
            }
            catch (NullPointerException e) {
                preparedStatement.setNull(ONE, 0);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            Employee employee = new Employee();
            while (resultSet.next()) {
                if (resultSet.getInt(EMPLOYEE_ID) != 0) {
                    employee.setId(resultSet.getInt(EMPLOYEE_ID));
                }
                else {
                    employee.setId(null);
                }
                employee.setSurname(resultSet.getString(SURNAME));
                employee.setFirstName(resultSet.getString(FIRST_NAME));
                employee.setPatronymic(resultSet.getString(PATRONYMIC));
                employee.setPost(resultSet.getString(POST));
            }
            return employee;
        }
        finally {

            DBConnection.closeConnection();
        }
    }
}
