CREATE TABLE if not exists users (
   id SERIAL PRIMARY KEY,
   "name" text       not null,
   login TEXT unique not null,
   password TEXT     not null
);

insert into users ("name", login, password) values  ('Nikanor', 'qwe', 'qwe');

select * from users;
select * from tasks;
update tasks set done = 'true' where id = 7