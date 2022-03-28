<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">

<head>
    <title> Добавить проект</title>

    <link type="text/css" rel="stylesheet" href="css/nav-bar.css">
    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/temp.css">

</head>
<body>

<ul>
    <li style="font-family: Arial"><a href="projects">Проекты</a></li>
    <li style="font-family: Arial"><a href="tasks">Задачи</a></li>
    <li style="font-family: Arial"><a class="choose" href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
    <div id="container">
        <h3>Редактировать проект</h3>
        <form action="employees" method="post">
            <input type="hidden" name="action" value="/update" />
            <input type="hidden" name="employeeId" value="${employeeId}" />

            <table>
                <div>
                    <input type="submit" value="Сохранить" class="add-button"> <button onclick="javascript:history.back()" type="button" class="add-button">Отмена</button>
                </div>
                <tbody>
                <tr>
                    <td><label>Фамилия:</label></td>
                    <td><input required="required"  type="text" maxlength="50"  name="surname"
                               value="${surname}"></td>
                </tr>
                <tr>
                    <td><label>Имя:</label></td>
                    <td><input type="text" required="required"  maxlength="50"  name="firstName"
                               value="${firstName}"></td>
                </tr>
                <tr>
                    <td><label>Отчество:</label></td>
                    <td><input type="text"  required="required" maxlength="50"  name="lastName"
                               value="${lastName}"></td>
                </tr>
                <tr>
                    <td><label>Должность:</label></td>
                    <td><input type="text" required="required" maxlength="50"  name="post"
                               value="${post}"></td>
                </tr>
                </tbody>
                <br/><br/>
        </form>

    </div>
</div>

</body>
</html>
