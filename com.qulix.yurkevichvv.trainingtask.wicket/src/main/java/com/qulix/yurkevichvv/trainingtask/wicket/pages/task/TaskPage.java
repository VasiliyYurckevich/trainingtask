package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.LambdaChoiceRenderer;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.qulix.yurkevichvv.trainingtask.model.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Employee;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Status;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.NoDoubleClickButton;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.PreventSubmitOnEnterBehavior;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.DateValidator;

/**
 * Страница добавления/редактирования задач.
 *
 * @author Q-YVV
 */
public class TaskPage extends AbstractEntityPage<Task> {

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
     * Имя страницы редактирования задачи в проекте.
     */
    public static final String EDIT_TASK = "Редактировать задачу";
    /**
     * Идентификатор поля наименования.
     */
    public static final String TITLE = "title";
    private Task task;

    /**
     * Конструктор.
     *
     * @param task задача
     */
    public TaskPage(Task task) {
       super();
       this.task = task;
    }

    @Override
    public void setEntity(Task task) {
        this.task = task;
    }

    @Override
    protected void onSubmitting() {
    }

    @Override
    protected void onChangesSubmitted() {
    }

    public Task getTask() {
        return task;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        get(PAGE_TITLE).setDefaultModelObject(EDIT_TASK);
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

    /**
     * Добавляет компоненты в форму задачи.
     *
     * @param form форма для добавления
     */
    private void addFormComponents(Form<Task> form) {
        addButtons(form);
        addStatusesDropDownChoice(form);
        addTitleField(form);
        addWorkTimeField(form);
        addDateField(form);
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
                setResponsePage(TasksListPage.class);
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

        RequiredTextField<String> titleField =  new RequiredTextField<String>(TITLE);
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
        workTimeField.add(new RangeValidator<>(0, Integer.MAX_VALUE));
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
        LocalDateTextField beginDateField =  new LocalDateTextField(BEGIN_DATE, PATTERN);
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

        form.add(new DateValidator(beginDateField, endDateTextField));
    }

    /**
     * Добавляет выпадающий список проектов в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addProjectDropDownChoice(Form<Task> form) {
        List<Project> projects = new ProjectDao().getAll();
        ProjectIModel model = new ProjectIModel(projects, form.getModelObject());

        DropDownChoice<Project> projectDropDownChoice = new DropDownChoice<>("projectId" , model, projects,
            new ChoiceRenderer<>(TITLE));
        projectDropDownChoice.setRequired(true);
        form.add(projectDropDownChoice);

        CustomFeedbackPanel workTimeFeedbackPanel = new CustomFeedbackPanel("projectFeedbackPanel",
            new ComponentFeedbackMessageFilter(projectDropDownChoice));
        form.add(workTimeFeedbackPanel);
    }

    /**
     * Добавляет выпадающий список сотрудников в форму задачи.
     *
     * @param form форма для добавления
     */
    private static void addEmployeesDropDownChoice(Form<Task> form) {
        List<Employee> employees = new EmployeeDao().getAll();
        LambdaChoiceRenderer<Employee> employeeChoiceRenderer = new LambdaChoiceRenderer<>(Employee::getFullName,
            Employee::getId);

        EmployeeIModel model = new EmployeeIModel(employees, form.getModelObject());
        DropDownChoice<Employee> employeesDropDownChoice = new DropDownChoice<>("employeeId" , model, employees,
            employeeChoiceRenderer);
        form.add(employeesDropDownChoice);

        employeesDropDownChoice.setNullValid(true);
        CustomFeedbackPanel employeesFeedbackPanel = new CustomFeedbackPanel("employeesFeedbackPanel",
            new ComponentFeedbackMessageFilter(employeesDropDownChoice));
        form.add(employeesFeedbackPanel);
    }
}
