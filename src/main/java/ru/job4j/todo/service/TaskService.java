package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task save(Task task, List<Integer> categoriesId);

    boolean deleteById(int id);

    boolean update(Task task);

    boolean getDone(Task task);

    Optional<Task> findById(int id);

    Collection<Task> findAll(User user);

    Collection<Task> findAllDoneOrNew(User user, boolean done);
}
