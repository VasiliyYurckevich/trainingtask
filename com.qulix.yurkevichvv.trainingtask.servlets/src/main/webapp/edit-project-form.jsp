<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.qulix.yurkevichvv.trainingtask.servlets.dropdown.TaskView"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="project" scope="session" type="com.qulix.yurkevichvv.trainingtask.model.temporary.ProjectTemporaryData"/>
<jsp:useBean id="TASK_LIST" scope="session" type="java.util.List<com.qulix.yurkevichvv.trainingtask.servlets.dropdown.TaskView>"/>

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

                <form action="projects">
                    <input type="hidden" name="action"/>
                    <input type="hidden" name="projectId" value="${project.id}"/>

                    <div>
                        <my:buttons saveAction="/save" cancelAction="/list"/>
                    </div>

                    <div class="main">
                        <my:textField id="titleProject" label="Наименование" value="${project.title}"/>

                        <my:textField id="description" label="Описание" value="${project.description}"/>
                    </div>

                    <div class="header">
                        <h3>Задачи проекта</h3>
                    </div>

                    <c:url var="addLink" value="/projects">
                        <c:param name="action" value="/editTask"/>
                        <c:param name="projectId" value="${project.id}"/>
                        <c:param name="titleProject" value="${titleProject}"/>
                        <c:param name="description" value="${description}"/>
                    </c:url>

                    <a href="${addLink}" type="add-button">Добавить задачу</a>

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

                        <c:forEach var="tempTask" items="${TaskView.convertTasksList(project.tasksList)}" varStatus="theCount">

                            <c:url var="editLink" value="/projects">
                                <c:param name="action" value="/editTask"/>
                                <c:param name="projectId" value="${project.id}"/>
                                <c:param name="taskIndex" value="${theCount.index}"/>
                            </c:url>

                            <c:url var="deleteLink" value="/projects">
                                <c:param name="action" value="/deleteTask"/>
                                <c:param name="taskIndex" value="${theCount.index}"/>
                            </c:url>

                            <tr>
                                <td> ${tempTask.statusTitle}</td>
                                <td> ${fn:escapeXml(tempTask.title)} </td>
                                <td> ${fn:escapeXml(tempTask.workTime)} </td>
                                <td> ${fn:escapeXml(tempTask.beginDate)}</td>
                                <td> ${fn:escapeXml(tempTask.endDate)} </td>
                                <td> ${fn:escapeXml(project.title)}</td>
                                <td> ${fn:escapeXml(tempTask.employeeFullName)}</td>
                                <td>
                                    <a href="${editLink}">Редактировать</a>
                                    <a href="${deleteLink}">Удалить</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>
