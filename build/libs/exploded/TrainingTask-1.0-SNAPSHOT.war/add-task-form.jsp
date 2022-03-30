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
    <form action="tasks" onsubmit="check(event)" method="post" id="form">
      <input type="hidden" name="action" value="/add"/>
      <table>
        <div>
          <input class="add-button" type="submit"  value="Сохранить"><button onclick="window.history.back()" type="button" class="add-button">Отмена</button>
        </div>
        <tbody>
        <tr>
          <td><label>Cтатус:</label></td>
          <td>
            <select name="flag">
              <option>Не начата</option>
              <option>В процессе</option>
              <option>Завершена</option>
              <option>Отложена</option>
            </select>
          </td>
        <tr>
          <td><label>Наименование:</label></td>
          <td><input required ="required" maxlength="50" onkeydown="checkLength('title',50)" type="text" id="title" name="title"></td>
        </tr>
        <tr>
          <td><label>Работа:</label></td>
          <td><input required ="required"  type="number"  id="work_time" max="9223372036854775807" name="work_time" ONKEYUP="this.value=this.value.replace(/[^\d]/,'')"></td>
        </tr>
        <tr>
          <td><label>Начало работы:</label></td>
          <td><input required ="required" min="0001-01-01" max="9999-12-31" type="date" id="begin_date" name="begin_date"> </td>
        </tr>
        <tr>
          <td><label>Конец работы:</label></td>
          <td><input required ="required" min="0001-01-01" max="9999-12-31" type="date" id="end_date" name="end_date" ></td>
        </tr>
        <tr>
          <td><label>Проект:</label></td>
            <td> <select name="project_id" ${thisProjectId != null ? '  disabled="true"' : ''}>
              <option value="null">  </option>
              <c:forEach items="${PROJECT_LIST}" var="projects">
                    <option value="${projects.id}" ${projects.id == thisProjectId ? 'selected="selected"' : ''} >${projects.title} </option>
                </c:forEach>
            </select>
              <input type="hidden" name="project_id" value=${thisProjectId} />
            </td>
        </tr>
        <tr>
          <td><label>Сотрудник:</label></td>
          <td> <select name="employee_id">
            <option value="null">  </option>
            <c:forEach items="${EMPLOYEE_LIST}" var="employees">
              <option value="${employees.id}">  ${employees.surname} ${employees.firstName} ${employees.lastName}</option>
            </c:forEach>
          </select>
        </td>
        </tr>
        </tr>
        </tbody>
      </table>
      <br><br/>
    </form>
  </div>
</div>
<script type='text/javascript'>
  function check(event) {
    const startDt = document.getElementById("begin_date").value;
    const endDt = document.getElementById("end_date").value;
    const title = document.getElementById("title").value;


    if( (new Date(endDt).getTime() < new Date(startDt).getTime()))
    {
      event.preventDefault();
      alert("Дата начала работы не может быть позже даты окончания работы");
    }else if(title.trim() == ''){
      event.preventDefault();
      alert("Наиминование не может состоять только из пробелов");
    }
  }

  // Message if length of the field is more than maxLength symbols
  function checkLength(fieldName,maxLength) {
    const len = document.getElementById(fieldName).value.length;

    if( len == maxLength){
      alert("Превышена допустимая длина поля " + maxLength + " символов");
    }
  }
</script>
</body>
</html>

