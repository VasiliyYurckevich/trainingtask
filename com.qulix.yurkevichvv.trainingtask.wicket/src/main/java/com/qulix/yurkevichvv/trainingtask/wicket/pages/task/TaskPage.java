package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;


import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.LambdaChoiceRenderer;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Status;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.DateValidator;

/**
 * Страница добавления редактирования задач.
 *
 * @author Q-YVV
 */
public class TaskPage extends AbstractEntityPage<Task> {

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
     * Идентификатор поля наименования.
     */
    private static final String TITLE = "title";

    /**
     * Конструктор.
     *
     * @param taskModel модель задачи
     */
    public TaskPage(CompoundPropertyModel<Task> taskModel) {
        super(taskModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        get("pageTitle").setDefaultModelObject("Редактировать задачу");

        Form<Task> form = new Form<>(TASK_FORM, entityModel) {
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
    protected void addFormComponents(Form<Task> form) {
        addButtons(form);
        addStatusesDropDownChoice(form);
        addStringField(form, TITLE, MAXLENGTH);
        addWorkTimeField(form);
        addDateFields(form);
        addProjectDropDownChoice(form);
        addEmployeesDropDownChoice(form);
    }

    /**
     * Добавляет выпадающий список статусов в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addStatusesDropDownChoice(Form<Task> form) {
        DropDownChoice<Status> statusesDropDownChoice =
            new DropDownChoice<>("statuses", new PropertyModel<>(form.getModelObject(), "status"),
            List.of(Status.values()), new ChoiceRenderer<>("statusTitle"));
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
        LoadableDetachableModel<List<Project>> projects = LoadableDetachableModel.of(()-> new ProjectService().findAll());
        ProjectDropDownModel model = new ProjectDropDownModel(projects, form.getModelObject());

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

        LoadableDetachableModel<List<Employee>> employees = LoadableDetachableModel.of(()-> new EmployeeService().findAll());
        LambdaChoiceRenderer<Employee> employeeChoiceRenderer = new LambdaChoiceRenderer<>(Employee::getFullName,
            Employee::getId);
        EmployeeDropDownModel model = new EmployeeDropDownModel(employees, form.getModelObject());

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
    static class EmployeeDropDownModel implements IModel<Employee> {

        /**
         * Список сотрудников.
         */
        private final LoadableDetachableModel<List<Employee>> list;

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
        public EmployeeDropDownModel(LoadableDetachableModel<List<Employee>> list, Task task) {
            this.list = list;
            this.task = task;
        }

        @Override
        public Employee getObject() {
            for (Employee employee : list.getObject()) {
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
    static class ProjectDropDownModel implements IModel<Project> {

        /**
         * Список проектов.
         */
        private final LoadableDetachableModel<List<Project>> list;

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
        public ProjectDropDownModel(LoadableDetachableModel<List<Project>> list, Task task) {
            this.list = list;
            this.task = task;
        }

        @Override
        public Project getObject() {
            for (Project project : list.getObject()) {
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
