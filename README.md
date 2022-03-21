# trainingtask

Инструкция по сборке:

    Скачать проект;
    Выполнить команду: build;
    Выполнить команду: startDatabase.
    Выполнить create.sql скрипт в СУБД:
        1)Запустить hsqldb.jar находящийся  в папаке src/main/webapp/lib/hsqldb-2.6.1.jar
        2) В появившемся окне ввести данные (dbName,dbPassword, dbUrl) из  settings.gradle и нажать OK
        3) File -> Open Script-> выбрать скрипт create.sql
        4) Нажать Execute script
    Выполнить команду: startServer.
Инструкция по Запуску:
    
    Перейти по ссылке появившейся в консоли сборки билда(http://localhost:8080/TrainingTask)
