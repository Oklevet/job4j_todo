package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface PriorityService {

    Collection<Priority> findAllPriors();

    Optional<Priority> findByName(String name);
}
