-- !Ups

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

drop table if exists books cascade;
drop table if exists cables cascade;
drop table if exists consoles cascade;
drop table if exists games cascade;
drop table if exists laptops cascade;
drop table if exists phones cascade;
drop table if exists shoes cascade;
drop table if exists tshirts cascade;
drop table if exists tvs cascade;
drop table if exists watches cascade;

create table books
(
    id serial primary key,
    uuid uuid,
    title varchar not null,
    published int not null
);

create table cables
(
    id serial primary key,
    uuid uuid,
    name varchar not null,
    lengt varchar not null
);

create table consoles
(
    id serial primary key,
    uuid uuid,
    model varchar not null
);

create table games
(
    id serial primary key,
    uuid uuid,
    title varchar not null,
    released int not null,
    category varchar not null
);

create table laptops
(
    id serial primary key,
    uuid uuid,
    model varchar not null,
    released int not null,
    memory int not null,
    ram int not null,
    graphics varchar not null,
    cpu varchar not null
);

create table phones
(
    id serial primary key,
    uuid uuid,
    model varchar not null,
    released int not null,
    memory int not null,
    ram int not null
);

create table shoes
(
    id serial primary key,
    uuid uuid,
    brand varchar not null,
    siz int not null
);

create table tshirts
(
    id serial primary key,
    uuid uuid,
    brand varchar not null,
    siz varchar not null
);

create table tvs
(
    id serial primary key,
    uuid uuid,
    model varchar not null,
    sizeInch int not null
);

create table watches
(
    id serial primary key,
    uuid uuid,
    brand varchar not null,
    diameter int not null
);

insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07741', 'book1', 1998);
insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07742', 'book2', 1998);
insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07743', 'book3', 1998);
insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07744', 'book4', 1998);
insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07745', 'book5', 1998);
insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07746', 'book6', 1998);
insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07747', 'book7', 1998);
insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07748', 'book8', 1998);
insert into books(uuid, title, published) values ('81073561-f7f5-4bfa-8635-8da7e6d07749', 'book9', 1998);

insert into cables(uuid, name, lengt) values ('81073561-f7f5-4bfa-8635-8da7e6d07741', 'cable1', '1m');
insert into cables(uuid, name, lengt) values ('81073561-f7f5-4bfa-8635-8da7e6d07742', 'cable2', '50cm');
insert into cables(uuid, name, lengt) values ('81073561-f7f5-4bfa-8635-8da7e6d07743', 'cable3', '2m');
insert into cables(uuid, name, lengt) values ('81073561-f7f5-4bfa-8635-8da7e6d07744', 'cable4', '2m');
insert into cables(uuid, name, lengt) values ('81073561-f7f5-4bfa-8635-8da7e6d07745', 'cable5', '2m');
insert into cables(uuid, name, lengt) values ('81073561-f7f5-4bfa-8635-8da7e6d07746', 'cable6', '2m');
insert into cables(uuid, name, lengt) values ('81073561-f7f5-4bfa-8635-8da7e6d07747', 'cable7', '3m');
insert into cables(uuid, name, lengt) values ('81073561-f7f5-4bfa-8635-8da7e6d07748', 'cable8', '5m');
insert into cables(uuid, name, lengt) values ('81073561-f7f5-4bfa-8635-8da7e6d07749', 'cable9', '10m');

insert into consoles(uuid, model) values ('81073561-f7f5-4bfa-8635-8da7e6d07741', 'console1');
insert into consoles(uuid, model) values ('81073561-f7f5-4bfa-8635-8da7e6d07742', 'console2');
insert into consoles(uuid, model) values ('81073561-f7f5-4bfa-8635-8da7e6d07743', 'console3');
insert into consoles(uuid, model) values ('81073561-f7f5-4bfa-8635-8da7e6d07744', 'console4');
insert into consoles(uuid, model) values ('81073561-f7f5-4bfa-8635-8da7e6d07745', 'console5');
insert into consoles(uuid, model) values ('81073561-f7f5-4bfa-8635-8da7e6d07746', 'console6');
insert into consoles(uuid, model) values ('81073561-f7f5-4bfa-8635-8da7e6d07747', 'console7');
insert into consoles(uuid, model) values ('81073561-f7f5-4bfa-8635-8da7e6d07748', 'console8');
insert into consoles(uuid, model) values ('81073561-f7f5-4bfa-8635-8da7e6d07749', 'console9');

insert into games(uuid, title, released, category) values ('81073561-f7f5-4bfa-8635-8da7e6d07741', 'game1', 2000, 'action');
insert into games(uuid, title, released, category) values ('81073561-f7f5-4bfa-8635-8da7e6d07742', 'game2', 2001, 'action');
insert into games(uuid, title, released, category) values ('81073561-f7f5-4bfa-8635-8da7e6d07743', 'game3', 2002, 'action');
insert into games(uuid, title, released, category) values ('81073561-f7f5-4bfa-8635-8da7e6d07744', 'game4', 2003, 'action');
insert into games(uuid, title, released, category) values ('81073561-f7f5-4bfa-8635-8da7e6d07745', 'game5', 2004, 'action');
insert into games(uuid, title, released, category) values ('81073561-f7f5-4bfa-8635-8da7e6d07746', 'game6', 2005, 'action');
insert into games(uuid, title, released, category) values ('81073561-f7f5-4bfa-8635-8da7e6d07747', 'game7', 2006, 'action');
insert into games(uuid, title, released, category) values ('81073561-f7f5-4bfa-8635-8da7e6d07748', 'game8', 2007, 'action');
insert into games(uuid, title, released, category) values ('81073561-f7f5-4bfa-8635-8da7e6d07749', 'game9', 2008, 'action');

insert into laptops(uuid, model, released, memory, ram, graphics, cpu) values ('81073561-f7f5-4bfa-8635-8da7e6d07741', 'laptop1', 2000, 512, 16, 'graphic card 1', 'cpu 1');
insert into laptops(uuid, model, released, memory, ram, graphics, cpu) values ('81073561-f7f5-4bfa-8635-8da7e6d07742', 'laptop2', 2001, 512, 16, 'graphic card 1', 'cpu 1');
insert into laptops(uuid, model, released, memory, ram, graphics, cpu) values ('81073561-f7f5-4bfa-8635-8da7e6d07743', 'laptop3', 2002, 512, 16, 'graphic card 1', 'cpu 1');
insert into laptops(uuid, model, released, memory, ram, graphics, cpu) values ('81073561-f7f5-4bfa-8635-8da7e6d07744', 'laptop4', 2003, 512, 16, 'graphic card 1', 'cpu 1');
insert into laptops(uuid, model, released, memory, ram, graphics, cpu) values ('81073561-f7f5-4bfa-8635-8da7e6d07745', 'laptop5', 2004, 512, 16, 'graphic card 1', 'cpu 1');
insert into laptops(uuid, model, released, memory, ram, graphics, cpu) values ('81073561-f7f5-4bfa-8635-8da7e6d07746', 'laptop6', 2005, 512, 16, 'graphic card 1', 'cpu 1');
insert into laptops(uuid, model, released, memory, ram, graphics, cpu) values ('81073561-f7f5-4bfa-8635-8da7e6d07747', 'laptop7', 2006, 512, 16, 'graphic card 1', 'cpu 1');
insert into laptops(uuid, model, released, memory, ram, graphics, cpu) values ('81073561-f7f5-4bfa-8635-8da7e6d07748', 'laptop8', 2007, 512, 16, 'graphic card 1', 'cpu 1');
insert into laptops(uuid, model, released, memory, ram, graphics, cpu) values ('81073561-f7f5-4bfa-8635-8da7e6d07749', 'laptop9', 2008, 512, 16, 'graphic card 1', 'cpu 1');

insert into phones(uuid, model, released, memory, ram) values ('81073561-f7f5-4bfa-8635-8da7e6d07741', 'phone1', 2000, 512, 16);
insert into phones(uuid, model, released, memory, ram) values ('81073561-f7f5-4bfa-8635-8da7e6d07742', 'phone2', 2001, 512, 16);
insert into phones(uuid, model, released, memory, ram) values ('81073561-f7f5-4bfa-8635-8da7e6d07743', 'phone3', 2002, 512, 16);
insert into phones(uuid, model, released, memory, ram) values ('81073561-f7f5-4bfa-8635-8da7e6d07744', 'phone4', 2003, 512, 16);
insert into phones(uuid, model, released, memory, ram) values ('81073561-f7f5-4bfa-8635-8da7e6d07745', 'phone5', 2004, 512, 16);
insert into phones(uuid, model, released, memory, ram) values ('81073561-f7f5-4bfa-8635-8da7e6d07746', 'phone6', 2005, 512, 16);
insert into phones(uuid, model, released, memory, ram) values ('81073561-f7f5-4bfa-8635-8da7e6d07747', 'phone7', 2006, 512, 16);
insert into phones(uuid, model, released, memory, ram) values ('81073561-f7f5-4bfa-8635-8da7e6d07748', 'phone8', 2007, 512, 16);
insert into phones(uuid, model, released, memory, ram) values ('81073561-f7f5-4bfa-8635-8da7e6d07749', 'phone9', 2008, 512, 16);

insert into shoes(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07741', 'Nike', 42);
insert into shoes(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07742', 'Adidas', 43);
insert into shoes(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07743', 'Reebok', 44);
insert into shoes(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07744', 'Umbro', 42);
insert into shoes(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07745', 'Nike', 41);
insert into shoes(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07746', 'Nike', 41);
insert into shoes(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07747', 'Adidas', 43);
insert into shoes(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07748', 'Adidas', 43);
insert into shoes(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07749', 'Reebok', 43);

insert into tshirts(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07741', 'Nike', 'M');
insert into tshirts(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07742', 'Adidas', 'M');
insert into tshirts(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07743', 'Reebok', 'L');
insert into tshirts(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07744', 'Umbro', 'L');
insert into tshirts(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07745', 'Nike', 'S');
insert into tshirts(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07746', 'Nike', 'S');
insert into tshirts(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07747', 'Adidas', 'XL');
insert into tshirts(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07748', 'Adidas', 'XXL');
insert into tshirts(uuid, brand, siz) values ('81073561-f7f5-4bfa-8635-8da7e6d07749', 'Reebok', 'XXXL');

insert into tvs(uuid, model, sizeInch) values ('81073561-f7f5-4bfa-8635-8da7e6d07741', 'Samsung', 50);
insert into tvs(uuid, model, sizeInch) values ('81073561-f7f5-4bfa-8635-8da7e6d07742', 'Samsung', 50);
insert into tvs(uuid, model, sizeInch) values ('81073561-f7f5-4bfa-8635-8da7e6d07743', 'Samsung', 50);
insert into tvs(uuid, model, sizeInch) values ('81073561-f7f5-4bfa-8635-8da7e6d07744', 'LG', 42);
insert into tvs(uuid, model, sizeInch) values ('81073561-f7f5-4bfa-8635-8da7e6d07745', 'LG', 40);
insert into tvs(uuid, model, sizeInch) values ('81073561-f7f5-4bfa-8635-8da7e6d07746', 'LG', 40);
insert into tvs(uuid, model, sizeInch) values ('81073561-f7f5-4bfa-8635-8da7e6d07747', 'LG', 40);
insert into tvs(uuid, model, sizeInch) values ('81073561-f7f5-4bfa-8635-8da7e6d07748', 'LG', 40);
insert into tvs(uuid, model, sizeInch) values ('81073561-f7f5-4bfa-8635-8da7e6d07749', 'LG', 40);

insert into watches(uuid, brand, diameter) values ('81073561-f7f5-4bfa-8635-8da7e6d07741', 'brand1', 41);
insert into watches(uuid, brand, diameter) values ('81073561-f7f5-4bfa-8635-8da7e6d07742', 'brand2', 41);
insert into watches(uuid, brand, diameter) values ('81073561-f7f5-4bfa-8635-8da7e6d07743', 'brand3', 41);
insert into watches(uuid, brand, diameter) values ('81073561-f7f5-4bfa-8635-8da7e6d07744', 'brand4', 41);
insert into watches(uuid, brand, diameter) values ('81073561-f7f5-4bfa-8635-8da7e6d07745', 'brand5', 42);
insert into watches(uuid, brand, diameter) values ('81073561-f7f5-4bfa-8635-8da7e6d07746', 'brand6', 42);
insert into watches(uuid, brand, diameter) values ('81073561-f7f5-4bfa-8635-8da7e6d07747', 'brand7', 42);
insert into watches(uuid, brand, diameter) values ('81073561-f7f5-4bfa-8635-8da7e6d07748', 'brand8', 45);
insert into watches(uuid, brand, diameter) values ('81073561-f7f5-4bfa-8635-8da7e6d07749', 'brand9', 45);
-- !Downs

drop table if exists books cascade;
drop table if exists cables cascade;
drop table if exists consoles cascade;
drop table if exists games cascade;
drop table if exists laptops cascade;
drop table if exists phones cascade;
drop table if exists shoes cascade;
drop table if exists tshirts cascade;
drop table if exists tvs cascade;
drop table if exists watches cascade;
drop table if exists play_evolutions;
