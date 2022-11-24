package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.LambdaChoiceRenderer;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Status;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.TaskService;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.NoDoubleClickButton;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.PreventSubmitOnEnterBehavior;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.DateValidator;

/**
 * Страница добавления/редактирования задач.
 *
 * @author Q-YVV
 */
public class TaskPage extends AbstractEntityPage {

    /**
     * Идентификатор элемента формы.
     */
    private static final String TASK_FORM = "taskForm";

    /**
     * Максимальная длинна ввода полей.
     */
    private static final int MAXLENGTH = 50;

    /**
     * Идентификатор поля начала работы.
     */
    private static final String BEGIN_DATE = "beginDate";

    /**
     * Паттерн для дат.
     */
    private static final String PATTERN = "yyyy-MM-dd";

    /**
     * Идентификатор поля начала работы.
     */
    private static final String END_DATE = "endDate";

    /**
     * Имя страницы редактирования задачи в проекте.
     */
    private static final String EDIT_TASK = "Редактировать задачу";

    /**
     * Идентификатор поля наименования.
     */
    private static final String TITLE = "title";

    /**
     * Модель задачи.
     */
    private IModel<Task> task;

    /**
     * Сервис для работы с Task.
     */
    protected TaskService taskService = new TaskService();

    /**
     * Конструктор.
     *
     * @param task задача
     */
    public TaskPage(IModel<Task> task) {
        super();
        this.task = task;
    }

    public IModel<Task> getTaskModel() {
        return task;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        get("pageTitle").setDefaultModelObject(EDIT_TASK);

        Form<Task> form = new Form<>(TASK_FORM, new CompoundPropertyModel<>(task)) {
            @Override
            protected void onSubmit() {
                onSubmitting();
                onChangesSubmitted();
            }
        };

        addFormComponents(form);
        add(form);
    }

    @Override
    protected void onSubmitting() {
    }

    @Override
    protected void onChangesSubmitted() {
    }

    /**
     * Определяет возможность изменения задачи проекта.
     *
     * @return true если изменение возможно, иначе false
     */
    protected boolean changeProjectOption() {
        return true;
    }

    @Override
    protected void addFormComponents(Form form) {
        addButtons(form);
        addStatusesDropDownChoice(form);
        addStringField(form, TITLE, MAXLENGTH);
        addWorkTimeField(form);
        addDateFields(form);
        addProjectDropDownChoice(form);
        addEmployeesDropDownChoice(form);
    }

    /**
     * Добавляет кнопки.
     *
     * @param form форма для добавления
     */
    private void addButtons(Form<Task> form) {
        NoDoubleClickButton button = new NoDoubleClickButton("submit");
        form.add(button);
        form.setDefaultButton(button);

        form.add(new PreventSubmitOnEnterBehavior());

        Link<Void> cancelButton = new Link<>("cancel") {
            @Override
            public void onClick() {
                onChangesSubmitted();
            }
        };
        form.add(cancelButton);
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

        FeedbackPanel employeesFeedbackPanel = new FeedbackPanel("statusesFeedbackPanel",
            new ComponentFeedbackMessageFilter(statusesDropDownChoice));
        form.add(employeesFeedbackPanel);
        form.add(statusesDropDownChoice);
    }

    /**
     * Добавляет поле времени работы в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addWorkTimeField(Form<Task> form) {
        RequiredTextField<Integer> workTimeField = new RequiredTextField<>("workTime");
        workTimeField.add(new RangeValidator<>(0, Integer.MAX_VALUE));
        form.add(workTimeField);

        FeedbackPanel workTimeFeedbackPanel = new FeedbackPanel("workTimeFeedbackPanel",
            new ComponentFeedbackMessageFilter(workTimeField));
        form.add(workTimeFeedbackPanel);
    }

    /**
     * Добавляет поле даты начала работы в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addDateFields(Form<Task> form) {
        LocalDateTextField beginDateField = new LocalDateTextField(BEGIN_DATE, PATTERN);
        form.add(beginDateField.setRequired(true));
        beginDateField.setRequired(true);

        FeedbackPanel beginDateFeedbackPanel = new FeedbackPanel("beginDateFeedbackPanel",
            new ComponentFeedbackMessageFilter(beginDateField));
        form.add(beginDateFeedbackPanel);

        LocalDateTextField endDateTextField = new LocalDateTextField(END_DATE,
            new PropertyModel<>(form.getModelObject(), END_DATE), PATTERN);
        endDateTextField.setRequired(true);
        form.add(endDateTextField);

        FeedbackPanel endDateFeedbackPanel = new FeedbackPanel("endDateFeedbackPanel",
            new ComponentFeedbackMessageFilter(endDateTextField));
        form.add(endDateFeedbackPanel);

        form.add(new DateValidator(beginDateField, endDateTextField));
    }

    /**
     * Добавляет выпадающий список проектов в форму задачи.
     *
     * @param form форма для добавления
     */
    private void addProjectDropDownChoice(Form<Task> form) {
        List<Project> projects = new ProjectService().findAll();
        ProjectIModel model = new ProjectIModel(projects, form.getModelObject());

        DropDownChoice<Project> projectDropDownChoice = new DropDownChoice<>("projectId", model,
            projects, new ChoiceRenderer<>(TITLE));
        projectDropDownChoice.setRequired(true).setEnabled(changeProjectOption());
        form.add(projectDropDownChoice);

        FeedbackPanel workTimeFeedbackPanel = new FeedbackPanel("projectFeedbackPanel",
            new ComponentFeedbackMessageFilter(projectDropDownChoice));
        form.add(workTimeFeedbackPanel);
    }

    /**
     * Добавляет выпадающий список сотрудников в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addEmployeesDropDownChoice(Form<Task> form) {

        List<Employee> employees = new EmployeeService().findAll();
        LambdaChoiceRenderer<Employee> employeeChoiceRenderer = new LambdaChoiceRenderer<>(Employee::getFullName,
            Employee::getId);
        EmployeeIModel model = new EmployeeIModel(employees, form.getModelObject());

        DropDownChoice<Employee> employeesDropDownChoice = new DropDownChoice<>("employeeId" , model, employees,
            employeeChoiceRenderer);
        employeesDropDownChoice.setNullValid(true);
        form.add(employeesDropDownChoice);

        FeedbackPanel employeesFeedbackPanel = new FeedbackPanel("employeesFeedbackPanel",
            new ComponentFeedbackMessageFilter(employeesDropDownChoice));
        form.add(employeesFeedbackPanel);
    }

    /**
     * Представляет модель списка сотрудников для выпадающего списка.
     *
     * @author Q-YVV
     */
    static class EmployeeIModel implements IModel<Employee> {

        /**
         * Список сотрудников.
         */
        private final List<Employee> list;

        /**
         * Редактируемая задача.
         */
        private final Task task;

        /**
         * Конструктор.
         *
         * @param list список сотрудников
         * @param task редактируемая задача
         */
        public EmployeeIModel(List<Employee> list, Task task) {
            this.list = list;
            this.task = task;
        }

        @Override
        public Employee getObject() {
            for (Employee employee : list) {
                if (employee.getId().equals(task.getEmployeeId())) {
                    return employee;
                }
            }
            return null;
        }

        @Override
        public void setObject(final Employee employee) {
            task.setEmployeeId(employee != null ? employee.getId() : null);
        }
    }

    /**
     * Представляет модель списка проектов для выпадающего списка.
     *
     * @author Q-YVV
     */
    static class ProjectIModel implements IModel<Project> {

        /**
         * Список проектов.
         */
        private final List<Project> list;

        /**
         * Редактируемая задача.
         */
        private final Task task;

        /**
         * Конструктор.
         *
         * @param list список проектов
         * @param task редактируемая задача
         */
        public ProjectIModel(List<Project> list, Task task) {
            this.list = list;
            this.task = task;
        }

        @Override
        public Project getObject() {
            for (Project project : list) {
                if (project.getId().equals(task.getProjectId())) {
                    return project;
                }
            }
            return null;
        }

        @Override
        public void setObject(final Project project) {
            task.setProjectId(project.getId());
        }
    }
}
