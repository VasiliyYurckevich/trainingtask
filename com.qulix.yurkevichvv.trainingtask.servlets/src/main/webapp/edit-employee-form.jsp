<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@page import="com.qulix.yurkevichvv.trainingtask.model.entity.Employee"%>
<jsp:useBean id="employee" scope="request" class="com.qulix.yurkevichvv.trainingtask.model.entity.Employee"/>
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

        <div class="chief">
            <div id="container">
                <h3>Редактировать сотрудника</h3>
                <form action="employees">
                    <input type="hidden" name="employeeId" value="${employee.id}"/>
                    <input name="action" type="hidden">

                    <div>
                        <my:buttons/>
                    </div>

                    <div class="main">
                        <my:textField name="Имя" id="surname" value="${surname}"/>

                        <my:textField name="Фамилия" id="firstName" value="${firstName}"/>

                        <my:textField name="Отчество" id="patronymic" value="${patronymic}"/>

                        <my:textField name="Должность" id="post" value="${post}"/>
                    </div>

                </form>
            </div>
        </div>
    </body>
</html>
