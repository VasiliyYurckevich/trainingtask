<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=utf-8" %>
<jsp:useBean id="TASKS_LIST" scope="request" type="java.util.List<com.qulix.yurkevichvv.trainingtask.servlets.view_items.TaskView>"/>
<jsp:useBean id="IS_ZERO_PROJECTS" scope="request" type="java.lang.Boolean"/>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <link type="text/css" rel="stylesheet" href="css/style.css">
        <title>Задачи</title>
    </head>

    <body>

        <my:header/>

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
                        <input type="hidden" name="taskId" value="">
                        <input type="submit" value="Добавить" ${IS_ZERO_PROJECTS ? 'disabled' : ''} class="add-button">
                        ${IS_ZERO_PROJECTS ?
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

                        <c:forEach var="taskView" items="${TASKS_LIST}">

                            <c:url var="editLink" value="/tasks">
                                <c:param name="action" value="/edit"/>
                                <c:param name="taskId" value="${taskView.id}"/>
                            </c:url>
                            <c:url var="deleteLink" value="/tasks">
                                <c:param name="action" value="/delete"/>
                                <c:param name="taskId" value="${taskView.id}"/>
                            </c:url>

                            <tr>
                                <td> ${fn:escapeXml(taskView.statusTitle)}</td>
                                <td> ${fn:escapeXml(taskView.title)} </td>
                                <td> ${fn:escapeXml(taskView.workTime)} </td>
                                <td> ${fn:escapeXml(taskView.beginDate)}</td>
                                <td> ${fn:escapeXml(taskView.endDate)}</td>
                                <td> ${fn:escapeXml(taskView.projectTitle)}</td>
                                <td> ${fn:escapeXml(taskView.employeeFullName)}</td>
                                <td>
                                    <a href="${editLink}">Редактировать</a>
                                    <a href="${deleteLink}">Удалить</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>