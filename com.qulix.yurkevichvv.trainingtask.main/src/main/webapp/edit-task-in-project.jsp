<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Редактировать задачу</title>

        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>

        <my:mainMenu></my:mainMenu>

        <div>
            <div id="container">
                <h3>Редактировать задачу</h3>
                <form action="tasks" method="post" >
                    <input type="hidden" name="action" value="/updateTaskInProject" />
                    <input type="hidden" name="taskId" value="${taskId}" />

                    <table>
                        <tbody>
                            <input class="add-button" type="submit" name="submitButton" id="submitButton" value="Сохранить">
                            <button id="cancelButton" name="cancelButton" onclick="window.history.back()"
                                type="button" class="add-button">
                                Отмена
                            </button>
                            <tr>
                                <td>
                                    <label>Статус:</label>
                                </td>
                                <td>
                                    <select name="status" data-selected="${status}">
                                        <c:forEach items="${STATUS_LIST}" var="statuses">
                                            <option value="${statuses.getId()}" ${statuses.getId() == status ? 'selected="selected"' : ''}>
                                                    ${fn:escapeXml(statuses.getStatusTitle())}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <h4>${ERRORS.get("status")}</h4>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Наименование:</label>
                                </td>
                                <td>
                                    <input id="title" name="title" value="${fn:escapeXml(title)}">
                                </td>
                                <td>
                                    <h4>${ERRORS.get("title")}</h4>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Работа:</label>
                                </td>
                                <td>
                                    <input id="workTime" name="workTime" value="${fn:escapeXml(workTime)}">
                                </td>
                                <td>
                                    <h4>${ERRORS.get("workTime")}</h4>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Дата начала(ГГГГ-ММ-ДД):</label>
                                </td>
                                <td>
                                    <input id="beginDate" name="beginDate" value="${fn:escapeXml(beginDate)}">
                                </td>
                                <td>
                                    <h4>${ERRORS.get("beginDate")}</h4>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Дата окончания(ГГГГ-ММ-ДД):</label>
                                </td>
                                <td>
                                    <input id="endDate" name="endDate" value="${fn:escapeXml(endDate)}">
                                </td>
                                <td>
                                    <h4>${ERRORS.get("endDate")}</h4>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Наименование проекта:</label>
                                </td>
                                <td>
                                    <label>${fn:escapeXml(titleProject)}</label>
                                <td>
                            </tr>
                            <tr>
                                <td>
                                    <label>Сотрудник:</label>
                                </td>
                                <td>
                                    <select name="employeeId">
                                        <option value="null"> </option>
                                        <c:forEach items="${EMPLOYEE_LIST}" var="employees">
                                            <option value="${employees.id}"
                                                ${employees.id == employeeId ? 'selected="selected"' : ''}>
                                                ${fn:escapeXml(employees.surname)}
                                                ${fn:escapeXml(employees.firstName)}
                                                ${fn:escapeXml(employees.patronymic)}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td></td>
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
