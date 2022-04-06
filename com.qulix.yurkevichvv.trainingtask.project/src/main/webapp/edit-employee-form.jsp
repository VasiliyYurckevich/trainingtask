<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <li style="font-family: Arial"><a href="projects">Проекты</a></li>
    <li style="font-family: Arial"><a href="task">Задачи</a></li>
    <li style="font-family: Arial"><a class="choose" href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
    <div id="container">
        <h3>Редактировать проект</h3>
        <form action="employees" onsubmit="check(event)" method="post">
            <input type="hidden" name="action" value="/update" />
            <input type="hidden" name="employeeId" value="${employeeId}" />

            <table>
                <div>
                    <input type="submit" value="Сохранить" class="add-button"> <button onclick="javascript:history.back()" type="button" class="add-button">Отмена</button>
                </div>
                <tbody>
                <tr>
                    <td><label>Фамилия:</label></td>
                    <td><input required="required"  type="text" maxlength="50" oninput="checkLength('surname',50)" name="surname" id="surname"
                               value="${fn:escapeXml(surname)}"></td>
                </tr>
                <tr>
                    <td><label>Имя:</label></td>
                    <td><input type="text" required="required"  maxlength="50" oninput="checkLength('firstName',50)" name="firstName" id="firstName"
                               value="${fn:escapeXml(firstName)}"></td>
                </tr>
                <tr>
                    <td><label>Отчество:</label></td>
                    <td><input type="text"  required="required" maxlength="50"  name="patronymic" id="patronymic" oninput="checkLength('patronymic',50)"
                               value="${fn:escapeXml(patronymic)}"></td>
                </tr>
                <tr>
                    <td><label>Должность:</label></td>
                    <td><input type="text" required="required" maxlength="50"  name="post" id="post" oninput="checkLength('post',50)"
                               value="${fn:escapeXml(post)}"></td>
                </tr>
                </tbody>
                <br/><br/>
            </table>
        </form>

    </div>
</div>
<script type='text/javascript'>
    function check(event) {
        const surname = document.getElementById("surname").value;
        const firstName = document.getElementById("firstName").value;
        const patronymic = document.getElementById("patronymic").value;
        const post = document.getElementById("post").value;

        if (surname.trim() == ''|| firstName.trim() == ''|| patronymic.trim() == ''|| post.trim() == '') {
            event.preventDefault();
            alert("Заполните все поля!Поля не могут быть пустыми");
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
