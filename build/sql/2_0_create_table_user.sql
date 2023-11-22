SET SCHEMA 'job_searching';

CREATE TABLE users
(
    id   uuid        NOT NULL DEFAULT uuid_generate_v4(),
    name varchar(20) NOT NULL,
    type varchar(20) NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);
CREATE TABLE seeker
(
    id uuid not null default uuid_generate_v4() primary key ,
    username varchar(255) not null unique,
    password varchar(255) not null,
    nickname varchar(255) not null,
    email varchar(255) not null,
    phone varchar(255) not null,
    firstname varchar(255) not null,
    lastname varchar(255) not null,
    gender varchar(255) not null
);
create table provider
(
    id uuid not null default uuid_generate_v4() primary key,
    username varchar(255) not null unique,
    password varchar(255) not null,
    email varchar(255) not null,
    company_name varchar(255) not null,
    company_contact_number varchar(255) not null,
    company_location varchar(255) not null,
    role varchar(255) not null
);