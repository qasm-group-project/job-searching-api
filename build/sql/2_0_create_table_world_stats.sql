SET SCHEMA 'job_searching';

CREATE TABLE "user"
(
    id   uuid        NOT NULL DEFAULT uuid_generate_v4(),
    name varchar(20) NOT NULL,
    type varchar(20) NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);
CREATE TABLE "job_seeker_account"
(
    id uuid not null default uuid_generate_v4() primary key ,
    ac_username varchar(50) not null unique,
    ac_password varchar(50) not null,
    nickname varchar(50) not null,
    email varchar(50) not null,
    phone varchar(20) not null,
    firstname varchar(50) not null,
    lastname varchar(50) not null,
    gender varchar(10) not null

);