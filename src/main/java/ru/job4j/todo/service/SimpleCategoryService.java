package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.persistence.CategoryStore;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    CategoryStore categoryStore;

    @Override
    public Collection<Category> findAll() {
        return categoryStore.findAll();
    }

    @Override
    public List<Category> findByIdList(int id) {
        return categoryStore.findByIdList(id);
    }
}