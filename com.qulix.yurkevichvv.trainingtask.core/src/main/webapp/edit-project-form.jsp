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

        <div>
            <div id="container">
                <h3 >Редактировать проект</h3>
                <form action="projects" method="post">
                    <input type="hidden" name="action" value="/update" />
                    <input type="hidden" name="projectId" value="${projectId}"/>

                    <table>
                        <div>
                            <input type="submit" value="Сохранить" name="submitButton" id="submitButton" class="add-button">
                            <button id="cancelButton" name="cancelButton" onclick="location.href='projects'"
                                type="button" class="add-button">
                                Отмена
                            </button>
                        </div>
                        <tbody>
                        <tr>
                            <td>
                                <label>Наименование:</label>
                            </td>
                            <td>
                                <input id="titleProject" name="titleProject" value="${fn:escapeXml(titleProject)}">
                            </td>
                            <td>
                                <h4>${ERRORS.get(0)}</h4>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Описание:</label>
                            </td>
                            <td>
                                <input id="description" name="description" value="${fn:escapeXml(description)}">
                            </td>
                            <td>
                                <h4>${ERRORS.get(1)}</h4>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <tbody>
                        <div>
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
                            <c:param name="projectId" value="${projectId}"/>
                            </c:url>

                            <c:forEach var="tempTask" items="${TASKS_LIST}" varStatus="theCount">

                                <c:url var="editLink" value="/projects">
                                <c:param name="action" value="/editTaskForm"/>
                                <c:param name="taskId" value="${tempTask.id}"/>
                                <c:param name="numberInList" value="${theCount.index}"/>
                                </c:url>

                                <c:url var="deleteLink" value="/projects">
                                <c:param name="action" value="/deleteTaskInProject"/>
                                <c:param name="numberInList" value="${theCount.index}"/>
                                </c:url>

                                <tr>
                                    <td> ${tempTask.status}</td>
                                    <td> ${fn:escapeXml(tempTask.title)} </td>
                                    <td> ${tempTask.workTime} </td>
                                    <td> ${tempTask.beginDate}</td>
                                    <td> ${tempTask.endDate} </td>
                                    <td> ${fn:escapeXml(titleProject)}</td>
                                    <td> ${fn:escapeXml(EMPLOYEE_IN_TASKS_LIST.get(theCount.index).surname)}
                                        ${fn:escapeXml(EMPLOYEE_IN_TASKS_LIST.get(theCount.index).firstName)}
                                        ${fn:escapeXml(EMPLOYEE_IN_TASKS_LIST.get(theCount.index).patronymic)}
                                    </td>
                                    <td>
                                        <a href="${editLink}">Редактировать</a>
                                        <a href="${deleteLink}" onclick="if (!(confirm('Вы уверены?'))) return false">
                                            Удалить
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <a href="${addLink}">Добавить задачу</a>
                    </tbody>
                </form>
            </div>
        </div>
    </body>
</html>
