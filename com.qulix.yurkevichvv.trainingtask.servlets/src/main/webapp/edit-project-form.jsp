<%@ page import="com.qulix.yurkevichvv.trainingtask.model.services.ProjectService" %>
<%@ page import="com.qulix.yurkevichvv.trainingtask.model.entity.Project" %>
<%@ page import="com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService" %>
<%@ page import="com.qulix.yurkevichvv.trainingtask.model.entity.Task" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Редактировать проект</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>

        <my:mainMenu></my:mainMenu>

        <div class="chief">
            <div id="container">
                <h3>Редактировать проект</h3>
                <form action="projects" method="post">
                    <input type="hidden" name="action" value="/update" />
                    <input type="hidden" name="projectId" value="${project.id}"/>
                    <div>
                        <input type="submit" value="Сохранить" name="submitButton" id="submitButton" class="add-button">
                        <button id="cancelButton" name="cancelButton" onclick="location.href='projects'"
                                type="button" class="add-button">
                            Отмена
                        </button>
                    </div>
                    <div class="main">
                        <div class="field">
                            <label>Наименование:</label>
                            <input id="titleProject" name="titleProject" value="${fn:escapeXml(titleProject)}">
                            <a class = "feedback">${ERRORS.get("titleProject")}</a>
                        </div>
                        <div class="field">
                            <label>Описание:</label>
                            <input id="description" name="description" value="${fn:escapeXml(description)}">
                            <a class = "feedback">${ERRORS.get("description")}</a>
                        </div>
                    </div>
                    <div class="header">
                        <h3>Задачи проекта</h3>
                    </div>
                    <table>
                        <tr>
                            <th>Статус</th>
                            <th>Наименование</th>
                            <th>Работа</th>
                            <th>Дата начала</th>
                            <th>Дата окончания</th>
                            <th>Проект</th>
                            <th>Исполнитель</th>
                            <th>Действия</th>
                        </tr>

                        <c:url var="addLink" value="/projects">
                            <c:param name="action" value="/addTaskForm"/>
                            <c:param name="projectId" value="${thisProjectId}"/>
                        </c:url>

                        <c:forEach var="tempTask" items="${project.tasksList}" varStatus="theCount">

                            <%
                                Task task = (Task) pageContext.getAttribute("tempTask");
                                pageContext.setAttribute("projectTitle",
                                        new ProjectService().getById(task.getProjectId()).getTitle());
                                pageContext.setAttribute("employeeFullName", task.getEmployeeId() != 0 ?
                                        new EmployeeService().getById(task.getEmployeeId()).getFullName() : "");
                            %>

                            <c:url var="editLink" value="/projects">
                                <c:param name="action" value="/editTaskForm"/>
                                <c:param name="taskId" value="${tempTask.id}"/>
                                <c:param name="projectId" value="${thisProjectId}"/>
                                <c:param name="taskIndex" value="${theCount.index}"/>
                            </c:url>

                            <c:url var="deleteLink" value="/projects">
                                <c:param name="action" value="/deleteTaskInProject"/>
                                <c:param name="taskIndex" value="${theCount.index}"/>
                            </c:url>

                            <tr>
                                <td> ${tempTask.status.getStatusTitle()}</td>
                                <td> ${fn:escapeXml(tempTask.title)} </td>
                                <td> ${fn:escapeXml(tempTask.workTime)} </td>
                                <td> ${fn:escapeXml(tempTask.beginDate)}</td>
                                <td> ${fn:escapeXml(tempTask.endDate)} </td>
                                <td> ${fn:escapeXml(projectTitle)}</td>
                                <td> ${fn:escapeXml(employeeFullName)}</td>
                                <td>
                                    <a href="${editLink}">Редактировать</a>
                                    <a href="${deleteLink}" onclick="if (!(confirm('Вы уверены?'))) return false">
                                        Удалить
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        <a href="${addLink}">Добавить задачу</a>
                    </table>
                </form>
            </div>
        </div>
        <script type="text/javascript" src="blocker.js"></script>
    </body>
</html>
