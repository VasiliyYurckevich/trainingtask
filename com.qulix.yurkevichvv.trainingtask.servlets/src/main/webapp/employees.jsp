<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=utf-8" %>
<jsp:useBean id="EMPLOYEE_LIST" scope="request" type="java.util.List<com.qulix.yurkevichvv.trainingtask.model.entity.Employee>"/>

<!DOCTYPE html>
<html lang="ru">
    <meta charset="UTF-8">

    <head>
        <link type="text/css" rel="stylesheet" href="css/style.css">
        <title>Сотрудники</title>
    </head>

    <body>

        <my:mainMenu/>

        <div class="chief">
            <div class="header">
                <div>
                    <h2>Сотрудники</h2>
                </div>
            </div>

            <div>
                <div>
                    <form action="employees" method="get">
                        <input type="hidden" name="action" value="/edit"/>
                        <input type="hidden" name="employeeId" value="">
                        <input type="submit" value="Добавить" class="add-button">
                    </form>
                    <table id="table" class="table table-striped">
                        <tr>
                            <th>Фамилия</th>
                            <th>Имя</th>
                            <th>Отчество</th>
                            <th>Должность</th>
                            <th>Действия</th>
                        </tr>

                        <c:forEach var="employee" items="${EMPLOYEE_LIST}">
                            <c:url var="editLink" value="employees">
                                <c:param name="action" value="/edit"/>
                                <c:param name="employeeId" value="${employee.id}"/>
                            </c:url>
                            <c:url var="deleteLink" value="employees">
                                <c:param name="action" value="/delete"/>
                                <c:param name="employeeId" value="${employee.id}"/>
                            </c:url>

                            <tr>
                                <td> ${fn:escapeXml(employee.surname)}</td>
                                <td> ${fn:escapeXml(employee.firstName)} </td>
                                <td> ${fn:escapeXml(employee.patronymic)}</td>
                                <td> ${fn:escapeXml(employee.post)} </td>
                                <td>
                                    <a href="${editLink}">Редактировать</a>
                                    <a href="${deleteLink}">Удалить</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>