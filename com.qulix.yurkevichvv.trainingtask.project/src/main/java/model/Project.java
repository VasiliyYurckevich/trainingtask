package model;



/**
 * Class Project describes project.
 *
 *<h2>Project class fields:</h2>
 * <ul>
 *     <li>id</li>
 *     <li>title</li>
 *     <li>description</li>
 * </ul>
 *
 *<h2>Project class constructors:</h2>
 * <ul>
 *     <li>Project()</li>
 *     <li>Project(int id, String title, String description)</li>
 *     <li>Project(String title, String description)</li>
 * </ul>
 *
 * <h2>Project class methods:</h2>
 * <ul>
 *     <li>getId()</li>
 *     <li>getTitle()</li>
 *     <li>getDescription()</li>
 *     <li>setId(int id)</li>
 *     <li>setTitle(String title)</li>
 *     <li>setDescription(String description)</li>
 *     <li>toString()</li>
 *</ul>
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings ("checkstyle:JavadocVariable")
public class Project {

    protected int id;
    protected String title;
    protected String description;

    /**
     * Constructor without parameters.
     */
    public Project() {
    }

    /**
     * Project constructor with parameters.
     * @param title project title
     * @param description project description
     */
    public Project(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /**
     * Project constructor with parameters.
     * @param id project id
     * @param title project title
     * @param description project description
     */
    public Project(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Project id=" + id + ", title=" + title + ", description=" + description;
    }

}
