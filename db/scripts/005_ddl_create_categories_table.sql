CREATE TABLE if not exists categories (
   id SERIAL PRIMARY KEY,
   name TEXT UNIQUE NOT NULL
);

CREATE TABLE if not exists task_categories (
   id SERIAL PRIMARY KEY,
   tasks_id int REFERENCES tasks(id) NOT NULL,
   categories_id int REFERENCES categories(id) NOT NULL,
   unique (tasks_id, categories_id)
);