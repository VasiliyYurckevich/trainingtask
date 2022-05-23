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
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL);

        try {
            preparedStatement.setString(Nums.ONE.getValue(), employee.getSurname());
            preparedStatement.setString(Nums.TWO.getValue(), employee.getFirstName());
            preparedStatement.setString(Nums.THREE.getValue(), employee.getPatronymic());
            preparedStatement.setString(Nums.FOUR.getValue(), employee.getPost());

            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection(preparedStatement);
        }
    }

    /**
     * Method for rewriting  employee to database.
     */
    @Override
    public boolean update(Employee employee) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_SQL);
        try {
            preparedStatement.setString(Nums.ONE.getValue(), employee.getSurname());
            preparedStatement.setString(Nums.TWO.getValue(), employee.getFirstName());
            preparedStatement.setString(Nums.THREE.getValue(), employee.getPatronymic());
            preparedStatement.setString(Nums.FOUR.getValue(), employee.getPost());
            preparedStatement.setInt(Nums.FIVE.getValue(), employee.getId());
            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection(preparedStatement);
        }
    }

    /**
     * Method for deleting employee from database.
     */

    @Override
    public boolean delete(Integer id) throws SQLException {

        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_SQL);

        try {
            preparedStatement.setInt(Nums.ONE.getValue(), id);

            return preparedStatement.execute();
        }
        finally {
            DBConnection.closeConnection(preparedStatement);
        }
    }

    /**
     * Method for getting all employees from database.
     */
    @Override
    public List<Employee> getAll() throws SQLException {

        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLIENT);

        try {
            List<Employee> employees = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

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
            DBConnection.closeConnection(preparedStatement);
        }
    }

    /**
     * Method for getting employee by id.
     */
    @Override
    public Employee getById(Integer  id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);
        try {
            try {
                preparedStatement.setInt(Nums.ONE.getValue(), id);
            }
            catch (NullPointerException e) {
                preparedStatement.setNull(Nums.ONE.getValue(), 0);
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
            DBConnection.closeConnection(preparedStatement);
        }
    }
}
