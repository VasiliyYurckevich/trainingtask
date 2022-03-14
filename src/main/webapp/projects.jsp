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
    <li style="font-family: Arial"><a class="active"  href="projects">Проекты</a></li>
    <li style="font-family: Arial"><a href="tasks">Задачи</a></li>
    <li style="font-family: Arial"><a href="employees">Сотрудники</a></li>
</ul>
<div style="padding-top: 50px;horiz-align: center">
    <div>
        <div>
            <h2>Проекты</h2>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <form action="projects" method="get">
                <input type="hidden" name="action" value="/new" />
                <input type="submit" value="Добавть" class="add-button">
            </form>
            <table id="table" class="table table-striped">
                <tr>
                    <th>Наименование</th>
                    <th>Описание</th>
                    <th>Действия</th>
                </tr>

                <c:forEach var="tempProject" items="${PROJECT_LIST}">

                    <c:url var="editLink" value="/projects">
                        <c:param name="action" value="/load"/>
                        <c:param name="projectId" value="${tempProject.id}"/>
                    </c:url>
                    <c:url var="deleteLink" value="/projects">
                        <c:param name="action" value="/delete"/>
                        <c:param name="projectId" value="${tempProject.id}"/>
                    </c:url>

                    <tr>
                        <td> ${tempProject.title}</td>
                        <td> ${tempProject.discription} </td>
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