<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Добавить задачу</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>

    <my:mainMenu></my:mainMenu>

    <div>
            <div id="container">
                <h3>Добавить задачу</h3>
                <form action="tasks" method="post" id="form" >
                    <input type="hidden" name="action" value="/add" />

                    <table>
                        <div>
                            <input class="add-button" type="submit" name="submitButton" id="submitButton" value="Сохранить">
                            <button id="cancelButton" name="cancelButton" onclick="window.history.back()"
                                    type="button" class="add-button">
                                Отмена
                            </button>
                        </div>
                        <tbody>
                        <tr>
                            <td>
                                <label>Статус:</label>
                            </td>
                            <td>
                                <select name="status" data-selected="${status}">
                                    <option ${status == "Не начата" ? 'selected="selected"' : ''}>Не начата</option>
                                    <option ${status == "В процессе" ? 'selected="selected"' : ''}>В процессе</option>
                                    <option ${status == "Завершена" ? 'selected="selected"' : ''}>Завершена</option>
                                    <option ${status == "Отложена" ? 'selected="selected"' : ''}>Отложена</option>
                                </select>
                            </td>
                            <td>
                                <h4>${ERRORS.get(0)}</h4>
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
                                <h4>${ERRORS.get(1)}</h4>
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
                                <h4>${ERRORS.get(2)}</h4>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Дата начала(ГГГГ-ММ-ДД):</label>
                            </td>
                            <td>
                                <input id="beginDate" name="beginDate" value="${beginDate}">
                            </td>
                            <td>
                                <h4>${ERRORS.get(3)}</h4>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Дата окончания(ГГГГ-ММ-ДД):</label>
                            </td>
                            <td>
                                <input id="endDate" name="endDate" value="${endDate}">
                            </td>
                            <td>
                                <h4>${ERRORS.get(4)}</h4>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Наименование проекта:</label>
                            </td>
                            <td>
                                <select name="projectId">
                                <c:forEach items="${PROJECT_LIST}" var="projects">
                                    <option value="${projects.id}" ${projects.id == projectId ? 'selected="selected"' : ''}>
                                        ${fn:escapeXml(projects.title)}
                                    </option>
                                </c:forEach>
                                </select>
                            <td>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Сотрудник:</label>
                            </td>
                            <td>
                                <select name="employeeId">
                                <option value="null"> </option>
                                <c:forEach items="${EMPLOYEE_LIST}" var="employees">
                                    <option value="${employees.id}" ${employees.id == employeeId ? 'selected="selected"' : ''}>
                                        ${fn:escapeXml(employees.surname)}
                                        ${fn:escapeXml(employees.firstName)}
                                        ${fn:escapeXml(employees.patronymic)}
                                    </option>
                                </c:forEach>
                            </select>
                            </td>
                            <td>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>

