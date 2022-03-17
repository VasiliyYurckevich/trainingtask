<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=utf-8" %>

<html lang="ru">
<!DOCTYPE html>
<head>
    <link type="text/css" rel="stylesheet" href="css/nav-bar.css">
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
<ul>
    <li style="font-family: Arial"><a href="projects">Проекты</a></li>
    <li style="font-family: Arial"><a class="choose" href="tasks">Задачи</a></li>
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
                <input type="hidden" name="action" value="/new" />
                <input type="submit" value="Добавть" class="add-button">
            </form>
            <table id="table" class="table table-striped">
                <tr>
                    <th>ID</th>
                    <th>Статус</th>
                    <th>Наименование</th>
                    <th>Работа</th>
                    <th>Дата начала</th>
                    <th>Дата окончания</th>
                    <th>Проект</th>
                    <th>Исполнитель</th>
                    <th>Действия</th>

                </tr>

                <c:forEach var="tempTask" items="${TASKS_LIST}" varStatus="theCount">

                    <c:url var="editLink" value="/tasks">
                        <c:param name="action" value="/edit"/>
                        <c:param name="taskId" value="${tempTask.id}"/>
                    </c:url>
                    <c:url var="deleteLink" value="/tasks">
                        <c:param name="action" value="/delete"/>
                        <c:param name="taskId" value="${tempTask.id}"/>
                    </c:url>

                    <tr>
                        <td> ${tempTask.id}</td>
                        <td> ${tempTask.flag}</td>
                        <td> ${tempTask.title} </td>
                        <td> ${tempTask.workTime} </td>
                        <td> ${tempTask.beginDate}</td>
                        <td> ${tempTask.endDate} </td>
                        <td> ${PROJ_LIST.get(theCount.index).title}</td>
                        <td>${EMP_LIST.get(theCount.index).surname} ${EMP_LIST.get(theCount.index).firstName} ${EMP_LIST.get(theCount.index).lastName}</td>
                        <td>
                            <a href="${editLink}">Редактировать</a>

                            <a style="padding-left: 15px" href="${deleteLink}"
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