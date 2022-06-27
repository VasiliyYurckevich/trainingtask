INSERT INTO employee ( "ID", "SURNAME", "FIRST_NAME", "PATRONYMIC", "POST" )
VALUES ( 1,'Сидоров', 'Евгений', 'Николаевич', 'Младший разработчик');
INSERT INTO employee ( "ID", "SURNAME", "FIRST_NAME", "PATRONYMIC", "POST" )
VALUES ( 2, 'Зеленский', 'Владимир', 'Александрович', 'Продукт-менеджер');
INSERT INTO employee ( "ID", "SURNAME", "FIRST_NAME", "PATRONYMIC", "POST" )
VALUES ( 3, 'Ельцин', 'Борис', 'Николаевич', 'Бизнес-аналитик');
INSERT INTO employee ( "ID", "SURNAME", "FIRST_NAME", "PATRONYMIC", "POST" )
VALUES ( 4, 'Джугашвили', 'Иосиф', 'Виссарионович', 'Старший разработчик');
INSERT INTO employee ( "ID", "SURNAME", "FIRST_NAME", "PATRONYMIC", "POST" )
VALUES ( 5, 'Сикорский', 'Игорь', 'Иванович', 'Разработчик');
INSERT INTO employee ( "ID", "SURNAME", "FIRST_NAME", "PATRONYMIC", "POST" )
VALUES ( 6, 'Меньшиков', 'Александр', 'Данилович', 'Тестировщик');
INSERT INTO employee ( "ID", "SURNAME", "FIRST_NAME", "PATRONYMIC", "POST" )
VALUES ( 7, 'Котиков', 'Степан', 'Николаевич', 'Дизайнер');
INSERT INTO project ( "ID", "TITLE", "DESCRIPTION" ) VALUES ( 1, 'MobilBank', 'Мобильное банковское приложение');
INSERT INTO project ( "ID", "TITLE", "DESCRIPTION" ) VALUES ( 2, 'Microsoft', 'Операционная система');
INSERT INTO project ( "ID", "TITLE", "DESCRIPTION" ) VALUES ( 3, 'Witcher 4', 'Компьютерная игра');
INSERT INTO project ( "ID", "TITLE", "DESCRIPTION" ) VALUES ( 4, 'PowerPoint', 'Программа для презентаций');
INSERT INTO task
( "ID", "STATUS", "TITLE", "WORK_TIME", "BEGIN_DATE", "END_DATE", "EMPLOYEE_ID", "PROJECT_ID" )
VALUES ( 1, 1, 'Составить отчет о экономической выгоде', 35, '2022-02-28', '2022-03-05', 3, 3);
INSERT INTO task
( "ID", "STATUS", "TITLE", "WORK_TIME", "BEGIN_DATE", "END_DATE", "EMPLOYEE_ID", "PROJECT_ID" )
VALUES ( 2, 2, 'Добавить функцию просмотра расходов клиента', 20, '2022-04-11', '2022-04-12', 5, 1);
INSERT INTO task
( "ID", "STATUS", "TITLE", "WORK_TIME", "BEGIN_DATE", "END_DATE", "EMPLOYEE_ID", "PROJECT_ID" )
VALUES ( 3, 2, 'Полное тестирование функциональности', 170, '2022-03-28', '2022-04-30', 6, 2);
INSERT INTO task
( "ID", "STATUS", "TITLE", "WORK_TIME", "BEGIN_DATE", "END_DATE", "EMPLOYEE_ID", "PROJECT_ID" )
VALUES ( 4, 4, '№132 Ошибка при вводе некорректных данных', 20, '2022-03-11', '2022-03-15', 5, 1);
INSERT INTO task
( "ID", "STATUS", "TITLE", "WORK_TIME", "BEGIN_DATE", "END_DATE", "EMPLOYEE_ID", "PROJECT_ID" )
VALUES ( 5, 2, 'Разработать дизайн диаграмм расходов', 2, '2022-03-28', '2022-03-29', 7, 1);
INSERT INTO task
( "ID", "STATUS", "TITLE", "WORK_TIME", "BEGIN_DATE", "END_DATE", "EMPLOYEE_ID", "PROJECT_ID" )
VALUES ( 6, 1, 'Доработать игровой движок', 235, '2022-03-28', '2022-05-05', 4, 3);
INSERT INTO task
( "ID", "STATUS", "TITLE", "WORK_TIME", "BEGIN_DATE", "END_DATE", "EMPLOYEE_ID", "PROJECT_ID" )
VALUES ( 7, 3, '№51 Неудачный выбор типа для атрибута Task.status', 8, '2022-03-11', '2022-03-12', 1, 1);
INSERT INTO task
( "ID", "STATUS", "TITLE", "WORK_TIME", "BEGIN_DATE", "END_DATE", "EMPLOYEE_ID", "PROJECT_ID" )
VALUES ( 8, 3, 'Составить отчет для клиента', 25, '2022-02-28', '2022-03-05', NULL, 3);