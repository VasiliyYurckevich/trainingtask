<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">

<head>
    <title> Добавить сотрудника </title>
    <link type="text/css" rel="stylesheet" href="css/nav-bar.css">
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>

<ul>
    <li style="font-family: Arial"><a href="projects">Проекты</a></li>
    <li style="font-family: Arial"><a href="task">Задачи</a></li>
    <li style="font-family: Arial"><a class="choose" href="employees">Сотрудники</a></li>
</ul>

<div style="padding:20px; margin-top:50px;height:600px;">
    <div id="container">
        <h3>Добавить сотрудника</h3>
        <form action="employees" onsubmit="check(event)" method="post">
            <input type="hidden"  name="action" value="/add" />
            <table>
                <tbody>
                <tr>
                    <td><label>Фамилия:</label></td>
                    <td><input required ="required"  id="surname" maxlength="50" oninput="checkLength('surname',50)" type="text" name="surname"></td>
                </tr>
                <tr>
                    <td><label>Имя:</label></td>
                    <td><input  required ="required"  id="firstName" maxlength="50" type="text" oninput="checkLength('firstName',50)" name="firstName" ></td>
                </tr>
                <tr>
                    <td><label>Отчество:</label></td>
                    <td><input required ="required"  id="patronymic"  maxlength="50" oninput="checkLength('patronymic',50)"  type="text" name="patronymic"></td>
                </tr>
                <tr>
                    <td><label>Должность:</label></td>
                    <td><input  required ="required" id="post"  maxlength="50" oninput="checkLength('post',50)" type="text" name="post" ></td>
                </tr>
                </tbody>
            </table>

            <br/><br/>
            <input type="submit" value="Сохранить" class="add-button"> <button onclick="javascript:history.back()" type="button" class="add-button">Отмена</button>
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
            alert("Достигнута допустимая длина поля " + maxLength-1 + " символов");
        }
    }
</script>
</body>
</html>
