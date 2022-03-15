<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">

<head>
  <title> Добавить проект</title>

  <link type="text/css" rel="stylesheet" href="css/nav-bar.css">
  <link type="text/css" rel="stylesheet" href="css/style.css">

</head>
<body>

<ul>
  <li style="font-family: Arial"><a class="choose"  href="projects">Проекты</a></li>
  <li style="font-family: Arial"><a href="tasks">Задачи</a></li>
  <li style="font-family: Arial"><a href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
  <div id="container">
    <h3>Добавить проект</h3>

    <form action="projects" method="get">
      <input type="hidden" name="action" value="/add" />
      <table>
        <tbody>
        <tr>
          <td><label>Наименование:</label></td>
          <td><input required ="required" maxlength="50"  type="text" name="title"></td>
        </tr>
        <tr>
          <td><label>Описание:</label></td>
          <td><input  required ="required" maxlength="250"   type="text" name="discription" ></td>
        </tr>
        </tbody>
      </table>
      <br><br/>
      <input type="submit" value="Save" class="add-button"> <button onclick="javascript:history.back()" type="button" class="add-button">Cancel</button>
    </form>

  </div>
</div>

</body>
</html>
