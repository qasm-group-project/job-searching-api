SET SCHEMA 'job_searching';

CREATE TABLE users
(
    id   uuid        NOT NULL DEFAULT uuid_generate_v4(),
    name varchar(20) NOT NULL,
    type varchar(20) NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);