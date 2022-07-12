DROP TABLE IF EXISTS users CASCADE;
DROP SEQUENCE IF EXISTS global_seq CASCADE;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    first_name      VARCHAR         NOT NULL,
    last_name       VARCHAR         NOT NULL,
    password        VARCHAR         NOT NULL,
    phone           VARCHAR UNIQUE  NOT NULL
);