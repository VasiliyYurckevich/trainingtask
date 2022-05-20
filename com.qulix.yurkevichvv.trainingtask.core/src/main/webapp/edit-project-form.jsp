<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>

<!DOCTYPE html>
<html lang="ru">
    <head>
      <title>Редактировать проект</title>

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
                <h3 >Редактировать проект</h3>
                <form action="projects" onsubmit="check(event)" method="post">
                    <input type="hidden" name="action" value="/update" />
                    <input type="hidden" name="projectId" value="${projectId}"/>

                    <table>
                        <div>
                            <input type="submit" value="Сохранить" name="submitButton" id="submitButton" class="add-button"> <button id="cancelButton" name="cancelButton" onclick="location.href='projects'" type="button" class="add-button">Отмена</button>
                        </div>
                        <tbody>
                            <tr>
                                <td><label>Наименование:</label></td>
                                <td><input required="required"  type="text" maxlength="50" oninput="checkLength('title',50)"   name="title" id="title"
                                 value="${fn:escapeXml(title)}"></td>
                            </tr>
                            <tr>
                                <td><label>Описание:</label></td>
                                <td><input type="text" required="required"  maxlength="250" name="description" id="description" oninput="checkLength('description',250)"
                                 value="${fn:escapeXml(description)}"></td>
                            </tr>
                        </tbody>
                    </table>
                    <tbody>
                        <div>
                            <h3>Задачи проекта</h3>
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
                            <c:param name="action" value="/addTaskForm"/>
                            <c:param name="projectId" value="${projectId}"/>
                            </c:url>

                            <c:forEach var="tempTask" items="${TASKS_LIST}" varStatus="theCount">

                                <c:url var="editLink" value="/projects">
                                <c:param name="action" value="/editTaskForm"/>
                                <c:param name="taskId" value="${tempTask.id}"/>
                                <c:param name="numberInList" value="${theCount.index}"/>
                                </c:url>

                                <c:url var="deleteLink" value="/projects">
                                <c:param name="action" value="/deleteTaskInProject"/>
                                <c:param name="numberInList" value="${theCount.index}"/>
                                </c:url>

                                <tr>
                                    <td> ${tempTask.status}</td>
                                    <td> ${fn:escapeXml(tempTask.title)} </td>
                                    <td> ${tempTask.workTime} </td>
                                    <td> ${tempTask.beginDate}</td>
                                    <td> ${tempTask.endDate} </td>
                                    <td> ${fn:escapeXml(title)}</td>
                                    <td>${fn:escapeXml(EMPLOYEE_IN_TASKS_LIST.get(theCount.index).surname)} ${fn:escapeXml(EMPLOYEE_IN_TASKS_LIST.get(theCount.index).firstName)} ${fn:escapeXml(EMPLOYEE_IN_TASKS_LIST.get(theCount.index).patronymic)}</td>
                                    <td>
                                        <a href="${editLink}">Редактировать</a>
                                        <a style="padding-left: 15px" href="${deleteLink}" onclick="if (!(confirm('Вы уверены?'))) return false">Удалить</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    <form>
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
              alert("Превышена допустимая длина поля: " + maxLength + " символов");
            }
          }
        </script>
    </body>
</html>
