<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title> Добавить сотрудника </title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>

        <my:mainMenu></my:mainMenu>

        <div class="chief">
            <div id="container" class="main">
                <h3>Добавить сотрудника</h3>
                <form action="employees" method="post">
                    <input type="hidden" name="action" value="/add"/>
                    <input type="submit" value="Сохранить" name="submitButton" id="submitButton" class="add-button">
                    <button onclick="location.href='employees.jsp'" type="button"
                            id="cancelButton" name="cancelButton" class="add-button">Отмена
                    </button>

                    <div class="field">
                        <label>Фамилия:</label>
                        <input name="surname" id="surname" value="${fn:escapeXml(surname)}">
                        <h4>${ERRORS.get("surname")}</h4>
                    </div>
                    <div class="field">
                        <label>Имя:</label>
                        <input type="text" name="firstName" id="firstName" value="${fn:escapeXml(firstName)}">
                        <h4>${ERRORS.get("firstName")}</h4>
                    </div>
                    <div class="field">
                        <label>Отчество:</label>
                        <input type="text" name="patronymic" id="patronymic" value="${fn:escapeXml(patronymic)}">
                        <h4>${ERRORS.get("patronymic")}</h4>
                    </div>
                    <div class="field">
                        <label>Должность:</label>
                        <input type="text" name="post" id="post" value="${fn:escapeXml(post)}">
                        <h4>${ERRORS.get("post")}</h4>
                    </div>
                </form>
            </div>
        </div>
        <script>
            let forms = document.querySelector('form');
            forms.addEventListener('submit', function(){
                let btn = this.querySelector("input[type=submit], button[type=submit]");
                btn.disabled = true;
            });
        </script>
    </body>
</html>
