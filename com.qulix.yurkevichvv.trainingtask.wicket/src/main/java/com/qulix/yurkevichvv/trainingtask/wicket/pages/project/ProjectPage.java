package com.qulix.yurkevichvv.trainingtask.wicket.pages.project;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.AbstractTaskPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.EditTaskPage;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.entity.Task;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.base.AbstractEntityPage;

/**
 * Страница добавления проекта.
 *
 * @author Q-YVV
 */
public class ProjectPage extends AbstractEntityPage<ProjectTemporaryData> {

    /**
     * Максимальная длинна ввода поля наименования.
     */
    private static final int TITLE_MAXLENGTH = 50;

    /**
     * Максимальная длинна ввода поля описания.
     */
    private static final int DESCRIPTION_MAXLENGTH = 250;

    /**
     * Конструктор.
     */
    public ProjectPage(CompoundPropertyModel<ProjectTemporaryData> propertyModel) {
        super("Редактировать проект", propertyModel, new ProjectForm("projectForm", propertyModel));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        addFormComponents();
        addTaskList();
        add(getForm());
    }

    /**
     * Генерирует страницу редактирования задачи.
     *
     * @param task задача
     * @return страницу редактирования задачи
     */
    protected AbstractTaskPage getNewTaskPage(Task task) {
//переделать
        task.setProjectId(getEntityModel().getObject().getId());

        return new EditTaskPage(CompoundPropertyModel.of(task)/*, getEntityModel()*/);
    }

    @Override
    protected void addFormComponents() {
      /*  Link<Void> addTaskLink = new Link<>("addTaskInProject",) {
            @Override
            public void onClick() {
                setResponsePage(getNewTaskPage(new Task()));
            }
        };
        getForm().add(addTaskLink);*/

        addButtons();

        addStringField("project.title", TITLE_MAXLENGTH);
        addStringField("project.description", DESCRIPTION_MAXLENGTH);
    }

    /**
     * Добавляет список задач проекта.
     */
    private void addTaskList() {
        //Исправить сервис
        getForm().add(new TasksInProjectListView(LoadableDetachableModel.of(() ->
            this.getEntityModel().getObject().getTasksList()), new ProjectTemporaryService()));
    }

/*    @Override
    protected final void onSubmitting() {
        service.save(entityModel.getObject());
    }

    @Override
    protected final void onChangesSubmitted() {
        setResponsePage(ProjectsListPage.class);
    }*/

    /**
     * Страница создания задачи проекта.
     */
//    private class NewProjectTaskPage extends AbstractTaskPage {
//
//        *
//         * Модель проекта.
//
//        private final CompoundPropertyModel<ProjectTemporaryData> projectModel;
//
//        *
//         * Конструктор.
//         *
//         * @param taskModel    модель задачи
//         * @param projectModel модель проекта
//
//        private NewProjectTaskPage(CompoundPropertyModel<Task> taskModel, CompoundPropertyModel<ProjectTemporaryData> projectModel) {
//            super(taskModel);
//            this.projectModel = projectModel;
//        }
//
//        @Override
//        protected void onSubmitting() {
//            service.addTask(projectModel.getObject(), entityModel.getObject());
//        }
//
//        @Override
//        protected void onChangesSubmitted() {
//                setResponsePage(ProjectPage.this);
//        }
//
//        @Override
//        protected boolean changeProjectOption() {
//            return false;
//        }
//    }
}
