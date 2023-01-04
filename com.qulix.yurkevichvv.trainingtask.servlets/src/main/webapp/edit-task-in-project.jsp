<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Редактировать задачу</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>

        <my:mainMenu></my:mainMenu>

        <div class="chief">
            <div id="container" class="main">
                <h3>Редактировать задачу</h3>
                <form action="tasks" method="post" >
                    <input type="hidden" name="action" value="/saveTaskInProject" />
                    <input type="hidden" name="taskIndex" value="${taskIndex}" />
                    <input class="add-button" type="submit" name="submitButton" id="submitButton" value="Сохранить">
                    <button id="cancelButton" name="cancelButton"
                            onclick="location.href='${pageContext.request.contextPath}/projects?action=%2fedit&projectId=${project.id}'"
                            type="button" class="add-button">
                        Отмена
                    </button>

                    <div class="field">
                        <label>Статус:</label>
                        <select name="status">
                            <c:forEach items="${STATUS_LIST}" var="statuses">
                                <option value="${statuses.getId()}" ${statuses.getId() == status ? 'selected="selected"' : ''}>
                                        ${fn:escapeXml(statuses.getStatusTitle())}
                                </option>
                            </c:forEach>
                        </select>
                        <br>
                        <a class="feedback">${ERRORS.get("status")}</a>
                    </div>

                    <my:textField name="Наименование" id="title" value="${title}"/>

                    <my:textField name="Работа" id="workTime" value="${workTime}"/>

                    <my:textField name="Дата начала(ГГГГ-ММ-ДД)" id="beginDate" value="${beginDate}"/>

                    <my:textField name="Дата окончания(ГГГГ-ММ-ДД)" id="endDate" value="${endDate}"/>

                    <div class="field">
                        <label>Наименование проекта:</label>
                        <select disabled="disabled" name="projectId">
                            <c:forEach items="${PROJECT_LIST}" var="projects">
                                <option value="${projects.id}" ${projects.id == projectId ? 'selected="selected"' : ''}>
                                        ${fn:escapeXml(projects.title)}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="field">
                        <label>Сотрудник:</label>
                        <select name="employeeId">
                            <option value=""> </option>
                            <c:forEach items="${EMPLOYEE_LIST}" var="employees">
                                <option value="${employees.id}" ${employees.id == employeeId ? 'selected="selected"' : ''}>
                                        ${fn:escapeXml(employees.fullName)}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
