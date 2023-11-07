package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistence.UserStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private final UserStore userStore;

    @Override
    public Optional<User> save(User user) {
        return userStore.save(user);
    }

    @Override
    public Collection<User> findAll() {
        return userStore.findAll();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String email, String password) {
        return userStore.findByLoginAndPassword(email, password);
    }

    @Override
    public boolean deleteByLoginAndPassword(String email, String password) {
        var fileOptional = findByLoginAndPassword(email, password);
        if (fileOptional.isPresent()) {
            userStore.deleteByLoginAndPassword(email, password);
            return true;
        }
        return false;
    }

}
