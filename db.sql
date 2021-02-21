create database demo;
use demo;

create table user(
	user_id bigint auto_increment primary key not null,
    email varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    password varchar(255) not null
);

create table role(
	role_id bigint auto_increment primary key not null,
    name varchar(255) not null
);


create table users_roles(
	id bigint auto_increment primary key not null,
    user_id bigint not null,
    role_id bigint not null ) ;
    
alter table users_roles add foreign key(user_id) references user(user_id);
alter table users_roles add foreign key(role_id) references user(role_id);
    
    