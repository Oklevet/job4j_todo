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

select task0_.id as id1_0_, task0_.created as created2_0_, task0_.description as descript3_0_, task0_.done as done4_0_ from tasks task0_