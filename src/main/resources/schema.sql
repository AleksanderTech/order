create table order_user(
     id bigserial primary key,
     name varchar(100) not null,
     email varchar(100) unique not null,
     password varchar(100) not null,
     is_active boolean not null default false
);