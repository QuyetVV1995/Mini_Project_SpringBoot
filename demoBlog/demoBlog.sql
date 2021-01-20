create database demoBlog;
use demoBlog;

create table category (
       category_id bigint not null auto_increment primary key,
        category varchar(255) not null,
        post_id bigint not null
    );
    
    create table comment (
       comment_id bigint not null auto_increment primary key,
        body TEXT,
        create_date timestamp not null,
        post_id bigint not null,
        user_id bigint not null
    );
     create table post (
       post_id bigint not null auto_increment primary key,
        body TEXT,
        create_date timestamp not null,
        title varchar(255) not null,
        user_id bigint not null
    );
    
    create table role (
       role_id bigint not null auto_increment primary key,
        role varchar(255)
    );
    
    create table user (
       user_id bigint not null auto_increment primary key,
        active integer not null,
        email varchar(255) not null,
        last_name varchar(255) not null,
        name varchar(255) not null,
        password varchar(255) not null,
        username varchar(255) not null
    );
    
    create table user_role (
       user_id bigint not null,
        role_id bigint not null
    );
    
    alter table role add constraint UK_bjxn5ii7v7ygwx39et0wawu0q unique (role);
    alter table user add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);
    alter table user add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);
    
    alter table category 
       add constraint FKopeo59t1mr1euhddriighg7ir 
       foreign key (post_id) 
       references post(post_id);
       
	alter table comment 
       add constraint FKs1slvnkuemjsq2kj4h3vhx7i1 
       foreign key (post_id) 
       references post(post_id);
       
	alter table comment 
       add constraint FK8kcum44fvpupyw6f5baccx25c 
       foreign key (user_id) 
       references user(user_id);
       
	alter table post 
       add constraint FK72mt33dhhs48hf9gcqrq4fxte 
       foreign key (user_id) 
       references user(user_id);
       
	alter table user_role 
       add constraint FKa68196081fvovjhkek5m97n3y 
       foreign key (role_id) 
       references role(role_id);
       
	 alter table user_role 
       add constraint FK859n2jvi8ivhui0rl0esws6o 
       foreign key (user_id) 
       references user(user_id);
  
-- USER  
INSERT INTO USER ( password, email, username, name, last_name, active)
VALUES
  ( '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'admin@mail.com', 'admin', 'Name', 'Surname', 1);
INSERT INTO USER ( password, email, username, name, last_name, active)
VALUES
  ( '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'user@gmail.com', 'user', 'Name', 'Surname', 1);
  
-- ROLE  
INSERT INTO ROLE ( role)
VALUES ( 'ROLE_ADMIN');
INSERT INTO ROLE ( role)
VALUES ( 'ROLE_USER');

-- User Roles
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (2, 2);

-- Posts
INSERT INTO POST ( user_id, title, body, create_date)
VALUES ( 1, 'Title 1','"Post 1"',now());
INSERT INTO POST ( user_id, title, body, create_date)
VALUES ( 2, 'Title 2','"Post 2"',now());

-- Comments
INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (1, 1,'"Comment 1 "', now());

INSERT INTO COMMENT (post_id, user_id, body, create_date)
VALUES (2, 2,'"Comment 2 "', now());

-- CATEGORY
INSERT INTO  CATEGORY( category, post_id)
VALUES('SPRINGBOOT', 1);
INSERT INTO  CATEGORY( category, post_id)
VALUES('JAPANESES', 2);

       
       