<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Редактировать сотрудника</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>

        <my:mainMenu></my:mainMenu>

        <div>
            <div id="container">
                <h3>Редактировать сотрудника</h3>
                <form action="employees" method="post">
                    <input type="hidden" name="action" value="/update"/>
                    <input type="hidden" name="employeeId" value="${employeeId}"/>

                    <table>
                        <div>
                            <input type="submit" value="Сохранить"
                                name="submitButton" id="submitButton" class="add-button">
                            <button id="cancelButton" name="cancelButton" onclick="location.href='employees.jsp'"
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
                                    <input name="surname" id="surname" value="${fn:escapeXml(surname)}">
                                </td>
                                <td>
                                    <h4>${ERRORS.get("surname")}</h4>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Имя:</label>
                                </td>
                                <td>
                                    <input type="text" name="firstName" id="firstName" value="${fn:escapeXml(firstName)}">
                                </td>
                                <td>
                                    <h4>${ERRORS.get("firstName")}</h4>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Отчество:</label>
                                </td>
                                <td>
                                    <input type="text" name="patronymic" id="patronymic" value="${fn:escapeXml(patronymic)}">
                                </td>
                                <td>
                                    <h4>${ERRORS.get("patronymic")}</h4>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Должность:</label>
                                </td>
                                <td>
                                    <input type="text" name="post" id="post" value="${fn:escapeXml(post)}">
                                </td>
                                <td>
                                    <h4>${ERRORS.get("post")}</h4>
                                </td>
                            </tr>
                        </tbody>
                    </table>
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
