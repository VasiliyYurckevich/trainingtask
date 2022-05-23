<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title> Добавить проект</title>

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
                <h3>Добавить проект</h3>
                <form action="projects"  method="post" >
                    <input type="hidden" name="action" value="/add" />
                    <table>
                        <tbody>
                        <tr>
                            <td><label>Наименование:</label></td>
                            <td><input id="titleProject" name="titleProject" value="${titleProject}"></td>
                            <td>
                                <error>${ERRORS.get(0)}</error>
                            </td>
                        </tr>
                        <tr>
                            <td><label>Описание:</label></td>
                            <td><input id="description"  name="description" value="${description}"></td>
                            <td>
                                <error>${ERRORS.get(1)}</error>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <br><br/>
                    <input type="submit" value="Сохранить" name="submitButton" id="submitButton" class="add-button"> <button id="cancelButton" name="cancelButton" onclick="javascript:history.back()" type="button" class="add-button">Отмена</button>
                </form>

            </div>
        </div>

    </body>
</html>
