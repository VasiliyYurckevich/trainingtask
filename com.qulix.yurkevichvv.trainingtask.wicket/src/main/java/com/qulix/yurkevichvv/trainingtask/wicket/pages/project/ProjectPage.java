package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import java.util.List;

import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;


import com.qulix.yurkevichvv.trainingtask.model.dao.EmployeeDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.ProjectDao;
import com.qulix.yurkevichvv.trainingtask.model.dao.TaskDao;
import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.CustomFeedbackPanel;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.EditInProject;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.NoDoubleClickButton;
import com.qulix.yurkevichvv.trainingtask.wicket.companents.PreventSubmitOnEnterBehavior;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.BasePage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TaskPage;
import com.qulix.yurkevichvv.trainingtask.wicket.validation.CustomStringValidator;
import org.apache.wicket.model.LoadableDetachableModel;


/**
 * Страница добавления проекта.
 *
 * @author Q-YVV
 */
public class ProjectPage extends BasePage {

    /**
     * Максимальная длинна ввода поля наименования.
     */
    public static final int TITLE_MAXLENGTH = 50;
    /**
     * Максимальная длинна ввода поля описания.
     */
    public static final int DESCRIPTION_MAXLENGTH = 250;

    /**
     * Идентификатор элемента названия страницы.
     */
    private static final String PAGE_TITLE = "pageTitle";


    /**
     * Идентификатор поля сотрудника.
     */
    private static final String EMPLOYEE_NAME = "employeeName";

    /**
     * Идентификатор формы проекта.
     */
    private static final String PROJECT_FORM = "projectForm";

    /**
     * Сообщение ошибки транзакции.
     */
    private static final String TRANSACTION_ERROR_MESSAGE = "Exception trying create transaction";
    private Project project;

    /**
     * Список задач проекта.
     */

    /**
     * Переменные доступа к методам классов DAO.
     */
    private ProjectDao projectDao = new ProjectDao();

    private TaskDao taskDao  = new TaskDao();
    private EmployeeDao employeeDao = new EmployeeDao();

    /**
     * Конструктор.
     *
     * @param project редактируемый проект
     */
    public ProjectPage(Project project) {
        super();
        this.project = project;

    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        get(PAGE_TITLE).setDefaultModelObject("Редактировать проект");
        Form<Project> form = new Form<Project>(PROJECT_FORM, new CompoundPropertyModel<>(project)) {
            @Override
            protected void onSubmit() {
                //ProjectService.update(project);
                setResponsePage(ProjectsListPage.class);
            }
        };
        addButtons(form);
        addFormComponents(form);
        addTaskList(project);
        add(form);
    }



    private void addButtons(Form<Project> form) {
        Link<Void> addTaskLink = new Link<Void>("addTaskInProject") {
            @Override
            public void onClick() {
                setResponsePage(new TaskPage(new Task()));
            }
        };
        add(addTaskLink);
        NoDoubleClickButton button = new NoDoubleClickButton("submit");
        form.add(button);
        form.setDefaultButton(button);
        form.add(new PreventSubmitOnEnterBehavior());
    }

    /**
     * Добавляет компоненты в форму проекта.
     *
     * @param form форма для добавления
     */
    protected static void addFormComponents(Form<Project> form) {
        Link<Void> cancelButton = new Link<Void>("cancel") {
            @Override
            public void onClick() {
                setResponsePage(ProjectsListPage.class);
            }
        };
        form.add(cancelButton);
        RequiredTextField<String> title = new RequiredTextField<>("title");
        title.add(new CustomStringValidator(TITLE_MAXLENGTH));
        form.add(title);
        RequiredTextField<String> description = new RequiredTextField<>("description");
        description.add(new CustomStringValidator(DESCRIPTION_MAXLENGTH));
        form.add(description);
        CustomFeedbackPanel titleFeedbackPanel = new CustomFeedbackPanel("titleFeedbackPanel",
            new ComponentFeedbackMessageFilter(title));
        form.add(titleFeedbackPanel);
        CustomFeedbackPanel descriptionFeedbackPanel = new CustomFeedbackPanel("descriptionFeedbackPanel",
            new ComponentFeedbackMessageFilter(description));
        form.add(descriptionFeedbackPanel);

    }

    /**
     * Добавляет список задач проекта.
     *
     * */
    private void addTaskList(Project project) {
        LoadableDetachableModel<List<Task>> tasks = new LoadableDetachableModel<List<Task>>() {
            @Override
            protected List<Task> load() {
                return project.getTasksList();
            }
        };
        ListView<Task> taskListView = new ListView<Task>("tasks", tasks) {
            @Override
            protected void populateItem(ListItem<Task> item) {
                final Task task = item.getModelObject();
                item.add(new Label("status", task.getStatus().getStatusTitle()));
                item.add(new Label("task_title", task.getTitle()));
                item.add(new Label("workTime", task.getWorkTime()));
                item.add(new Label("beginDate", task.getBeginDate().toString()));
                item.add(new Label("endDate", task.getEndDate().toString()));
                item.add(new Label("projectTitle", project.getTitle()));
                if (task.getEmployeeId() != null && task.getEmployeeId() != 0) {
                    item.add(new Label(EMPLOYEE_NAME, employeeDao.getById(task.getEmployeeId()).getFullName()));
                }
                else {
                    item.add(new Label(EMPLOYEE_NAME, " "));
                }
                item.add(new Link<Void>("deleteLink") {
                    @Override
                    public void onClick() {
                        ProjectService projectService = new ProjectService();
                        projectService.deleteTask(project,task);
                    }
                });
                item.add(new EditInProject("editLink", item, project));
            }
        };
        add(taskListView);
    }
}
