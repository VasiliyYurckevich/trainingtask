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
    <li style="font-family: Arial"><a href="tasks">Задачи</a></li>
    <li style="font-family: Arial"><a class="active" href="employees">Сотрудники</a></li>
</ul>
<div style="padding-top: 50px;horiz-align: center">
    <div>
        <div>
            <h2>Сотрудники</h2>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <form action="employees" method="get">
                <input type="hidden" name="action" value="add" />
                <input type="submit" value="Добавть" class="add-button">
            </form>
            <table id="table" class="table table-striped">
                <tr>
                    <th>Фамилия</th>
                    <th>Имя</th>
                    <th>Отчество</th>
                    <th>Должность</th>
                    <th>Действия</th>
                </tr>

                <c:forEach var="tempEmployee" items="${EMPLOYEE_LIST}">

                    <c:url var="editLink" value="employees">
                        <c:param name="action" value="/edit"/>
                        <c:param name="employeeId" value="${tempEmployee.id}"/>
                    </c:url>
                    <c:url var="deleteLink" value="employees">
                        <c:param name="action" value="/delete"/>
                        <c:param name="employeeId" value="${tempEmployee.id}"/>
                    </c:url>

                    <tr>
                        <td> ${tempEmployee.surname}</td>
                        <td> ${tempEmployee.firstName} </td>
                        <td> ${tempEmployee.lastName}</td>
                        <td> ${tempEmployee.post} </td>
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