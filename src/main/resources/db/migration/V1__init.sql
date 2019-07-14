create table member
(
    id            serial       not null
        constraint member_pk
            primary key,
    name          varchar(255) not null,
    supervisor_id integer
        constraint member_id_fk
            references member
);

create unique index member_name_uindex
    on member (name);

