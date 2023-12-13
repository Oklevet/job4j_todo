package ru.job4j.todo.persistence;

import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;

public interface CategoryStore {

    Collection<Category> findAll();

    List<Category> findByIdList(int id);
}
