CREATE TABLE if not exists tasks (
   id SERIAL PRIMARY KEY,
   title text,
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN
);