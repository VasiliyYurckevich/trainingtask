<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="projectTemporaryData" scope="session"
    type="com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData"/>
<jsp:useBean id="StatusList" scope="request" type="java.util.List<com.qulix.yurkevichvv.trainingtask.model.entity.Status>"/>
<jsp:useBean id="ProjectList" scope="request" type="java.util.List<com.qulix.yurkevichvv.trainingtask.model.entity.Project>"/>
<jsp:useBean id="EmployeeList" scope="request" type="java.util.List<com.qulix.yurkevichvv.trainingtask.model.entity.Employee>"/>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Редактировать задачу</title>
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
    </head>

    <body>

        <my:header/>

        <div class="chief">
            <div class="header">
                <div>
                    <h2>Редактировать задачу</h2>
                </div>
            </div>

            <div id="container" class="main">
                <form action="tasks" method="post" >
                    <input type="hidden" name="action" value="/saveTaskInProject"/>
                    <input type="hidden" name="token" value="${requestScope.token}"/>
                    <input type="hidden" name="taskId" value="${requestScope.taskId}"/>
                    <input type="hidden" name="taskIndex" value="${requestScope.taskIndex}"/>

                    <my:buttons cancelFormAction="projects" cancelAction="/edit" saveAction="/saveTaskInProject"/>

                    <my:dropDownChoice label="Статус" name="status" list="${StatusList}" selectedId="${requestScope.status}"/>

                    <my:textField label="Наименование" id="title" value="${requestScope.title}"/>

                    <my:textField label="Работа" id="workTime" value="${requestScope.workTime}"/>

                    <my:textField label="Дата начала(ГГГГ-ММ-ДД)" id="beginDate" value="${requestScope.beginDate}"/>

                    <my:textField label="Дата окончания(ГГГГ-ММ-ДД)" id="endDate" value="${requestScope.endDate}"/>

                    <my:dropDownChoice label="Наименование проекта" name="projectIdDropDown" list="${ProjectList}"
                        selectedId="${requestScope.projectId}" isNullOption="true" isDisabled="true"
                        basicOption="${fn:escapeXml(projectTemporaryData.title)}"/>

                    <my:dropDownChoice label="Сотрудник" name="employeeId" list="${EmployeeList}"
                        selectedId="${requestScope.employeeId}" isNullOption="true"/>
                </form>
            </div>
        </div>
    </body>
</html>
