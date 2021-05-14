-- !Ups

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

drop table if exists books cascade;

create table books
(
    id serial primary key,
    uuid uuid,
    title varchar not null,
    published int not null
);

insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07742', 'title1', 1998);
insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07743', 'title2', 1998);
insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07744', 'title3', 1998);
insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07745', 'title4', 1998);

-- !Downs

drop table if exists books cascade;
drop table if exists play_evolutions;
