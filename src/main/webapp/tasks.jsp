<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=utf-8" %>

<html lang="ru">
<!DOCTYPE html>
<head>
    <link type="text/css" rel="stylesheet" href="css/menu-navigation-bar.css">
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
<ul>
    <li style="font-family: Arial"><a href="projects">Проекты</a></li>
    <li style="font-family: Arial"><a class="active" href="tasks">Задачи</a></li>
    <li style="font-family: Arial"><a href="employees">Сотрудники</a></li>
</ul>
<div style="padding-top: 50px;horiz-align: center">
    <div>
        <div>
            <h2>Задачи</h2>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <form action="tasks" method="get">
                <input type="hidden" name="action" value="/add" />
                <input type="submit" value="Добавть" class="add-button">
            </form>
            <table id="table" class="table table-striped">
                <tr>
                    <th>Статус</th>
                    <th>Наименование</th>
                    <th>Проект</th>
                    <th>Работа</th>
                    <th>Дата начала</th>
                    <th>Дата окончания</th>
                    <th>Исполнитель</th>
                    <th>Действия</th>

                </tr>

                <c:forEach var="tempTask" items="${TASKS_LIST}">

                    <c:url var="editLink" value="/tasks">
                        <c:param name="action" value="/load"/>
                        <c:param name="taskId" value="${tempTask.id}"/>
                    </c:url>
                    <c:url var="deleteLink" value="/tasks">
                        <c:param name="action" value="/delete"/>
                        <c:param name="taskId" value="${tempTask.id}"/>
                    </c:url>

                    <tr>
                        <td> ${tempTask.flag}</td>
                        <td> ${tempTask.title} </td>
                        <td> ${tempTask.project_id}</td>
                        <td> ${tempTask.workTime} </td>
                        <td> ${tempTask.startDate}</td>
                        <td> ${tempTask.endDate} </td>
                        <td> ${tempTask.employee_id}</td>
                        <td>
                            <a href="${editLink}">Редактировать</a>
                            |
                            <a href="${deleteLink}"
                               onclick="if (!(confirm('Are you sure you want to delete this project?'))) return false">Удалить</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>

        </div>

    </div>
</div>



</body>
</html>