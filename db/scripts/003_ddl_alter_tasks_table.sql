ALTER TABLE tasks
    ADD COLUMN IF NOT EXISTS
    todo_user     int     REFERENCES users(id)    NOT NULL;