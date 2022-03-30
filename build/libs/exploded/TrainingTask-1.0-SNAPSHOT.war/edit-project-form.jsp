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
  <li style="font-family: Arial"><a class="choose"  href="projects">Проекты</a></li>
  <li style="font-family: Arial"><a href="tasks">Задачи</a></li>
  <li style="font-family: Arial"><a href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
  <div id="container">
    <h3 >Редактировать проект</h3>

    <form action="projects" onsubmit="check(event)" method="post">

      <input type="hidden" name="action" value="/update" />
      <input type="hidden" name="projectId" value="${projectId}" />

      <table>
        <div>
          <input type="submit" value="Сохранить" class="add-button"> <button onclick="location.href='projects'" type="button" class="add-button">Отмена</button>
        </div>
        <tbody>
        <tr>
          <td><label>Наименование:</label></td>
          <td><input required="required"  type="text" maxlength="50" onkeydown="checkLength('title',50)"   name="title" id="title"
                     value="${title}"></td>
        </tr>
        <tr>
          <td><label>Описание:</label></td>
          <td><input type="text" required="required"  maxlength="250" name="discription" id="discription" onkeydown="checkLength('discription',250)"
                     value="${discription}"></td>
        </tr>
        </tbody>
      </table>
        <tbody>
        <div>
          <h4>Задачи проекта</h4>
        </div>
        <table>
        <tr>
          <th>Статус</th>
          <th>Наименование</th>
          <th>Работа</th>
          <th>Дата начала</th>
          <th>Дата окончания</th>
          <th>Проект</th>
          <th>Исполнитель</th>
          <th>Действия</th>
        </tr>
          <c:url var="addLink" value="/projects">
            <c:param name="action" value="/addTask"/>
            <c:param name="projectId" value="${projectId}"/>
          </c:url>
         <c:forEach var="tempTask" items="${TASKS_LIST}" varStatus="theCount">

          <c:url var="editLink" value="/tasks">
            <c:param name="action" value="/edit"/>
            <c:param name="taskId" value="${tempTask.id}"/>
          </c:url>
          <c:url var="deleteLink" value="/tasks">
            <c:param name="action" value="/delete"/>
            <c:param name="taskId" value="${tempTask.id}"/>
          </c:url>



          <tr>
            <td> ${tempTask.flag}</td>
            <td> ${tempTask.title} </td>
            <td> ${tempTask.workTime} </td>
            <td> ${tempTask.beginDate}</td>
            <td> ${tempTask.endDate} </td>
            <td> ${title}</td>
            <td>${EMP_LIST.get(theCount.index).surname} ${EMP_LIST.get(theCount.index).firstName} ${EMP_LIST.get(theCount.index).lastName}</td>
            <td>
              <a href="${editLink}">Редактировать</a>

              <a style="padding-left: 15px" href="${deleteLink}"
                 onclick="if (!(confirm('Вы уверены?'))) return false">Удалить</a>
            </td>
          </tr>
        </c:forEach>
        </table>
        <form></form>
        <a href="${addLink}">Добавить задачу</a>
        </form>
        </tbody>
      <br/><br/>
    </form>
  </div>
</div>
<script type='text/javascript'>
  //Check empty fields
  function check(event) {
    const title = document.getElementById("title").value;
    const discription = document.getElementById("discription").value;


    if (title.trim() == "" || discription.trim() == "") {
      event.preventDefault();
      alert("Заполните все поля!Поля не могут быть пустыми и состоять только из пробелов");
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
