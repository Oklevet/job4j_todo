CREATE TABLE if not exists users (
   id SERIAL PRIMARY KEY,
   "name" text       not null,
   login TEXT unique not null,
   password TEXT     not null
);