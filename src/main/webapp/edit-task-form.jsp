<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">

<head>
    <title>Редактировать проект</title>

    <link type="text/css" rel="stylesheet" href="css/nav-bar.css">
    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/temp.css">

</head>
<body>

<ul>
    <li style="font-family: Arial"><a href="projects">Проекты</a></li>
    <li style="font-family: Arial"><a class="choose"  href="tasks">Задачи</a></li>
    <li style="font-family: Arial"><a href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
    <div id="container">
        <h3>Редактировать задачу</h3>

        <form action="tasks" method="get">

            <input type="hidden" name="action" value="/update" />
            <input type="hidden" name="taskId" value="${taskId}" />

            <table>
                <tbody>
                <input type="submit" value="Save" class="add-button"> <button onclick="location.href='tasks'" type="button" class="add-button">Cancel</button>

                <tr>
                    <td><label>Статус:</label></td>
                    <td>
                        <select type="text"name="flag" data-selected="${flag}">
                            <option>Не начата</option>
                            <option>В процессе </option>
                            <option>Завершена </option>
                            <option>Отложена </option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>Наименование:</label></td>
                    <td><input type="text" name="title"
                               value="${title}"></td>
                </tr>

                <tr>
                    <td><label>Работа:</label></td>
                    <td><input type="number" name="work_time"
                               value="${work_time}"></td>
                </tr>
                <tr>
                    <td><label>Дата начала:</label></td>
                    <td><input type="date" name="begin_date"
                               value="${begin_date}"></td>
                </tr>
                <tr>
                    <td><label>Дата окончания:</label></td>
                    <td><input type="date" name="end_date"
                               value="${end_date}"></td>
                </tr>

                <tr>
                    <td><label>Наименование проекта:</label></td>
                    <td><input type="number" name="project_id" value="${project_id}" ></td>
                </tr>
                <tr>
                    <td><label>Исполнитель :</label></td>
                    <td><input type="number" name="employee_id" value="${employee_id}" ></td>
                </tr>
                </tbody>
                <br/><br/>

        </form>

    </div>
</div>

</body>
</html>
