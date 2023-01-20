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

        <my:mainMenu></my:mainMenu>

        <div class="chief">
            <div id="container">
                <h3>Редактировать сотрудника</h3>
                <form action="employees">
                    <input type="hidden" name="employeeId" value="${employeeId}"/>
                    <input name="action" type="hidden">

                    <div>
                        <my:buttons saveAction="/save" cancelAction="/list"/>
                    </div>

                    <div class="main">
                        <my:textField label="Имя" id="surname" value="${surname}"/>

                        <my:textField label="Фамилия" id="firstName" value="${firstName}"/>

                        <my:textField label="Отчество" id="patronymic" value="${patronymic}"/>

                        <my:textField label="Должность" id="post" value="${post}"/>
                    </div>

                </form>
            </div>
        </div>
    </body>
</html>
