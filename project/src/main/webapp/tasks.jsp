<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=utf-8" language="java" import="utilits.Util.*" %>


<html lang="ru">
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link type="text/css" rel="stylesheet" href="css/nav-bar.css">
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
<ul>
    <li style="font-family: Arial"><a href="projects">Проекты</a></li>
    <li style="font-family: Arial"><a class="choose" href="task">Задачи</a></li>
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
            <form action="task" method="get">
                <input type="hidden" name="action" value="/new" />
                <input type="submit" value="Добавить" class="add-button">
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
                        <td> ${fn:replace(tempTask.beginDate,"-",".")}</td>
                        <td> ${fn:replace(tempTask.endDate,"-",".")}</td>
                        <td> ${fn:escapeXml(PROJ_LIST.get(theCount.index).title)}</td>
                        <td>${fn:escapeXml(EMP_LIST.get(theCount.index).surname)} ${fn:escapeXml(MP_LIST.get(theCount.index).firstName)} ${fn:escapeXml(EMP_LIST.get(theCount.index).patronymic)}</td>
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