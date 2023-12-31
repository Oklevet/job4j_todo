package ru.job4j.todo.persistence;

import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TaskStore {

    Task save(Task task);

    boolean deleteById(int id);

    boolean deleteCategoriesByTask(int id);

    boolean update(Task task);

    boolean getDone(Task task);

    Optional<Task> findById(int id);

    Collection<Task> findAll(User user);

    Collection<Task> findAllDoneOrNew(User user, boolean done);
}