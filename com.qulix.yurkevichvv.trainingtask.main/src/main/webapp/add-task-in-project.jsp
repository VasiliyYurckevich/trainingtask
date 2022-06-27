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
                <form action="tasks" method="post" id="form">
                    <input type="hidden" name="action" value="/newTaskInProject"/>
                    <div>
                        <input class="add-button" type="submit" name="submitButton"
                               id="submitButton" value="Сохранить">
                        <button id="cancelButton" name="cancelButton"
                                onclick="location.href='${pageContext.request.contextPath}/projects?action=%2fedit&projectId=${thisProjectId}'"
                                type="button" class="add-button">
                            Отмена
                        </button>
                    </div>

                    <div>
                        <label>Статус:</label>
                        <select name="status" data-selected="${status}">
                            <c:forEach items="${STATUS_LIST}" var="statuses">
                                <option value="${statuses.getId()}">
                                        ${fn:escapeXml(statuses.getStatusTitle())}
                                </option>
                            </c:forEach>
                        </select>
                        <h4>${ERRORS.get("status")}</h4>
                    </div>
                    <div>
                        <label>Наименование:</label>
                        <input id="title" name="title" value="${fn:escapeXml(title)}">
                        <h4>${ERRORS.get("title")}</h4>
                    </div>
                    <div>
                        <label>Работа:</label>
                        <input id="workTime" name="workTime" value="${fn:escapeXml(workTime)}">
                        <h4>${ERRORS.get("workTime")}</h4>
                    </div>
                    <div>
                        <label>Дата начала(ГГГГ-ММ-ДД):</label>
                        <input id="beginDate" name="beginDate" value="${fn:escapeXml(beginDate)}">
                        <h4>${ERRORS.get("beginDate")}</h4>
                    </div>
                    <div>
                        <label>Дата окончания(ГГГГ-ММ-ДД):</label>
                        <input id="endDate" name="endDate" value="${fn:escapeXml(endDate)}">
                        <h4>${ERRORS.get("endDate")}</h4>
                    </div>
                    <div>
                        <label>Наименование проекта:</label>
                        <select name="projectId">
                            <c:forEach items="${PROJECT_LIST}" var="projects">
                                <option value="${projects.id}" ${projects.id == projectId ? 'selected="selected"' : ''}>
                                        ${fn:escapeXml(projects.title)}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label>Сотрудник:</label>
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
                    </div>
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

