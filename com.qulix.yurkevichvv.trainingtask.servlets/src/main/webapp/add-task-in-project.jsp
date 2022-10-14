<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Добавить задачу</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>

        <my:mainMenu></my:mainMenu>

        <div class="chief">
            <div id="container" class="main">
                <h3>Добавить задачу</h3>
                <form action="tasks" method="post" id="form">
                    <input type="hidden" name="action" value="/newTaskInProject"/>
                    <div>
                        <input class="add-button" type="submit" name="submitButton" id="submitButton" value="Сохранить">
                        <button id="cancelButton" name="cancelButton"
                                onclick="location.href='${pageContext.request.contextPath}/projects?action=%2fedit&projectId=${thisProjectId}'"
                                type="button" class="add-button">
                            Отмена
                        </button>
                    </div>

                    <div class="field">
                        <label>Статус:</label>
                        <select name="status">
                            <c:forEach items="${STATUS_LIST}" var="statuses">
                                <option value="${statuses.getId()}">
                                        ${fn:escapeXml(statuses.getStatusTitle())}
                                </option>
                            </c:forEach>
                        </select>
                        <br>
                        <a class = "feedback">${ERRORS.get("status")}</a>
                    </div>
                    <div class="field">
                        <label>Наименование:</label>
                        <input id="title" name="title" value="${fn:escapeXml(title)}">
                        <br>
                        <a class = "feedback">${ERRORS.get("title")}</a>
                    </div>
                    <div class="field">
                        <label>Работа:</label>
                        <input id="workTime" name="workTime" value="${fn:escapeXml(workTime)}">
                        <br>
                        <a class = "feedback">${ERRORS.get("workTime")}</a>
                    </div>
                    <div class="field">
                        <label>Дата начала(ГГГГ-ММ-ДД):</label>
                        <input id="beginDate" name="beginDate" value="${fn:escapeXml(beginDate)}">
                        <br>
                        <a class = "feedback">${ERRORS.get("beginDate")}</a>
                    </div>
                    <div class="field">
                        <label>Дата окончания(ГГГГ-ММ-ДД):</label>
                        <input id="endDate" name="endDate" value="${fn:escapeXml(endDate)}">
                        <br>
                        <a class = "feedback">${ERRORS.get("endDate")}</a>
                    </div>
                    <div class="field">
                        <label>Наименование проекта:</label>
                        <select name="projectId" disabled="disabled">
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
                                        ${fn:escapeXml(employees.surname)}
                                        ${fn:escapeXml(employees.firstName)}
                                        ${fn:escapeXml(employees.patronymic)}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </div>
        </div>
        <script type="text/javascript" src="blocker.js"></script>
    </body>
</html>

