package ru.job4j.todo.persistence;

import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Optional;

public interface PriorityStore {

    Collection<Priority> findAllPriors();

    Optional<Priority> findByName(String name);
}