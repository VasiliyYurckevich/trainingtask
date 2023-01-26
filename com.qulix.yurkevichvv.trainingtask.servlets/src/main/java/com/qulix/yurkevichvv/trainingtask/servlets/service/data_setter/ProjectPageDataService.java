package com.qulix.yurkevichvv.trainingtask.servlets.service.data_setter;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectService;
import com.qulix.yurkevichvv.trainingtask.model.services.ProjectTemporaryService;
import com.qulix.yurkevichvv.trainingtask.servlets.view_items.TaskView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class ProjectPageDataService implements PageDataService<ProjectTemporaryData>{

    /**
     * Обозначение ID проекта.
     */
    private static final String PROJECT_ID = "projectId";

    /**
     * Обозначение проекта, при редактировании проекта.
     */
    private static final String PROJECT_TEMPORARY_DATA = "projectTemporaryData";

    /**
     * Обозначение название проекта.
     */
    private static final String TITLE_OF_PROJECT = "titleProject";

    /**
     * Обозначение описание проекта.
     */
    private static final String DESCRIPTION = "description";

    private final ProjectTemporaryService projectTemporaryService;
    private final ProjectService projectService;

    public ProjectPageDataService() {
        this.projectService = new ProjectService();
        this.projectTemporaryService = new ProjectTemporaryService();
    }

    @Override
    public void setOutputDataToEntity(Map<String, String> paramsMap, ProjectTemporaryData entity) {
        entity.getProject().setTitle(paramsMap.get(TITLE_OF_PROJECT));
        entity.getProject().setDescription(paramsMap.get(DESCRIPTION));
    }

    @Override
    public void setValidatedDataToPage(HttpServletRequest req, Map<String, String> paramsMap, Map<String, String> errorsMap) {
        req.setAttribute("ERRORS", errorsMap);
        ProjectTemporaryData projectTemporaryData = (ProjectTemporaryData) req.getSession().getAttribute(PROJECT_TEMPORARY_DATA);
        setOutputDataToEntity(paramsMap, projectTemporaryData);
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

        ProjectTemporaryData projectTemporaryData = getEntity(req);

        session.setAttribute(PROJECT_TEMPORARY_DATA, projectTemporaryData);
        session.setAttribute("TASK_LIST", TaskView.convertTasksList(projectTemporaryData.getTasksList()));
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
