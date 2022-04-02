package model;

import static util.Util.htmlSpecialChars;

/**
 * Class Project represents project.
 *
 * @author Yurkevichvv
 * @version 1.0
 */
public class Project {
    // Fields
    protected int id;
    protected String title;
    protected String description;
    // Constructors
    public Project() {
    }

    public Project(String title, String description) {
        this.title = htmlSpecialChars(title);
        this.description = htmlSpecialChars(description);
    }

    public Project(int id, String title, String description) {
        this.id = id;
        this.title = htmlSpecialChars(title);
        this.description = htmlSpecialChars(description);
    }
    // Getters and setters
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

    public void setDescription(String discription) {
        this.description = discription;
    }

    // Method
    @Override
    public String toString() {
        return "Project id=" + id + ", title=" + title + ", description=" + description;
    }

}