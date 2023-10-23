package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.persistence.TaskStore;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class SimpleTaskService implements TaskService {

    private TaskStore taskStore;

    @Override
    public Task save(Task task) {
        return taskStore.save(task);
    }

    @Override
    public boolean deleteById(int id) {
        return taskStore.deleteById(id);
    }

    @Override
    public boolean getDone(Task task) {
        return taskStore.getDone(task);
    }

    @Override
    public boolean update(Task task) {
        return taskStore.update(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    @Override
    public Collection<Task> findAll() {
        return taskStore.findAll();
    }

    @Override
    public Collection<Task> findAllNew() {
        return taskStore.findAllNew();
    }

    @Override
    public Collection<Task> findAllDone() {
        return taskStore.findAllDone();
    }
}