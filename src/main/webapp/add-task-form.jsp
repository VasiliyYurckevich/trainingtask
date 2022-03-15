<%--
  Created by IntelliJ IDEA.
  User: yurkevichvv
  Date: 14.03.2022
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ru">

<head>
  <title>Добавить задачу</title>

  <link type="text/css" rel="stylesheet" href="css/nav-bar.css">
  <link type="text/css" rel="stylesheet" href="css/style.css">

</head>
<body>

<ul>
  <li style="font-family: Arial"><a href="projects">Проекты</a></li>
  <li style="font-family: Arial"><a class="choose" href="tasks">Задачи</a></li>
  <li style="font-family: Arial"><a href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
  <div id="container">
    <h3>Добавить задачу</h3>
    <form action="tasks" method="get">
      <input type="hidden" name="action" value="/add" />
      <table>
        <div>
           <input type="submit" value="Save" class="add-button"> <button onclick="location.href='tasks'" type="button" class="add-button">Cancel</button>
        </div>
        <tbody>
        <tr>
          <td><label>Flag:</label></td>
          <td>
            <select name="flag">
              <option></option>
              <option>Не начата</option>
              <option>В процессе </option>
              <option>Завершена </option>
              <option>Отложена </option>
            </select>
          </td>
        <tr>
          <td><label>Наименование:</label></td>
          <td><input required ="required" type="text" name="title"></td>
        </tr>
        <tr>
          <td><label>Работа:</label></td>
          <td><input required ="required"  type="number" name="work_time" ONKEYUP="this.value=this.value.replace(/[^\d]/,'')"></td>
        </tr>
        <tr>
          <td><label>Начало работы:</label></td>
          <td><input required ="required"  type="date" name="begin_date"></td>
        </tr>
        <tr>
          <td><label>Конец работы:</label></td>
          <td><input required ="required"  type="date" name="end_date"></td>
        </tr>
        <tr>
          <td><label>Проект:</label></td>
          <td><input required ="required"  type="number" name="project_id" ONKEYUP="this.value=this.value.replace(/[^\d]/,'')"></td>
        </tr>
        <tr>
          <td><label>Сотрудник:</label></td>
          <td><input  required ="required"  type="number" name="employee_id"  ONKEYUP="this.value=this.value.replace(/[^\d]/,'')"></td>
        </tr>

        </tr>
        </tbody>
      </table>
      <br/><br/>
    </form>
  </div>
</div>

</body>
</html>