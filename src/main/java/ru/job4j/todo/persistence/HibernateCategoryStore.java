package ru.job4j.todo.persistence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernateCategoryStore implements CategoryStore {

    CrudStore crudStore;

    @Override
    public Collection<Category> findAll() {
        return crudStore.query("from Category x", Category.class);
    }

    @Override
    public List<Category> findByIdList(int id) {
        return crudStore.query(
                "from Category c where c.id in "
                    + "(select tc.categories_id from task_categories where tc.tasks_id = :id)", Category.class,
                Map.of("id", id));
    }
}
