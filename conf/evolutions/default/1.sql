-- !Ups

create table books
(
    id serial primary key,
    title varchar not null,
    published int not null
);

insert into books(title, published) values ('title1', 1998);
insert into books(title, published) values ('title2', 1998);
insert into books(title, published) values ('title3', 1998);
insert into books(title, published) values ('title4', 1998);

-- !Downs

drop table if exists books cascade;
