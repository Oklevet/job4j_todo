package ru.job4j.todo.persistence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePriorityStore implements PriorityStore {

    private final CrudStore crudStore;

    @Override
    public Collection<Priority> findAllPriors() {
        return crudStore.query("from Priority", Priority.class);
    }

    @Override
    public Optional<Priority> findById(int id) {
        return crudStore.optional("from Priority x where x.id = :id", Priority.class,
                Map.of("id", id));
    }
}