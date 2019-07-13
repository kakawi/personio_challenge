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
    id serial       not null
        constraint company_pk
            primary key,
    supervisor_id int,
    employee_id int constraint employee_u unique
);

