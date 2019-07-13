create table members
(
    id   serial       not null
        constraint members_pk
            primary key,
    name varchar(255) not null
);

create unique index members_name_uindex
    on members (name);

create table company
(
    supervisor_id int,
    employee_id int constraint employee_u unique
);

