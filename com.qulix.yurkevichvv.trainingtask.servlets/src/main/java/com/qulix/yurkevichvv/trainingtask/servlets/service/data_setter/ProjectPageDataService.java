package com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.servlets.view_items.TaskView;

/**
 * Отвечает за взаимодействие данных {@link ProjectTemporaryData} и визуализации на странице.
 *
 * @author Q-YVV
 */
public class ProjectPageDataService implements PageDataService<ProjectTemporaryData> {

    /**
     * ID проекта.
     */
    private static final String PROJECT_ID = "projectId";

    /**
     * Данные проекта.
     */
    private static final String PROJECT_TEMPORARY_DATA = "projectTemporaryData";

    /**
     * Название проекта.
     */
    private static final String TITLE_OF_PROJECT = "titleProject";

    /**
     * Описание проекта.
     */
    private static final String DESCRIPTION = "description";

    /**
     * Список задач проекта.
     */
    private static final String TASK_LIST = "TASK_LIST";

    /**
     * Переменная доступа к методам работы с сущностями {@link Project}.
     */
    private final ProjectService projectService = new ProjectService();

    @Override
    public void setOutputDataToEntity(Map<String, String> paramsMap, ProjectTemporaryData entity) {
        entity.setTitle(paramsMap.get(TITLE_OF_PROJECT));
        entity.setDescription(paramsMap.get(DESCRIPTION));
    }

    @Override
    public void setFailedDataToPage(HttpServletRequest req, Map<String, String> paramsMap, Map<String, String> errorsMap) {
        req.setAttribute("ERRORS", errorsMap);
        req.setAttribute(TITLE_OF_PROJECT, paramsMap.get(TITLE_OF_PROJECT));
        req.setAttribute(DESCRIPTION, paramsMap.get(DESCRIPTION));
    }

    @Override
    public Map<String, String> getDataFromPage(HttpServletRequest req) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(TITLE_OF_PROJECT, req.getParameter(TITLE_OF_PROJECT));
        paramsMap.put(DESCRIPTION, req.getParameter(DESCRIPTION));
        return paramsMap;
    }

    @Override
    public void setDataToPage(HttpServletRequest req, ProjectTemporaryData entity) {
        HttpSession session = req.getSession();

        session.setAttribute(PROJECT_TEMPORARY_DATA, entity);
        session.setAttribute(TASK_LIST, TaskView.convertTasksList(entity.getTasksList()));

        req.setAttribute(TITLE_OF_PROJECT, entity.getTitle());
        req.setAttribute(DESCRIPTION, entity.getDescription());
    }

    @Override
    public ProjectTemporaryData getEntity(HttpServletRequest req) {
        if (req.getSession().getAttribute(PROJECT_TEMPORARY_DATA) != null) {
            return (ProjectTemporaryData) req.getSession().getAttribute(PROJECT_TEMPORARY_DATA);
        }

        if (!req.getParameter(PROJECT_ID).isBlank()) {
            return new ProjectTemporaryData(projectService.getById(Integer.valueOf(req.getParameter(PROJECT_ID))));
        }

        return new ProjectTemporaryData(new Project());
    }
}
