<%@ page import="com.qulix.yurkevichvv.trainingtask.model.entity.Task" %>
<%@ page import="com.qulix.yurkevichvv.trainingtask.model.services.ProjectService" %>
<%@ page import="com.qulix.yurkevichvv.trainingtask.model.services.EmployeeService" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=utf-8" %>
<jsp:useBean id="TASKS_LIST" scope="request" type="java.util.List<com.qulix.yurkevichvv.trainingtask.servlets.TaskView>"/>


<!DOCTYPE html>
<html lang="ru">
    <head>
        <link type="text/css" rel="stylesheet" href="css/style.css">
        <title>Задачи</title>
    </head>

    <body>

        <my:mainMenu/>

        <div class="chief">
            <div class="header">
                <div>
                    <h2>Задачи</h2>
                </div>
            </div>

            <div>
                <div>
                    <form action="tasks" method="get">
                        <input type="hidden" name="action" value="/edit" />
                        <input type="submit" value="Добавить" ${PROJECT_LIST.isEmpty() ? 'disabled' : ''} class="add-button">
                        ${PROJECT_LIST.isEmpty() ?
                        '<a class="feedback">Отсутствуют проекты в которые можно добавить задачу! Создайте хотя бы один проект</a>': ''}
                    </form>

                    <table id="table" class="table table-striped">
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

                        <c:forEach var="tempTask" items="${TASKS_LIST}">

                            <c:url var="editLink" value="/tasks">
                                <c:param name="action" value="/edit"/>
                                <c:param name="taskId" value="${tempTask.id}"/>
                            </c:url>
                            <c:url var="deleteLink" value="/tasks">
                                <c:param name="action" value="/delete"/>
                                <c:param name="taskId" value="${tempTask.id}"/>
                            </c:url>

                            <tr>
                                <td> ${fn:escapeXml(tempTask.statusTitle)}</td>
                                <td> ${fn:escapeXml(tempTask.title)} </td>
                                <td> ${fn:escapeXml(tempTask.workTime)} </td>
                                <td> ${fn:escapeXml(tempTask.beginDate)}</td>
                                <td> ${fn:escapeXml(tempTask.endDate)}</td>
                                <td> ${fn:escapeXml(tempTask.projectTitle)}</td>
                                <td> ${fn:escapeXml(tempTask.employeeFullName)}</td>
                                <td>
                                    <a href="${editLink}">Редактировать</a>
                                    <a href="${deleteLink}"
                                        onclick="if (!(confirm('Вы уверены?'))) return false">Удалить</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>