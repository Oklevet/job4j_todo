CREATE TABLE if not exists tasks (
   id SERIAL PRIMARY KEY,
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN
);

INSERT INTO tasks (description, created, done) VALUES
('qwe', current_timestamp, true),
('asd', current_timestamp, true),
('zxc', current_timestamp, true);

select * from tasks