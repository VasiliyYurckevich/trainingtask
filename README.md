# Приложение для управления задачами

## Требования к окружению
Для сборки и работы приложения необходима предустановленная JDK версии не ниже 1.9

## Инструкция по сборке 
1. Клонировать данный репозиторий с gitlab

2. Для компиляции и сборки проекта необходимо выполнить команду

     `./gradlew build` 

## Инструкция по запуску
1. Запустить базу данных 

     `./gradlew startDatabase`

2. Запустить сервер (для jsp/servlet-версии)

   `./gradlew startServer`

    или (для wicket-версии)

   `./gradlew startWicketServer`

3. Перейти по ссылке (для jsp/servlet-версии http://localhost:8080/com.qulix.yurkevichvv.trainingtask.servlets)

    или (для wicket-версии http://localhost:8090/com.qulix.yurkevichvv.trainingtask.wicket) 

## Дополнительные функции
- Удаление базы данных 

   `./gradlew dropDatabase`

- Остановка базы данных

   `./gradlew stopDatabase`

- Заполнение базы данных тестовыми данными 

   `./gradlew fillDatabase`
