package ru.job4j.todo.persistence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.*;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {
    private final CrudStore crudStore;

    @Override
    public Task save(Task task) {
        return crudStore.run(session -> session.save(task)) ? task : null;
    }

    @Override
    public boolean deleteById(int id) {
        return crudStore.run(
                    "delete Task as t where t.id = :id", Map.of("id", id));
    }

    @Override
    public boolean update(Task task) {
        return crudStore.run(session -> session.merge(task));
    }

    @Override
    public boolean getDone(Task task) {
        return crudStore.run(
                    "update Task set done = :done where id = :id",
                Map.of("id", task.getId(), "done", !task.isDone()));
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudStore.optional("from Task x where x.id = :id", Task.class, Map.of("id", id));
    }

    @Override
    public Collection<Task> findAll(User user) {
        return crudStore.query("from Task x where x.user = :user", Task.class,
                    Map.of("user", user));
    }

    @Override
    public Collection<Task> findAllDoneOrNew(User user, boolean done) {
        return crudStore.query("from Task x where x.todo_user = :user_id", Task.class,
                Map.of("user_id", user.getId(), "done", done));
    }
}