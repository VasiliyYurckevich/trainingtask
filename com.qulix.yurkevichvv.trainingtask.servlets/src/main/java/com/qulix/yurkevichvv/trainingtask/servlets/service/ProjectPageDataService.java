package com.qulix.yurkevichvv.trainingtask.servlets.service;

import com.qulix.yurkevichvv.trainingtask.model.entity.Project;
import com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ProjectPageDataService implements PageDataService<ProjectTemporaryData>{

    @Override
    public void setOutputDataToEntity(Map<String, String> paramsMap, ProjectTemporaryData entity) {

    }

    @Override
    public void setValidatedDataToPage(HttpServletRequest req, Map<String, String> paramsMap, Map<String, String> errorsMap) {

    }

    @Override
    public Map<String, String> getDataFromPage(HttpServletRequest req) {
        return null;
    }

    @Override
    public ProjectTemporaryData getEntity(String id) {
        return null;
    }
}
