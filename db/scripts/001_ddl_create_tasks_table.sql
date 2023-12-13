CREATE TABLE if not exists tasks (
   id SERIAL PRIMARY KEY,
   title text,
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN
);

truncate table task_categories;
truncate table tasks, task_categories cascade;

select * from task_categories;
select * from tasks;
select * from categories;

select * from tasks t, task_categories tc, categories c where t.id = tc.tasks_id
    and c.id = tc.categories_id;
commit;

SELECT * FROM crosstab('select tk.id from tasks tk', 'select tk.id from tasks tk') ;