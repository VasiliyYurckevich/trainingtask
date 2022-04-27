<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html>
<html lang="ru">

<head>
    <title>Добавить задачу</title>

    <link type="text/css" rel="stylesheet" href="css/nav-bar.css">
    <link type="text/css" rel="stylesheet" href="css/style.css">

</head>
<body>

<ul>
    <li style="font-family: Arial"><a class="choose" href="projects">Проекты</a></li>
    <li style="font-family: Arial"><a href="task">Задачи</a></li>
    <li style="font-family: Arial"><a href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
    <div id="container">
        <h3>Добавить задачу</h3>
        <form action="task" onsubmit="check(event)" method="post" id="form">
            <input type="hidden" name="action" value="/newTaskInProject"/>
            <table>
                <div>
                    <input class="add-button" type="submit"  value="Сохранить"><button onclick="window.history.back()" type="button" class="add-button">Отмена</button>
                </div>
                <tbody>
                <tr>
                    <td><label>Cтатус:</label></td>
                    <td>
                        <select name="status">
                            <option>Не начата</option>
                            <option>В процессе</option>
                            <option>Завершена</option>
                            <option>Отложена</option>
                        </select>
                    </td>
                <tr>
                    <td><label>Наименование:</label></td>
                    <td><input required ="required" maxlength="50" oninput="checkLength('title',50)"  type="text" id="title" name="title"></td>
                </tr>
                <tr>
                    <td><label>Работа:</label></td>
                    <td><input required ="required" type="number"  max="999999999999999999" id="workTime"  name="workTime" ONKEYUP="this.value=this.value.replace(/[^\d]/,'')"></td>
                </tr>
                <tr>
                    <td><label>Дата начала(ГГГГ-ММ-ДД):</label></td>
                    <td><input required ="required" type="text" pattern="^((((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(0[13578]|10|12)([-])(0[1-9]|[12][0-9]|3[01]))|(((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(0[469]|11)([-])([0][1-9]|[12][0-9]|30))|(((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(02)([-])(0[1-9]|1[0-9]|2[0-8]))|(([02468][048]00)([-])(02)([-])(29))|(([13579][26]00)([-])(02)([-])(29))|(([0-9][0-9][0][48])([-])(02)([-])(29))|(([0-9][0-9][2468][048])([-])(02)([-])(29))|(([0-9][0-9][13579][26])([-])(02)([-])(29)))$"
                               oninput="this.setCustomValidity('')" oninvalid="this.setCustomValidity('Введите дату в формате ГГГГ-ММ-ДД в диапазоне между 1900-01-01 и 2999-12-31.')"  id="beginDate" name="beginDate"> </td>
                </tr>
                <tr>
                    <td><label>Дата окончания(ГГГГ-ММ-ДД):</label></td>
                    <td><input required ="required" type="text" pattern="^((((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(0[13578]|10|12)([-])(0[1-9]|[12][0-9]|3[01]))|(((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(0[469]|11)([-])([0][1-9]|[12][0-9]|30))|(((19[0-9][0-9])|(2[0-9][0-9][0-9]))([-])(02)([-])(0[1-9]|1[0-9]|2[0-8]))|(([02468][048]00)([-])(02)([-])(29))|(([13579][26]00)([-])(02)([-])(29))|(([0-9][0-9][0][48])([-])(02)([-])(29))|(([0-9][0-9][2468][048])([-])(02)([-])(29))|(([0-9][0-9][13579][26])([-])(02)([-])(29)))$"
                               oninput="this.setCustomValidity('')" oninvalid="this.setCustomValidity('Введите дату в формате ГГГГ-ММ-ДД в диапазоне между 1900-01-01 и 2999-12-31.')" id="endDate" name="endDate" ></td>
                </tr>
                <tr>
                    <td><label>Проект:</label></td>
                    <td> <select name="projectId" disabled="true">
                        <option value="null">  </option>
                        <c:forEach items="${PROJECT_LIST}" var="projects">
                            <option value="${projects.id}" ${projects.id == thisProjectId ? 'selected="selected"' : ''} >${fn:escapeXml(projects.title)} </option>
                        </c:forEach>
                    </select>
                        <input type="hidden" name="projectId" value=${thisProjectId} />
                    </td>
                </tr>
                <tr>
                    <td><label>Сотрудник:</label></td>
                    <td> <select name="employeeId">
                        <option value="null">  </option>
                        <c:forEach items="${EMPLOYEE_LIST}" var="employees">
                            <option value="${employees.id}">  ${fn:escapeXml(employees.surname)} ${fn:escapeXml(employees.firstName)} ${fn:escapeXml(employees.patronymic)}</option>
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
        const beginDt = document.getElementById("beginDate").value;
        const endDt = document.getElementById("endDate").value;
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

