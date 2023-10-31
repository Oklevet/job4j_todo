package ru.job4j.todo.persistence;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskStore {

    Task save(Task task);

    boolean deleteById(int id);

    boolean update(Task task);

    boolean getDone(Task task, boolean done);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findAllDoneOrNew(boolean done);
}