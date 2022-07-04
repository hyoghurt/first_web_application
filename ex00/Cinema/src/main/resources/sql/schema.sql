CREATE TABLE IF NOT EXISTS users (
    id serial primary key,
    first_name text not null,
    last_name text not null,
    phone text unique,
    password text not null
);
