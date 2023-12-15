package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistence.TaskStore;
import ru.job4j.todo.utility.CategoriesUtility;
import ru.job4j.todo.utility.TimeZoneUtility;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private TaskStore taskStore;

    @Override
    public Task save(Task task, List<Integer> categoriesId) {
        CategoriesUtility.setCategoriesToTask(task, categoriesId);
        return taskStore.save(task);
    }

    @Override
    public boolean deleteById(int id) {
        return taskStore.deleteById(id);
    }

    @Override
    public boolean deleteCategoriesByTask(int id) {
        return taskStore.deleteCategoriesByTask(id);
    }

    @Override
    public boolean getDone(Task task) {
        return taskStore.getDone(task);
    }

    @Override
    public boolean update(Task task, List<Integer> categoriesId) {
        CategoriesUtility.setCategoriesToTask(task, categoriesId);
        return taskStore.update(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    @Override
    public Collection<Task> findAll(User user) {
        Collection<Task> tasks = taskStore.findAll(user);
        TimeZoneUtility.changeToUsersTimeZone(tasks, user);
        return tasks;
    }

    @Override
    public Collection<Task> findAllDoneOrNew(User user, boolean done) {
        Collection<Task> tasks = taskStore.findAllDoneOrNew(user, done);
        TimeZoneUtility.changeToUsersTimeZone(tasks, user);
        return tasks;
    }
}
