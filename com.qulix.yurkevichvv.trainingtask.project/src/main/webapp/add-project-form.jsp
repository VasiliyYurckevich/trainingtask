<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
  <li style="font-family: Arial"><a href="task">Задачи</a></li>
  <li style="font-family: Arial"><a href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
  <div id="container">
    <h3>Добавить проект</h3>

    <form action="projects" onsubmit="check(event)" method="post" >
      <input type="hidden" name="action" value="/add" />
      <table>
        <tbody>
        <tr>
          <td><label>Наименование:</label></td>
          <td><input required ="required" maxlength="50" oninput="checkLength('title',50)" type="text" id="title" name="title"></td>
        </tr>
        <tr>
          <td><label>Описание:</label></td>
          <td><input  required ="required" maxlength="250" oninput="checkLength('description',250)"  type="text" id="description"  name="description"></td>
        </tr>
        </tbody>
      </table>
      <br><br/>
      <input type="submit" value="Сохранить" name="submitButton" id="submitButton" class="add-button"> <button id="cancelButton" name="cancelButton" onclick="javascript:history.back()" type="button" class="add-button">Отмена</button>
    </form>

  </div>
</div>
<script type='text/javascript'>
  function check(event) {
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;

    if (title.trim() == "" || description.trim() == "") {
      event.preventDefault();
      alert("Заполните все поля!Поля не могут быть пустыми и состоять только из пробелов");
    }else {
      this.submitButton.disabled = true;
      this.cancelButton.disabled = true;
    }
  }

  // Message if length of the field is more than maxLength symbols
  function checkLength(fieldName,maxLength) {
    const len = document.getElementById(fieldName).value.length;

    if( len == maxLength){
      alert("Достигнута допустимая длина поля: " + maxLength + " символов");
    }
  }
</script>
</body>
</html>
