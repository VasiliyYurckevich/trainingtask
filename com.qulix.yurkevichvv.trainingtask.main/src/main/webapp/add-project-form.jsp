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

                    <table>
                        <tbody>
                        <tr>
                            <td>
                                <label>Наименование:</label>
                            </td>
                            <td>
                                <input id="titleProject" name="titleProject" value="${fn:escapeXml(titleProject)}">
                            </td>
                            <td>
                                <h4>${ERRORS.get("titleProject")}</h4>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Описание:</label>
                            </td>
                            <td>
                                <input id="description" name="description" value="${fn:escapeXml(description)}">
                            </td>
                            <td>
                                <h4>${ERRORS.get("description")}</h4>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <input type="submit" value="Сохранить" name="submitButton" id="submitButton" class="add-button">
                    <button id="cancelButton" name="cancelButton" onclick="history.back()"
                        type="button" class="add-button">
                        Отмена
                    </button>
                </form>
            </div>
        </div>
        <script>
            var forms = document.querySelector('form');
            forms.addEventListener('submit', function(){
                var btn = this.querySelector("input[type=submit], button[type=submit]");
                btn.disabled = true;
            });
        </script>
    </body>
</html>
