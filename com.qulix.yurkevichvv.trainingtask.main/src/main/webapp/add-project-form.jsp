<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title> Добавить проект</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>

        <my:mainMenu></my:mainMenu>

        <div>
            <div id="container">
                <h3>Добавить проект</h3>
                <form action="projects" method="post" >
                    <input type="hidden" name="action" value="/add" />
                    <input type="submit" value="Сохранить" name="submitButton" id="submitButton" class="add-button">
                    <button id="cancelButton" name="cancelButton" onclick="location.href='projects'"
                            type="button" class="add-button">
                        Отмена
                    </button>

                    <div>
                        <label>Наименование:</label>
                        <input id="titleProject" name="titleProject" value="${fn:escapeXml(titleProject)}">
                        <h4>${ERRORS.get("titleProject")}</h4>
                    </div>
                    <div>
                        <label>Описание:</label>
                        <input id="description" name="description" value="${fn:escapeXml(description)}">
                        <h4>${ERRORS.get("description")}</h4>
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
