<%@ page import="java.util.List" %>
<%@ page import="com.qulix.yurkevichvv.trainingtask.model.Project" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<head>
    <title>Projects list</title>
</head>
<body>
<div class="container">
<h1>Список проектов</h1>
    <hr>
    <div align="right">
        <a class="btn btn-primary btn-lg" href='/add' role="button">Добавить проект</a>
    </div>
    <p></p>
    <
<table class="table table-hover">
    <thead class="thead-light">
<tr><th scope="col">Title</th><th scope="col">Discription</th>
    </thead>
    <tbody>
<%
    List<Project> list = (List<Project>) request.getAttribute("PROJECT_LIST");
    if (list!=null || !list.isEmpty()) {
        for (Project e : list) {
            out.print("<tr><td class=\"text-center\">" + e.getTitle() + "</td><td class=\"text-center\">" + e.getDiscription() +"</td></tr>");
        }
    }
    else out.println("<p>Ошибка</p>");
%>
    </tbody>
</table>
</div>
</body>
</html>
=======


