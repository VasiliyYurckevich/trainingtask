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

        <div class="chief">
            <div id="container" class="main">
                <h3>Добавить проект</h3>
                <form action="projects" method="post" >
                    <input type="hidden" name="action" value="/add" />
                    <input type="submit" value="Сохранить" name="submitButton" id="submitButton" class="add-button">
                    <button id="cancelButton" name="cancelButton" onclick="location.href='projects'"
                            type="button" class="add-button">
                        Отмена
                    </button>

                    <div class="field">
                        <label>Наименование:</label>
                        <input id="titleProject" name="titleProject" value="${fn:escapeXml(titleProject)}">
                        <a class = "feedback">${ERRORS.get("titleProject")}</a>
                    </div>
                    <div class="field">
                        <label>Описание:</label>
                        <input id="description" name="description" value="${fn:escapeXml(description)}">
                        <a class = "feedback">${ERRORS.get("description")}</a>
                    </div>
                </form>
            </div>
        </div>
        <script type="text/javascript" src="blocker.js"></script>
    </body>
</html>
