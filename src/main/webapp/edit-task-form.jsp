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
                <input type="submit" value="Сохранить" class="add-button"> <button onclick="javascript:history.back()" type="button" class="add-button">Отмена</button>

                <tr>
                    <td><label>Статус:</label></td>
                    <td>
                        <select type="text"name="flag" data-selected="${flag}">
                            <option ${flag == "Не начата"  ? 'selected="selected"' : ''}>Не начата</option>
                            <option ${flag == "В процессе"  ? 'selected="selected"' : ''}>В процессе</option>
                            <option ${flag == "Завершена"  ? 'selected="selected"' : ''}>Завершена</option>
                            <option ${flag == "Отложена"  ? 'selected="selected"' : ''}>Отложена</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>Наименование:</label></td>
                    <td><input type="text"maxlength="50" required="required"  name="title"
                               value="${title}"></td>
                </tr>
                <tr>
                    <td><label>Работа:</label></td>
                    <td><input type="number" min="-2,147,483,648" required="required" max="2,147,483,647" name="work_time" ONKEYUP="this.value=this.value.replace(/[^\d]/,'')"
                               value="${work_time}"></td>
                </tr>
                <tr>
                    <td><label>Дата начала:</label></td>
                    <td><input type="date" min="0001-01-01" max="9999-12-31" name="begin_date"
                               value="${begin_date}"></td>
                </tr>
                <tr>
                    <td><label>Дата окончания:</label></td>
                    <td><input type="date"  min="0001-01-01" max="9999-12-31" name="end_date"
                               value="${end_date}"></td>
                </tr>

                <tr>
                    <td><label>Наименование проекта:</label></td>
                    <td> <select name="project_id">
                        <c:forEach items="${PROJECT_LIST}" var="projects">
                            <option value="${projects.id}" ${projects.id == project_id ? 'selected="selected"' : ''}>${projects.title}</option>
                        </c:forEach>
                    </select>
                </tr>
                <tr>
                    <td><label>Сотрудник:</label></td>
                    <td> <select name="employee_id" >
                        <option value="null">  </option>
                        <c:forEach items="${EMPLOYEE_LIST}" var="employees">
                            <option value="${employees.id}" ${employees.id == employee_id ? 'selected="selected"' : ''}>${employees.surname} ${employees.firstName} ${employees.lastName}</option>
                        </c:forEach>
                    </select>
                    </td>
                </tr>
                </tbody>
                <br/><br/>
                </table>
        </form>
    </div>
</div>
</body>
</html>
