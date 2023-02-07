<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="employee" scope="request" class="com.qulix.yurkevichvv.trainingtask.model.entity.Employee"/>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Редактировать сотрудника</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>

        <my:header/>

        <div class="chief">
            <div class="header">
                <div>
                    <h2>Редактировать сотрудника</h2>
                </div>
            </div>

            <div id="container" class="main">
                <form action="employees">
                    <input type="hidden" name="employeeId" value="${employeeId}"/>
                    <input name="action" type="hidden">

                    <my:buttons saveAction="/save" cancelAction="/list"/>

                    <div class="main">
                        <my:textField label="Фамилия" id="surname" value="${surname}"/>

                        <my:textField label="Имя" id="firstName" value="${firstName}"/>

                        <my:textField label="Отчество" id="patronymic" value="${patronymic}"/>

                        <my:textField label="Должность" id="post" value="${post}"/>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
