# Приложение для управления задачами

## Требования к окружению

Для сборки и работы приложения необходима предустановленная JDK версии не ниже 11

## Инструкция по сборке 

1. Клонировать данный репозиторий с gitlab

2. Для компиляции и сборки проекта необходимо выполнить команду

    `./gradlew build` 

## Инструкция по запуску

1. Запустить базу данных 

   `./gradlew startDatabase`

2. Запустить сервер

   - для обеих версий

     `./gradlew startServer`

   - для jsp/servlet-версии
   
       `./gradlew com.qulix.yurkevichvv.trainingtask.servlets:startServer`

   - для wicket-версии

       `./gradlew com.qulix.yurkevichvv.trainingtask.wicket:startServer`

3. Перейти по ссылке  

    - jsp/servlet-версия 
    
       http://localhost:9090/servlets

    - wicket-версия 
   
       http://localhost:9090/wicket

4. Остановить сервер 

    `./gradlew stopServer`

## Дополнительные функции

- Удаление базы данных 

   `./gradlew dropDatabase`

- Остановка базы данных

   `./gradlew stopDatabase`

- Заполнение базы данных тестовыми данными 

   `./gradlew fillDatabase`
