DROP TABLE IF EXISTS users CASCADE;
DROP SEQUENCE IF EXISTS global_seq CASCADE;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE IF NOT EXISTS users
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    first_name      VARCHAR         NOT NULL,
    last_name       VARCHAR         NOT NULL,
    password        VARCHAR         NOT NULL,
    phone           VARCHAR UNIQUE  NOT NULL
);

CREATE TABLE IF NOT EXISTS signin_users (
    id              SERIAL PRIMARY KEY,
    phone_user      VARCHAR,
    date            TIMESTAMP(3),
    ip              VARCHAR
);

CREATE TABLE IF NOT EXISTS images (
    id              uuid PRIMARY KEY,
    phone_user      VARCHAR,
    name            VARCHAR,
    size            BIGINT,
    mime            VARCHAR
);