<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=utf-8" %>
<jsp:useBean id="PROJECT_LIST" scope="request" type="java.util.List<com.qulix.yurkevichvv.trainingtask.model.entity.Project>"/>


<!DOCTYPE html>
<html lang="ru">
    <head>
        <link type="text/css" rel="stylesheet" href="css/style.css">
        <title>Проекты</title>
    </head>

    <body>

        <my:header/>

        <div class="chief">
            <div class="header">
                <div>
                    <h2>Проекты</h2>
                </div>
            </div>

            <div>
                <div>
                    <form action="projects" method="get">
                        <input type="hidden" name="action" value="/edit"/>
                        <input type="hidden" name="projectId" value="">
                        <input type="hidden" name="token" value="${requestScope.token}">
                        <input type="submit" value="Добавить" class="add-button">
                    </form>

                    <table id="table" class="table table-striped">
                        <tr>
                            <th>Наименование</th>
                            <th>Описание</th>
                            <th>Действия</th>
                        </tr>

                        <c:forEach var="project" items="${PROJECT_LIST}">

                            <c:url var="editLink" value="/projects">
                                <c:param name="action" value="/edit"/>
                                <c:param name="projectId" value="${project.id}"/>
                                <c:param name="token" value="${requestScope.token}"/>
                            </c:url>
                            <c:url var="deleteLink" value="/projects">
                                <c:param name="action" value="/delete"/>
                                <c:param name="projectId" value="${project.id}"/>
                                <c:param name="token" value="${requestScope.token}"/>
                            </c:url>

                            <tr>
                                <td> ${fn:escapeXml(project.title)}</td>
                                <td> ${fn:escapeXml(project.description)} </td>
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