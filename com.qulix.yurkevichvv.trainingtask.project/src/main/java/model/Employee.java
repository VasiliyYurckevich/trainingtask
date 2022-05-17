package model;

/**
 * Class Employee describes employee.
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
 */
public class Employee {

    protected Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
