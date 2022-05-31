<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Редактировать сотрудника</title>

        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>
    <body>

        <ul>
            <li>
                <a href="projects">Проекты</a>
            </li>
            <li>
                <a href="task">Задачи</a>
            </li>
            <li>
                <a class="choose" href="employees">Сотрудники</a>
            </li>
        </ul>

        <div >
            <div id="container">
                <h3>Редактировать сотрудника</h3>
                <form action="employees"  method="post">
                    <input type="hidden" name="action" value="/update" />
                    <input type="hidden" name="employeeId" value="${employeeId}" />
                    <table>
                        <div>
                            <input type="submit" value="Сохранить" name="submitButton" id="submitButton" class="add-button">
                            <button id="cancelButton" name="cancelButton" onclick="history.back()"
                                    type="button" class="add-button">
                                Отмена
                            </button>
                        </div>
                        <tbody>
                            <tr>
                                <td>
                                    <label>Фамилия:</label>
                                </td>
                                <td>
                                    <input  name="surname" id="surname" value="${fn:escapeXml(surname)}">
                                </td>
                                <td>
                                    <error>${ERRORS.get(0)}</error>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Имя:</label>
                                </td>
                                <td>
                                    <input type="text"  name="firstName" id="firstName" value="${fn:escapeXml(firstName)}">
                                </td>
                                <td>
                                    <error>${ERRORS.get(1)}</error>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Отчество:</label>
                                </td>
                                <td>
                                    <input type="text"   name="patronymic" id="patronymic" value="${fn:escapeXml(patronymic)}">
                                </td>
                                <td>
                                    <error>${ERRORS.get(2)}</error>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Должность:</label>
                                </td>
                                <td>
                                    <input type="text"   name="post" id="post" value="${fn:escapeXml(post)}">
                                </td>
                                <td>
                                    <error>${ERRORS.get(3)}</error>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>
