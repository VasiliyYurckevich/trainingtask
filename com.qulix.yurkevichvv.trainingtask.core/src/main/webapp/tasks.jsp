    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=utf-8" %>

<html lang="ru">
    <!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <link type="text/css" rel="stylesheet" href="css/style.css">
        <title></title>
    </head>


    <body>
        <ul>
            <li>
                <a href="projects">Проекты</a>
            </li>
            <li>
                <a class="choose" href="task">Задачи</a>
            </li>
            <li>
                <a href="employees">Сотрудники</a>
            </li>
        </ul>

        <div>
            <div>
                <div>
                    <h2>Задачи</h2>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <form action="task" method="get">
                        <input type="hidden" name="action" value="/new" />
                        <input type="submit" value="Добавить" ${PROJECT_LIST.isEmpty() ? 'disabled' : ''} class="add-button">
                        ${PROJECT_LIST.isEmpty() ?
                        '<h4>Отсутствуют проекты в которые можно добавить задачу! Создайте хотя бы один проект</h4>': ''}
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

                        <c:forEach var="tempTask" items="${TASKS_LIST}" varStatus="theCount" >

                            <c:url var="editLink" value="/task">
                                <c:param name="action" value="/edit"/>
                                <c:param name="taskId" value="${tempTask.id}"/>
                            </c:url>
                            <c:url var="deleteLink" value="/task">
                                <c:param name="action" value="/delete"/>
                                <c:param name="taskId" value="${tempTask.id}"/>
                            </c:url>

                            <tr>
                                <td> ${tempTask.status}</td>
                                <td> ${fn:escapeXml(tempTask.title)} </td>
                                <td> ${tempTask.workTime} </td>
                                <td> ${tempTask.beginDate}</td>
                                <td> ${tempTask.endDate}</td>
                                <td> ${fn:escapeXml(PROJ_LIST.get(theCount.index).title)}</td>
                                <td> ${fn:escapeXml(EMPLOYEE_IN_TASKS_LIST.get(theCount.index).surname)}
                                    ${fn:escapeXml(EMPLOYEE_IN_TASKS_LIST.get(theCount.index).firstName)}
                                    ${fn:escapeXml(EMPLOYEE_IN_TASKS_LIST.get(theCount.index).patronymic)}
                                </td>
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