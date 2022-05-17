package model;


import java.time.LocalDate;

/**
 * Class Task represents a task.
 *
 * @author Q-YVV
 * @version 1.0
 * @since 1.0
 */
public class Task {

    protected Integer taskId;
    protected String status;
    protected String title;
    protected long workTime;
    protected LocalDate beginDate;
    protected LocalDate endDate;
    protected Integer projectId;
    protected Integer employeeId;

    /**
     * Constructor without parameters.

     */
    public Task() {
    }

    /**
     * Constructor with parameters.
     *
     * @param status task's status
     * @param title task's title
     * @param workTime task's work time
     * @param beginDate task's begin date
     * @param endDate task's end date
     * @param projectId task's project id
     * @param employeeId task's employee id
     */

    public Task(String status, String title, long workTime, LocalDate beginDate, LocalDate endDate,
        Integer projectId, Integer employeeId) {
        this.status = status;
        this.title = title;
        this.workTime = workTime;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.employeeId = employeeId;
    }

    /**
     * Constructor with parameters.
     *
     * @param taskId task's id
     * @param status task's status
     * @param title task's title
     * @param workTime task's work time
     * @param beginDate task's begin date
     * @param endDate task's end date
     * @param projectId task's project id
     * @param employeeId task's employee id
     */
    @SuppressWarnings ("checkstyle:ParameterNumber")
    public Task(Integer taskId, String status, String title, long workTime,
        LocalDate beginDate, LocalDate endDate, Integer projectId, Integer employeeId) {
        this.taskId = taskId;
        this.status = status;
        this.title = title;
        this.workTime = workTime;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.projectId = projectId;
        this.employeeId = employeeId;
    }

    public Integer getId() {
        return taskId;
    }

    public void setId(Integer id) {
        this.taskId = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime = workTime;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Task " +
                "taskId=" + taskId +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", workTime=" + workTime +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", project_id=" + projectId +
                ", employee_id=" + employeeId;
    }
}
