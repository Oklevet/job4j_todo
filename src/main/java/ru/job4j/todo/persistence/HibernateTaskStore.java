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
        try {
            crudStore.run(session -> session.save(task));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        Map<String, Object> taskAttrs = new HashMap<>();
        taskAttrs.put("id", id);
        boolean result = true;

        try {
            crudStore.run(
                    "delete Task as t where t.id = :id", taskAttrs);

        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public boolean update(Task task) {
        boolean result = true;
        try {
            crudStore.run(session -> session.merge(task));
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public boolean getDone(Task task) {
        Map<String, Object> taskAttrs = new HashMap<>();
        taskAttrs.put("id", task.getId());
        taskAttrs.put("done", !task.isDone());
        boolean result = true;

        try {
            crudStore.run(
                    "update Task set done = :done where id = :id", taskAttrs);

        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public Optional<Task> findById(int id) {
        Map<String, Object> taskAttrs = new HashMap<>();
        taskAttrs.put("id", id);

        try {
            return crudStore.optional(
                    "from Task x where x.id = :id", Task.class, taskAttrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Collection<Task> findAll() {
        try {
            return crudStore.query("from Task", Task.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Task>();
    }

    @Override
    public Collection<Task> findAllDoneOrNew(boolean done) {
        Map<String, Object> taskAttrs = new HashMap<>();
        taskAttrs.put("done", done);
        try {
            return crudStore.query("from Task x where x.done = :done", Task.class, taskAttrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Task>();
    }
}