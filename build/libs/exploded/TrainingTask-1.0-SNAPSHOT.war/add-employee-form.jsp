<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">

<head>
    <title> Добавить сотрудника </title>
    <link type="text/css" rel="stylesheet" href="css/nav-bar.css">
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>

<ul>
    <li style="font-family: Arial"><a href="projects">Проекты</a></li>
    <li style="font-family: Arial"><a href="tasks">Задачи</a></li>
    <li style="font-family: Arial"><a class="choose" href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
    <div id="container">
        <h3>Добавить сотрудника</h3>
        <form action="employees" method="post">
            <input type="hidden"  name="action" value="/add" />
            <table>
                <tbody>
                <tr>
                    <td><label>Фамилия:</label></td>
                    <td><input required ="required"  maxlength="50" type="text" name=surname></td>
                </tr>
                <tr>
                    <td><label>Имя:</label></td>
                    <td><input  required ="required" maxlength="50"  type="text" name="firstName" ></td>
                </tr>
                <tr>
                    <td><label>Отчество:</label></td>
                    <td><input required ="required"   maxlength="50" type="text" name="lastName"></td>
                </tr>
                <tr>
                    <td><label>Должность:</label></td>
                    <td><input  required ="required"  maxlength="50" type="text" name="post" ></td>
                </tr>
                </tbody>
            </table>

            <r/><br/>
            <input type="submit" value="Сохранить" class="add-button"> <button onclick="javascript:history.back()" type="button" class="add-button">Отмена</button>
        </form>
    </div>
</div>

</body>
</html>
