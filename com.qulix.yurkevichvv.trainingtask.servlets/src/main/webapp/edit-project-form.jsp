<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:useBean id="projectTemporaryData" scope="session" type="com.qulix.yurkevichvv.trainingtask.model.entity.ProjectTemporaryData"/>
<jsp:useBean id="TASK_LIST" scope="session" type="java.util.List<com.qulix.yurkevichvv.trainingtask.model.entity.Task>"/>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Редактировать проект</title>
        <link type="text/css" rel="stylesheet" href="css/style.css"/>
    </head>

    <body>

        <my:header/>

        <div class="chief">
            <div class="header">
                <div>
                    <h2>Редактировать проект</h2>
                </div>
            </div>

            <div id="container" class="main">
                <form action="projects">
                    <input type="hidden" name="action"/>
                    <input type="hidden" name="projectId" value="${projectTemporaryData.id}"/>
                    <input type="hidden" name="token" value="${requestScope.token}">
                    <input type="hidden" name="taskIndex"/>

                    <my:buttons saveAction="/save" cancelAction="/list"/>

                    <div class="main">
                        <my:textField id="titleProject" label="Наименование" value="${requestScope.titleProject}"/>

                        <my:textField id="description" label="Описание" value="${requestScope.description}"/>
                    </div>

                    <div class="header">
                        <h3>Задачи проекта</h3>
                    </div>

                    <div class="single">
                        <button formmethod="get" id="addButton" name="addButton"
                                onclick="action.value='/editTask'" type="submit" class="add-button">
                            Добавить задачу
                        </button>
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

                        <c:forEach var="tempTask" items="${projectTemporaryData.tasksList}"
                            varStatus="theCount">

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
                                <td> ${fn:escapeXml(tempTask.status.statusTitle)}</td>
                                <td> ${fn:escapeXml(tempTask.title)} </td>
                                <td> ${fn:escapeXml(tempTask.workTime)} </td>
                                <td> ${fn:escapeXml(tempTask.beginDate)}</td>
                                <td> ${fn:escapeXml(tempTask.endDate)} </td>
                                <td> ${fn:escapeXml(projectTemporaryData.title)}</td>
                                <td> ${fn:escapeXml(tempTask.employee.fullName)}</td>
                                <td>
                                    <button formmethod="get" id="submitButton" name="submitButton"
                                        onclick="action.value='/editTask'; taskIndex.value = '${theCount.index}'"
                                        type="submit" class="add-button">
                                        Редактировать
                                    </button>
                                    <button formmethod="get" id="cancelButton" name="cancelButton"
                                        onclick="action.value='/deleteTask'; taskIndex.value = '${theCount.index}'"
                                        type="submit" class="add-button">
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
