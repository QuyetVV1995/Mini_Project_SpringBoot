create database demo;
use demo;

create table user(
id bigint not null primary key auto_increment,
email varchar(255) not null,
first_name varchar(255),
last_name varchar(255),
password varchar(255)
);
create table role(
id bigint not null primary key auto_increment,
name varchar(255)
);
create table users_roles(
id bigint not null primary key auto_increment,
user_id bigint not null,
role_id bigint not null
);

alter table users_roles add  foreign key(user_id) references user(id);
alter table users_roles add  foreign key(role_id) references role(id);

