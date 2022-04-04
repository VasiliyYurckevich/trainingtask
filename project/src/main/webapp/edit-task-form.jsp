<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
    <li style="font-family: Arial"><a class="choose"  href="task">Задачи</a></li>
    <li style="font-family: Arial"><a href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
    <div id="container">
        <h3>Редактировать задачу</h3>
        <form action="task" onsubmit="check(event)" method="post" id ="form">
            <input type="hidden" name="action" value="/update" />
            <input type="hidden" name="taskId" value="${taskId}" />
            <table>
                <tbody>
                <input class="add-button" type="submit"  value="Сохранить"><button onclick="window.history.back()" type="button" class="add-button">Отмена</button>
                <tr>
                    <td><label>Статус:</label></td>
                    <td>
                        <select type="text" name="status" data-selected="${status}">
                            <option ${status == "Не начата"  ? 'selected="selected"' : ''}>Не начата</option>
                            <option ${status == "В процессе"  ? 'selected="selected"' : ''}>В процессе</option>
                            <option ${status == "Завершена"  ? 'selected="selected"' : ''}>Завершена</option>
                            <option ${status == "Отложена"  ? 'selected="selected"' : ''}>Отложена</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>Наименование:</label></td>
                    <td><input type="text" maxlength="50" required="required" id="title" name="title" oninput="checkLength('title',50)" value="${title}"></td>
                </tr>
                <tr>
                    <td><label>Работа:</label></td>
                    <td><input required ="required" type="number" oninvalid="this.setCustomValidity('Ваше число выходит за рамки диапазона!Введите число в дапазоне от 0 до 999 999 999 999 999 999')"  max="999999999999999999" id="workTime"  name="workTime" ONKEYUP="this.value=this.value.replace(/[^\d]/,'')"
                               value="${workTime}"></td>
                </tr>
                <tr>
                    <td><label>Дата начала:</label></td>
                    <td><input required ="required" type="text" pattern="^[[0-9]{4}[.](?:(?:0[1-9]|1[0-2])[.](?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])[.](?:30))|(?:(?:0[13578]|1[02])[.]31))$"  oninput="this.setCustomValidity('')" oninvalid="this.setCustomValidity('Введите дату в формате ГГГГ.ММ.ДД')" id="beginDate" name="beginDate"
                               value="${fn:replace(beginDate,"-",".")}"></td>
                </tr>
                <tr>
                    <td><label>Дата окончания:</label></td>
                    <td><input required ="required" type="text" pattern="^[[0-9]{4}[.](?:(?:0[1-9]|1[0-2])[.](?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])[.](?:30))|(?:(?:0[13578]|1[02])[.]31))$"  oninput="this.setCustomValidity('')" oninvalid="this.setCustomValidity('Введите дату в формате ГГГГ.ММ.ДД')" id="endDate" name="endDate"
                               value="${fn:replace(beginDate,"-",".")}"></td>
                </tr>

                <tr>
                    <td><label>Наименование проекта:</label></td>
                    <td> <select name="projectId">
                        <option value="null">  </option>
                        <c:forEach items="${PROJECT_LIST}" var="projects">
                            <option value="${projects.id}" ${projects.id == projectId ? 'selected="selected"' : ''}>${projects.title}</option>
                        </c:forEach>
                    </select>
                </tr>
                <tr>
                    <td><label>Сотрудник:</label></td>
                    <td> <select name="employeeId" >
                        <option value="null">  </option>
                        <c:forEach items="${EMPLOYEE_LIST}" var="employees">
                            <option value="${employees.id}" ${employees.id == employeeId ? 'selected="selected"' : ''}>${employees.surname} ${employees.firstName} ${employees.patronymic}</option>
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
<script type='text/javascript'>

    function check(event) {
        const beginDt = document.getElementById("beginDate").value.replace('.','-');
        const endDt = document.getElementById("endDate").value.replace('.','-');
        const title = document.getElementById("title").value;

        if( (new Date(endDt).getTime() < new Date(beginDt).getTime()))
        {
            event.preventDefault();
            alert("Дата начала работы не может быть позже даты окончания работы");
        }else if(title.trim() == ''){
            event.preventDefault();
            alert("Наиминование не может состоять только из пробелов");
        }
    }

    function checkLength(fieldName,maxLength) {
        const len = document.getElementById(fieldName).value.length;

        if( len == maxLength){
            alert("Превышена допустимая длина поля " + maxLength + " символов");
        }
    }
</script>
</body>
</html>
