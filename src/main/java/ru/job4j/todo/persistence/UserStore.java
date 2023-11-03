package ru.job4j.todo.persistence;

import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStore {

    Collection<User> findAll();

    Optional<User> save(User user);

    Optional<User> findByLoginAndPassword(String login, String password);

    boolean deleteByLoginAndPassword(String login, String password);
}
