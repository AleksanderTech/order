create table order_user (
     id bigserial primary key,
     name varchar(100) not null,
     email varchar(100) unique not null,
     password varchar(100) not null,
     is_active boolean not null default false
);

create table if not exists thought (
    id bigserial primary key,
    name varchar(100),
    content text,
    user_id bigint references order_user(id),
    created_at timestamp not null default now(),
    modified_at timestamp
);

create table if not exists thought_link (
    thought_id bigint references thought(id),
    linked_thought_id bigint references thought(id),
    primary key(thought_id,linked_thought_id)
);

create table if not exists tag (
    name varchar(100) primary key
);

create table if not exists thought_tag (
    thought_id bigint references thought(id),
    tag_name varchar(100) references tag(name) on update cascade on delete cascade,
    primary key(thought_id,tag_name)
);

