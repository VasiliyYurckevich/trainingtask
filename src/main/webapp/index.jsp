<%@ page import="java.util.List" %>
<%@ page import="app.model.Client" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<head>
    <title>Clients list</title>
</head>
<body>
<div class="container">
<h1>Список проектов</h1>
    <hr>
    <div align="right">
        <a class="btn btn-primary btn-lg" href='/add' role="button">Добавить проект</a>
    </div>
    <p></p>
    <nav class="navbar navbar-light" style="background-color: #e3f2fd;">
        <form method="post">
            <input type="checkbox" name="sort" value="check"> Сортировать по фамилии
            <button type="submit" class="btn btn-primary btn-sm">Обновить</button>
        </form>
        <form method="get" action="client" class="form-inline">
            <input class="form-control mr-sm-2" type="search" name="name" placeholder="Поиск по имени" aria-label="Search">
            <button class="btn btn-outline-primary my-2 my-sm-0" type="submit">Поиск</button>
        </form>
    </nav>
<table class="table table-hover">
    <thead class="thead-light">
<tr><th scope="col">Id</th><th scope="col">Фамилия</th><th scope="col">Имя</th><th scope="col">Отчество</th><th scope="col">Email</th><th scope="col">Телефон</th><th scope="col">Изменить</th><th scope="col">Удалить</th></tr>
    </thead>
    <tbody>
<%
    List<Project> list = (List<Project>) request.getAttribute("clientsList");
    if (list!=null || !list.isEmpty()) {
        for (Client e : list) {
            out.print("<tr><td class=\"text-center\">" + e.getId() + "</td><td class=\"text-center\">" + e.getLastName() + "</td><td class=\"text-center\">" + e.getName() + "</td>" + "<td class=\"text-center\">" + e.getMiddleName() + "</td> " +
                    "<td class=\"text-center\">" + e.getEmail() + "</td><td class=\"text-center\">" + e.getTelNumber() + String.format("</td><td class=\"text-center\"><a href='/edit?id=%d&lN=%s&n=%s&mN=%s&e=%s&t=%s",e.getId(),e.getLastName(),e.getName(),e.getMiddleName(),e.getEmail(),e.getTelNumber()) + "'>изменить</a></td> " +
                    "<td class=\"text-center\"><a href='/delete?id=" + e.getId() + "'>удалить</a></td></tr>");
        }
    }
    else out.println("<p>Клиенты не добавлены!</p>");
%>
    </tbody>
</table>
</div>
</body>
</html>