package com.qulix.yurkevichvv.trainingtask.wicket.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import com.qulix.yurkevichvv.trainingtask.wicket.pages.employee.EmployeesListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.project.ProjectsListPage;
import com.qulix.yurkevichvv.trainingtask.wicket.pages.task.TasksListPage;

/**
 * Верхняя панель для переключения между страницами.
 *
 * @author Q-YVV
 */
public class Header extends Panel {

    /**
     * Конструктор.
     *
     * @param id идентификатор
     */
    public Header(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new WebPageLink("projectsList", ProjectsListPage.class));
        add(new WebPageLink("employeesList", EmployeesListPage.class));
        add(new WebPageLink("tasksList", TasksListPage.class));
    }

    private class WebPageLink<T extends Class<WebPage>> extends Link<WebPage> {
        /**
         * Класс страницы для перехода.
         */
        T clazz;

        public WebPageLink(String id, T clazz) {
            super(id);
            this.clazz = clazz;
        }

        @Override
        public void onClick() {
            setResponsePage(clazz);
        }
    }
}
