<%
    String message = pageContext.getException().getMessage().toString();
%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Exception</title>
    </head>

    <body>
        <h1>Ошибка!</h1>
        <p><%= message %></p>
    </body>
</html>