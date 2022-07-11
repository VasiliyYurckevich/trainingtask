<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>Exception</title>
        <link type="text/css" rel="stylesheet" href="css/style.css">
    </head>

    <body>
        <div class="chief">
            <div class="start-page">
                <h2>Ошибка!</h2>
                <h2>Status Code: ${pageContext.errorData.statusCode} </h2>
                <h2>Страница не найдена</h2>
                <button class="add-button" onclick="window.location.href = 'index.jsp'">На главную</button>
            </div>
        </div>
    </body>
</html>