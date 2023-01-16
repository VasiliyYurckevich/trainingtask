<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="StatusList" scope="request" type="java.util.List<com.qulix.yurkevichvv.trainingtask.model.entity.Status>"/>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Редактировать задачу</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>

        <my:mainMenu></my:mainMenu>

        <div class="chief">
            <div id="container" class="main">
                <h3>Редактировать задачу</h3>
                <form action="tasks" method="post" >
                    <input type="hidden" name="action" value="/saveTaskInProject"/>
                    <input type="hidden" name="taskIndex" value="${taskIndex}"/>

                    <my:buttons cancelFormAction="projects" cancelAction="/edit" saveAction="/saveTaskInProject"/>

                    <my:dropDownChoice label="Статус" id="status" list="${StatusList}" selectedId="${status}"/>

                    <my:textField label="Наименование" id="title" value="${title}"/>

                    <my:textField label="Работа" id="workTime" value="${workTime}"/>

                    <my:textField label="Дата начала(ГГГГ-ММ-ДД)" id="beginDate" value="${beginDate}"/>

                    <my:textField label="Дата окончания(ГГГГ-ММ-ДД)" id="endDate" value="${endDate}"/>

                    <my:dropDownChoice label="Наименование проекта" id="projectId" list="${ProjectList}"
                        selectedId="${projectId}" isDisabled="true" isNullOption="true"/>

                    <my:dropDownChoice label="Сотрудник" id="employeeId" list="${EmployeeList}"
                        selectedId="${employeeId}" isNullOption="true"/>
                </form>
            </div>
        </div>
    </body>
</html>
