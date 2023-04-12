create table games (
    id varchar(50) primary key,
    name varchar(50) not null unique,
    price double precision not null
);