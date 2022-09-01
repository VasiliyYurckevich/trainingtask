package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import com.qulix.yurkevichvv.trainingtask.servlets.connection.ConnectionController;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.servlets.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Project;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Status;
import com.qulix.yurkevichvv.trainingtask.servlets.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.lists.TasksListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.EditProjectPage;
import com.qulix.yurkevichvv.trainingtask.wicket.panels.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.DateLogicValidator;

/**
 * Страница добавления/редактирования задач.
 *
 * @author Q-YVV
 */
public class TaskPage extends BasePage {

    /**
     * Идентификатор элемента названия страницы.
     */
    public static final String PAGE_TITLE = "pageTitle";

    /**
     * Идентификатор элемента формы.
     */
    public static final String TASK_FORM = "taskForm";

    /**
     * Идентификатор проекта.
     */
    public static final String PROJECTS = "projects";

    /**
     * Максимальная длинна ввода полей.
     */
    public static final int MAXLENGTH = 50;

    /**
     * Идентификатор поля начала работы.
     */
    public static final String BEGIN_DATE = "beginDate";

    /**
     * Паттерн для дат.
     */
    public static final String PATTERN = "yyyy-MM-dd";

    /**
     * Идентификатор поля начала работы.
     */
    public static final String END_DATE = "endDate";


    /**
     * Конструктор.
     */
    public TaskPage() {
        get(PAGE_TITLE).setDefaultModelObject("Добавить задачу");
        Form<Task> taskForm = new Form<Task>(TASK_FORM, new CompoundPropertyModel<>(new Task())) {
            @Override
            protected void onSubmit() {
                TaskDao taskDao = new TaskDao();
                taskDao.add(getModelObject(), ConnectionController.getConnection());
                setResponsePage(TasksListPage.class);
            }
        };
        addFormComponents(taskForm);
        add(taskForm);
    }

    /**
     * Конструктор.
     *
     * @param project проект, к которому привязана задача
     * @param projectTasks список задач проекта
     */
    public TaskPage(Project project, List<Task> projectTasks) {
        get(PAGE_TITLE).setDefaultModelObject("Добавить задачу в проект " + project.getTitle());
        Form<Task> taskForm = new Form<>(TASK_FORM, new CompoundPropertyModel<>(new Task())) {
            @Override
            protected void onSubmit() {
                projectTasks.add(getModelObject());
                setResponsePage(new EditProjectPage(project, projectTasks));
            }
        };
        addFormComponents(taskForm);
        taskForm.get(PROJECTS).setDefaultModelObject(project.getId());
        taskForm.get(PROJECTS).setEnabled(false);
        add(taskForm);
    }

    /**
     * Конструктор.
     *
     * @param project проект, к которому привязана задача
     * @param projectTasks список задач проекта
     * @param taskId индекс задачи в projectTasks
     */
    public TaskPage(Project project, List<Task> projectTasks, int taskId) {
        get(PAGE_TITLE).setDefaultModelObject("Редактировать задачу в проекте " + project.getTitle());
        Task task = projectTasks.get(taskId);
        Form<Task> taskForm = new Form<>(TASK_FORM, new CompoundPropertyModel<>(task)) {
            @Override
            protected void onSubmit() {
                projectTasks.set(taskId, getModelObject());
                setResponsePage(new EditProjectPage(project, projectTasks));
            }
        };
        addFormComponents(taskForm);
        taskForm.get(PROJECTS).setDefaultModelObject(project.getId());
        taskForm.get(PROJECTS).setEnabled(false);
        add(taskForm);
    }

    /**
     * Конструктор.
     *
     * @param task редактируемая задача
     */
    public TaskPage(Task task) {
        get(PAGE_TITLE).setDefaultModelObject("Редактировать задачу");
        Form<Task> taskForm = new Form<Task>(TASK_FORM, new CompoundPropertyModel<>(task)) {
            @Override
            protected void onSubmit() {
                TaskDao taskDao = new TaskDao();
                taskDao.update(getModelObject(), ConnectionController.getConnection());
                setResponsePage(TasksListPage.class);
            }
        };
        addFormComponents(taskForm);
        add(taskForm);
    }

    /**
     * Добавляет компоненты в форму задачи.
     *
     * @param form форма для добавления
     */
    private void addFormComponents(Form<Task> form) {
        Link<Void> cancelButton = new Link<>("cancel") {
            @Override
            public void onClick() {
                setResponsePage(TasksListPage.class);
            }
        };
        form.add(cancelButton);
        addStatusesDropDownChoice(form);
        addTitleField(form);
        addWorkTimeField(form);
        addDateField(form);
        addProjectDropDownChoice(form);
        addEmployeesDropDownChoice(form);
    }

    /**
     * Добавляет выпадающий список статусов в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addStatusesDropDownChoice(Form<Task> form) {
        DropDownChoice statusesDropDownChoice =
            new DropDownChoice("statuses", new PropertyModel<>(form.getModelObject(), "status"),
            List.of(Status.values()), new ChoiceRenderer<Status>("statusTitle"));
        statusesDropDownChoice.setRequired(true);
        CustomFeedbackPanel employeesFeedbackPanel = new CustomFeedbackPanel("statusesFeedbackPanel",
            new ComponentFeedbackMessageFilter(statusesDropDownChoice));
        form.add(employeesFeedbackPanel);
        form.add(statusesDropDownChoice);
    }

    /**
     * Добавляет поле наименования в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addTitleField(Form<Task> form) {
        RequiredTextField<String> titleField =  new RequiredTextField<String>("title");
        titleField.add(new CustomStringValidator(MAXLENGTH));
        form.add(titleField);
        CustomFeedbackPanel titleFeedbackPanel = new CustomFeedbackPanel("titleFeedbackPanel",
            new ComponentFeedbackMessageFilter(titleField));
        form.add(titleFeedbackPanel);
    }

    /**
     * Добавляет поле времени работы в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addWorkTimeField(Form<Task> form) {
        RequiredTextField<Integer> workTimeField = new RequiredTextField<>("workTime");
        form.add(workTimeField);
        CustomFeedbackPanel workTimeFeedbackPanel = new CustomFeedbackPanel("workTimeFeedbackPanel",
            new ComponentFeedbackMessageFilter(workTimeField));
        form.add(workTimeFeedbackPanel);
    }


    /**
     * Добавляет поле даты начала работы в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addDateField(Form<Task> form) {
        LocalDateTextField beginDateField =  new LocalDateTextField(BEGIN_DATE,
            new PropertyModel<>(form.getModelObject(), BEGIN_DATE), PATTERN);
        form.add(beginDateField);
        beginDateField.setRequired(true);
        CustomFeedbackPanel beginDateFeedbackPanel = new CustomFeedbackPanel("beginDateFeedbackPanel",
            new ComponentFeedbackMessageFilter(beginDateField));
        form.add(beginDateFeedbackPanel);

        LocalDateTextField endDateTextField = new LocalDateTextField(END_DATE,
            new PropertyModel<>(form.getModelObject(), END_DATE), PATTERN);
        endDateTextField.setRequired(true);
        form.add(endDateTextField);
        CustomFeedbackPanel endDateFeedbackPanel = new CustomFeedbackPanel("endDateFeedbackPanel",
            new ComponentFeedbackMessageFilter(endDateTextField));
        form.add(endDateFeedbackPanel);

        form.add(new DateLogicValidator(beginDateField, endDateTextField));
    }

    /**
     * Добавляет выпадающий список проектов в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addProjectDropDownChoice(Form<Task> form) {
        ProjectDao projectDao = new ProjectDao();
        DropDownChoice projectDropDownChoice = new DropDownChoice<Integer>(PROJECTS,
            new PropertyModel(form.getModelObject(), "projectId"),
            projectDao.getAll().stream().map(Project::getId).collect(Collectors.toList()), new ChoiceRenderer<>() {
                @Override
                public String getDisplayValue(Integer id) {
                    return projectDao.getAll().stream().filter(project ->
                        id.equals(project.getId())).findFirst().orElse(null).getTitle();
                }
            });
        projectDropDownChoice.setRequired(true);
        CustomFeedbackPanel workTimeFeedbackPanel = new CustomFeedbackPanel("projectFeedbackPanel",
            new ComponentFeedbackMessageFilter(projectDropDownChoice));
        form.add(workTimeFeedbackPanel);
        form.add(projectDropDownChoice);
    }

    /**
     * Добавляет выпадающий список сотрудников в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addEmployeesDropDownChoice(Form<Task> form) {
        EmployeeDao employeeDao = new EmployeeDao();
        DropDownChoice<Integer> employeesDropDownChoice = new DropDownChoice<Integer>("employees",
            new PropertyModel(form.getModelObject(), "employeeId"),
            employeeDao.getAll().stream().map(Employee::getId).collect(Collectors.toList()), new ChoiceRenderer<Integer>() {
                @Override
                public String getDisplayValue(Integer id) {
                    return employeeDao.getAll().stream().filter(employee ->
                            id.equals(employee.getId())).findFirst().orElse(null).getFullName();

                }
            });
        employeesDropDownChoice.setNullValid(true);
        CustomFeedbackPanel employeesFeedbackPanel = new CustomFeedbackPanel("employeesFeedbackPanel",
            new ComponentFeedbackMessageFilter(employeesDropDownChoice));
        form.add(employeesFeedbackPanel);
        form.add(employeesDropDownChoice);
    }
}
