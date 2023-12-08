package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.persistence.PriorityStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {

    private PriorityStore priorityStore;

    @Override
    public Collection<Priority> findAllPriors() {
        return priorityStore.findAllPriors();
    }

    @Override
    public Optional<Priority> findByName(String name) {
        return priorityStore.findByName(name);
    }
}
