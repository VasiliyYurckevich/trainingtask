package com.qulix.yurkevichvv.trainingtask.wicket.pages.task;

import java.util.List;

import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
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
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityForm;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.DateValidator;

/**
 * Страница добавления редактирования задач.
 *
 * @author Q-YVV
 */
public abstract class AbstractTaskPage extends AbstractEntityPage<Task> {

    /**
     * Максимальная длинна ввода полей.
     */
    private static final int MAXLENGTH = 50;

    /**
     * Формат дат.
     */
    private static final String DATA_FORMAT = "yyyy-MM-dd";

    /**
     * Идентификатор поля наименования.
     */
    private static final String TITLE = "title";

    /**
     * Конструктор.
     *
     * @param taskModel модель задачи
     */
    public AbstractTaskPage(CompoundPropertyModel<Task> taskModel, AbstractEntityForm<Task> form) {
        super("Редактировать задачу", taskModel, form);
    }

    /**
     * Добавляет поле даты начала работы в форму задачи.
     */
    private void addDateFields() {
        LocalDateTextField beginDateField = new LocalDateTextField("beginDate", DATA_FORMAT);
        getForm().add(beginDateField);

        FeedbackPanel beginDateFeedbackPanel = new FeedbackPanel("beginDateFeedbackPanel",
            new ComponentFeedbackMessageFilter(beginDateField));
        getForm().add(beginDateFeedbackPanel);

        LocalDateTextField endDateTextField = new LocalDateTextField("endDate", DATA_FORMAT);
        getForm().add(endDateTextField);

        FeedbackPanel endDateFeedbackPanel = new FeedbackPanel("endDateFeedbackPanel",
            new ComponentFeedbackMessageFilter(endDateTextField));
        getForm().add(endDateFeedbackPanel);

        getForm().add(new DateValidator(beginDateField, endDateTextField));
    }

    /**
     * Добавляет поле времени работы в форму задачи.
     */
    private void addWorkTimeField() {
        RequiredTextField<Integer> workTimeField = new RequiredTextField<>("workTime");
        workTimeField.add(new RangeValidator<>(0, Integer.MAX_VALUE));
        getForm().add(workTimeField);

        FeedbackPanel workTimeFeedbackPanel = new FeedbackPanel("workTimeFeedbackPanel",
            new ComponentFeedbackMessageFilter(workTimeField));
        getForm().add(workTimeFeedbackPanel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        addFormComponents();
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
    protected void addFormComponents() {
        addButtons();
        addStatusesDropDownChoice();
        addStringField(TITLE, MAXLENGTH);
        addWorkTimeField();
        addDateFields();
        addProjectDropDownChoice();
        addEmployeesDropDownChoice();
        super.addFormComponents();
    }

    /**
     * Добавляет выпадающий список статусов в форму задачи.
     */
    private void addStatusesDropDownChoice() {
        DropDownChoice<Status> statusesDropDownChoice =
            new DropDownChoice<>("statuses", new PropertyModel<>(getEntityModel(), "status"),
            List.of(Status.values()), new ChoiceRenderer<>("statusTitle"));

        statusesDropDownChoice.setRequired(true);

        FeedbackPanel statusesFeedbackPanel = new FeedbackPanel("statusesFeedbackPanel",
            new ComponentFeedbackMessageFilter(statusesDropDownChoice));
        getForm().add(statusesFeedbackPanel);
        getForm().add(statusesDropDownChoice);
    }

    /**
     * Добавляет выпадающий список сотрудников в форму задачи.
     */
    private void addEmployeesDropDownChoice() {

        LoadableDetachableModel<List<Employee>> employees = LoadableDetachableModel.of(() -> new EmployeeService().findAll());
        LambdaChoiceRenderer<Employee> employeeChoiceRenderer = new LambdaChoiceRenderer<>(Employee::getFullName,
            Employee::getId);
        EmployeeDropDownModel employeeDropDownModel = new EmployeeDropDownModel(employees);

        DropDownChoice<Employee> employeesDropDownChoice = new DropDownChoice<>("employeeId", employeeDropDownModel,
            employees, employeeChoiceRenderer);
        employeesDropDownChoice.setNullValid(true);

        getForm().add(employeesDropDownChoice);

        FeedbackPanel employeesFeedbackPanel = new FeedbackPanel("employeesFeedbackPanel",
            new ComponentFeedbackMessageFilter(employeesDropDownChoice));
        getForm().add(employeesFeedbackPanel);
    }


    /**
     * Добавляет выпадающий список проектов в форму задачи.
     */
    private void addProjectDropDownChoice() {
        LoadableDetachableModel<List<Project>> projects = LoadableDetachableModel.of(() -> new ProjectService().findAll());
        ProjectDropDownModel model = new ProjectDropDownModel(projects);

        DropDownChoice<Project> projectDropDownChoice = new DropDownChoice<>("projectId", model,
            projects, new ChoiceRenderer<>(TITLE));
        projectDropDownChoice.setRequired(true).setEnabled(changeProjectOption());
        getForm().add(projectDropDownChoice);

        FeedbackPanel projectFeedbackPanel = new FeedbackPanel("projectFeedbackPanel",
            new ComponentFeedbackMessageFilter(projectDropDownChoice));
        getForm().add(projectFeedbackPanel);
    }

    /**
     * Представляет модель списка сотрудников для выпадающего списка.
     *
     * @author Q-YVV
     */
    private class EmployeeDropDownModel implements IModel<Employee> {

        /**
         * Список сотрудников.
         */
        private final LoadableDetachableModel<List<Employee>> list;

        /**
         * Конструктор.
         *
         * @param list список сотрудников
         */
        public EmployeeDropDownModel(LoadableDetachableModel<List<Employee>> list) {
            this.list = list;
        }

        @Override
        public Employee getObject() {
            for (Employee employee : list.getObject()) {
                if (employee.getId().equals(getEntityModel().getObject().getEmployeeId())) {
                    return employee;
                }
            }
            return null;
        }

        @Override
        public void setObject(Employee employee) {
            getEntityModel().getObject().setEmployeeId(employee != null ? employee.getId() : null);
        }
    }

    /**
     * Представляет модель списка проектов для выпадающего списка.
     *
     * @author Q-YVV
     */
    private class ProjectDropDownModel implements IModel<Project> {

        /**
         * Список проектов.
         */
        private final LoadableDetachableModel<List<Project>> list;

        /**
         * Конструктор.
         *
         * @param list список проектов
         */
        public ProjectDropDownModel(LoadableDetachableModel<List<Project>> list) {
            this.list = list;
        }

        @Override
        public Project getObject() {
            for (Project project : list.getObject()) {
                if (project.getId().equals(getEntityModel().getObject().getProjectId())) {
                    return project;
                }
            }
            return null;
        }

        @Override
        public void setObject(Project project) {
            getEntityModel().getObject().setProjectId(project.getId());
        }
    }

}
