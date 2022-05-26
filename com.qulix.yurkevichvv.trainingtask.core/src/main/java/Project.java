/**
 * Class Project describes project.
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
 */
public class Project {

    private int id;

    private String title;

    private String description;

    /**
     * Конструктор.
     */
    public Project() {
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
