<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">

<head>
  <title> Добавить проект</title>

  <link type="text/css" rel="stylesheet" href="css/menu-navigation-bar.css">
  <link type="text/css" rel="stylesheet" href="css/style.css">
  <link type="text/css" rel="stylesheet" href="css/temp.css">

</head>
<body>

<ul>
  <li style="font-family: Arial"><a class="active"  href="projects">Проекты</a></li>
  <li style="font-family: Arial"><a href="tasks">Задачи</a></li>
  <li style="font-family: Arial"><a href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
  <div id="container">
    <h3>Редактировать проект</h3>

    <form action="projects" method="get">

      <input type="hidden" name="action" value="/update" />
      <input type="hidden" name="projectId" value="${projectId}" />

      <table>
        <tbody>
        <tr>
          <td><label>ID:</label></td>
          <td><input style="background-color: #f6f6f6" type="number" value="${projectId}" name="" readonly></td>
        </tr>
        <tr>
          <td><label>Title:</label></td>
          <td><input type="text" name="title"
                     value="${title}"></td>
        </tr>

          <td><label>Description:</label></td>
          <td><input type="text" name="discription"
                     value="${discription}"></td>
        </tr>
        </tbody>
      <br/><br/>
      <input type="submit" value="Save" class="add-button"> <button onclick="location.href='projects'" type="button" class="add-button">Cancel</button>

    </form>

  </div>
</div>

</body>
</html>
