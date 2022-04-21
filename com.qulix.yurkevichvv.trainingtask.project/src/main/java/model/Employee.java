package model;

/**
 * Class Employee describes employee.
 *
 * <h2>Class fields:</h2>
 * <ul>
 *     <li>private String surname;</li>
 *     <li>private String name;</li>
 *     <li>private String patronymic;</li>
 *     <li>private String post;</li>
 *     <li>private int id;</li>
 * </ul>
 * <h2>Class constructors:</h2>
 * <ul>
 *     <li>public Employee(String surname, String name, String patronymic, String post);</li>
 *     <li>public Employee(int id, String surname, String name, String patronymic, String post);</li>
 *     <li>public Employee();</li>
 * </ul>
 * <h2>Class methods:</h2>
 * <ul>
 *     <li>public String getSurname();</li>
 *     <li>public String getName();</li>
 *     <li>public String getPatronymic();</li>
 *     <li>public String getPost();</li>
 *     <li>public int getId();</li>
 *     <li>public void setSurname(String surname);</li>
 *     <li>public void setName(String name);</li>
 *     <li>public void setPatronymic(String patronymic);</li>
 *     <li>public void setPost(String post);</li>
 *     <li>public void setId(int id);</li>
 *     <li>public String toString();</li>
 *</ul>
 *
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings ("checkstyle:JavadocVariable")
public class Employee {

    protected int id;
    protected String surname;
    protected String firstName;
    protected String patronymic;
    protected String post;

    /**
     * Constructor without parameters.
     */
    public Employee() {
    }

    /**
     * Constructor with parameters.
     *
     * @param id Employee id.
     * @param surname employee surname
     * @param firstName employee name
     * @param patronymic employee patronymic
     * @param post employee post
     */
    public Employee(int id, String surname, String firstName, String patronymic, String post) {
        super();
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.post = post;
    }
    /**
     * Constructor with parameters.
     *
     * @param surname employee surname
     * @param firstName employee name
     * @param patronymic employee patronymic
     * @param post employee post
     */
    public Employee(String surname, String firstName, String patronymic, String post) {
        super();
        this.surname = surname;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", surname=" + surname + ", firstName=" + firstName + ", patronymic=" + patronymic
                + ", post=" + post + "]";
    }

}
