package model;

import static utilits.Util.htmlSpecialChars;

/**
 * Class Employee describes employee.
 *
 * @author Yurkevichvv
 * @version 1.0
 */
public class Employee {
    // Fields
    protected int id;
    protected String surname;
    protected String firstName;
    protected String patronymic;
    protected String post;

    // Constructors
    public Employee() {
    }

    public Employee(int id, String surname, String firstName, String patronymic, String post) {
        super();
        this.id = id;
        this.surname = htmlSpecialChars(surname);
        this.firstName = htmlSpecialChars(firstName);
        this.patronymic = htmlSpecialChars(patronymic);
        this.post = htmlSpecialChars(post);
    }

    public Employee(String surname, String firstName, String patronymic, String post) {
        super();
        this.surname = htmlSpecialChars(surname);
        this.firstName = htmlSpecialChars(firstName);
        this.patronymic = htmlSpecialChars(patronymic);
        this.post = htmlSpecialChars(post);
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
