package ru.job4j.todo.utility;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

public class CategoriesUtility {

    public static Task setCategoriesToTask(Task task, List<Integer> categoriesId) {
        task.setCategories(new ArrayList<>());
        categoriesId.stream().forEach(x -> {
            Category category = new Category();
            category.setId(x);
            task.getCategories().add(category);
        });
        return task;
    }
}
