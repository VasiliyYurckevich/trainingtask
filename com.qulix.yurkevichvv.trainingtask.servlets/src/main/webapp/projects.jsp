<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <link type="text/css" rel="stylesheet" href="css/style.css">
        <title></title>
    </head>

    <body>

        <my:mainMenu></my:mainMenu>

        <div class="chief">
            <div class="header">
                <div>
                    <h2>Проекты</h2>
                </div>
            </div>

            <div>
                <div>
                    <form action="projects" method="get">
                        <input type="hidden" name="action" value="/new"/>
                        <input type="submit" value="Добавить" class="add-button">
                    </form>

                    <table id="table" class="table table-striped">
                        <tr>
                            <th>Наименование</th>
                            <th>Описание</th>
                            <th>Действия</th>
                        </tr>

                        <c:forEach var="tempProject" items="${PROJECT_LIST}">

                            <c:url var="editLink" value="/projects">
                                <c:param name="action" value="/editForm"/>
                                <c:param name="projectId" value="${tempProject.id}"/>
                            </c:url>
                            <c:url var="deleteLink" value="/projects">
                                <c:param name="action" value="/delete"/>
                                <c:param name="projectId" value="${tempProject.id}"/>
                            </c:url>

                            <tr>
                                <td> ${fn:escapeXml(tempProject.title)}</td>
                                <td> ${fn:escapeXml(tempProject.description)} </td>
                                <td>
                                    <a href="${editLink}">Редактировать</a>
                                    <a href="${deleteLink}"
                                        onclick="if (!(confirm('Вы уверены?'))) return false">Удалить</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>