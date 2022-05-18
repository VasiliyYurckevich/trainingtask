SET DATABASE SQL SYNTAX MYS TRUE;

create table if not exists project
(
    project_id  INT         not null
        primary key,
    title       VARCHAR(50) not null,
    description VARCHAR(250)
);

create table if not exists employee
(
    employee_id INT         not null
        primary key,
    surname     VARCHAR(50) not null,
    first_name  VARCHAR(50) not null,
    patronymic  VARCHAR(50) not null,
    post        VARCHAR(50) not null
);

create table if not exists task
(
    id          INT         not null
        primary key,
    status      VARCHAR(50) not null,
    title       VARCHAR(50) not null,
    work_time   BIGINT      not null,
    begin_date  DATE        not null,
    end_date    DATE        not null,
    employee_id INT
                            references employee
                                on update cascade on delete set null,
    project_id  INT         not null
        references project
            on update cascade on delete cascade
);


