<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Добавить задачу</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
    <body>

        <ul>
            <li style="font-family: Arial">
                <a class="choose" href="projects">Проекты</a>
            </li>
            <li style="font-family: Arial">
                <a href="task">Задачи</a>
            </li>
            <li style="font-family: Arial">
                <a href="employees">Сотрудники</a>
            </li>
        </ul>

        <div style="padding:20px; margin-top:50px;height:600px;">
            <div id="container">
                <h3>Добавить задачу</h3>
                <form action="task"  method="post" id="form">
                    <input type="hidden" name="action" value="/newTaskInProject"/>
                    <table>
                        <div>
                            <input class="add-button" type="submit" name="submitButton"
                                   id="submitButton"  value="Сохранить">
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
                                <select type="text" name="status" data-selected="${status}">
                                    <option ${status == "Не начата"  ? 'selected="selected"' : ''}>Не начата</option>
                                    <option ${status == "В процессе"  ? 'selected="selected"' : ''}>В процессе</option>
                                    <option ${status == "Завершена"  ? 'selected="selected"' : ''}>Завершена</option>
                                    <option ${status == "Отложена"  ? 'selected="selected"' : ''}>Отложена</option>
                                </select>
                            </td>
                            <td>
                                <error>${ERRORS.get(0)}</error>
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
                                <error>${ERRORS.get(1)}</error>
                            </td>

                        </tr>
                        <tr>
                            <td>
                                <label>Работа:</label>
                            </td>
                            <td>
                                <input id="workTime" name="workTime" value="${workTime}">
                            </td>
                            <td>
                                <error>${ERRORS.get(2)}</error>
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
                                <error>${ERRORS.get(3)}</error>
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
                                <error>${ERRORS.get(4)}</error>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Наименование проекта:</label>
                            </td>
                            <td>
                                <label>${titleProject}</label>
                            <td>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>Сотрудник:</label>
                            </td>
                            <td>
                                <select name="employeeId" >
                                <option value="null">  </option>
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
                    <br><br/>
                </form>
            </div>
        </div>
    </body>
</html>

