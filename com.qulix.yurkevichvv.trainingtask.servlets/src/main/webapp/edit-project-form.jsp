<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.qulix.yurkevichvv.trainingtask.servlets.view_items.TaskView"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="projectTemporaryData" scope="session" type="com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData"/>
<jsp:useBean id="TASK_LIST" scope="session" type="java.util.List<com.qulix.yurkevichvv.trainingtask.servlets.view_items.TaskView>"/>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Редактировать проект</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>

        <my:mainMenu/>

        <div class="chief">
            <div id="container">

                <h3>Редактировать проект</h3>

                <form action="projects">
                    <input type="hidden" name="action"/>
                    <input type="hidden" name="projectId" value="${projectTemporaryData.project.id}"/>
                    <input type="hidden" name="taskIndex"/>

                    <div>
                        <my:buttons saveAction="/save" cancelAction="/list"/>
                    </div>

                    <div class="main">
                        <my:textField id="titleProject" label="Наименование" value="${titleProject}"/>

                        <my:textField id="description" label="Описание" value="${description}"/>
                    </div>

                    <div class="header">
                        <h3>Задачи проекта</h3>
                    </div>

                    <button formmethod="get" id="addButton" name="addButton"
                            onclick="action.value='/editTask'" type="submit" class="add-button">
                        Добавить задачу
                    </button>

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

                        <c:forEach var="tempTask" items="${TaskView.convertTasksList(projectTemporaryData.tasksList)}" varStatus="theCount">

                            <c:url var="editLink" value="/projects">
                                <c:param name="action" value="/editTask"/>
                                <c:param name="projectId" value="${projectTemporaryData.id}"/>
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
                                <td> ${fn:escapeXml(projectTemporaryData.project.title)}</td>
                                <td> ${fn:escapeXml(tempTask.employeeFullName)}</td>
                                <td>
                                    <button formmethod="get" id="submitButton" name="submitButton"
                                            onclick="action.value='/editTask'; taskIndex.value = '${theCount.index}'" type="submit" class="add-button">
                                        Редактировать
                                    </button>
                                    <button formmethod="get" id="cancelButton" name="cancelButton"
                                            onclick="action.value='/deleteTask'; taskIndex.value = '${theCount.index}'" type="submit" class="add-button">
                                        Удалить
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>
