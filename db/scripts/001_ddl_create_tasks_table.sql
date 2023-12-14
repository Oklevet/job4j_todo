CREATE TABLE if not exists tasks (
   id SERIAL PRIMARY KEY,
   title text,
   description TEXT,
   created TIMESTAMP WITHOUT TIME ZONE,
   done BOOLEAN
);