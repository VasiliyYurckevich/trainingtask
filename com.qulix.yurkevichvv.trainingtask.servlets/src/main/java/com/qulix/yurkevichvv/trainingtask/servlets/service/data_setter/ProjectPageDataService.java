package com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;

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
    public void setFailedDataToPage(HttpServletRequest request, Map<String, String> paramsMap, Map<String, String> errorsMap) {
        request.setAttribute("ERRORS", errorsMap);
        request.setAttribute(TITLE_OF_PROJECT, paramsMap.get(TITLE_OF_PROJECT));
        request.setAttribute(DESCRIPTION, paramsMap.get(DESCRIPTION));
    }

    @Override
    public Map<String, String> getDataFromPage(HttpServletRequest request) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(TITLE_OF_PROJECT, request.getParameter(TITLE_OF_PROJECT));
        paramsMap.put(DESCRIPTION, request.getParameter(DESCRIPTION));
        return paramsMap;
    }

    @Override
    public void setDataToPage(HttpServletRequest request, ProjectTemporaryData entity) {
        HttpSession session = request.getSession();

        session.setAttribute(PROJECT_TEMPORARY_DATA, entity);
        session.setAttribute(TASK_LIST, entity.getTasksList());

        request.setAttribute(TITLE_OF_PROJECT, entity.getTitle());
        request.setAttribute(DESCRIPTION, entity.getDescription());
    }

    @Override
    public ProjectTemporaryData getEntity(HttpServletRequest request) {

        if (request.getSession().getAttribute(PROJECT_TEMPORARY_DATA) != null) {
            return (ProjectTemporaryData) request.getSession().getAttribute(PROJECT_TEMPORARY_DATA);
        }

        if (!request.getParameter(PROJECT_ID).isBlank()) {
            return new ProjectTemporaryData(projectService.getById(Integer.valueOf(request.getParameter(PROJECT_ID))));
        }

        return new ProjectTemporaryData(new Project());
    }
}
