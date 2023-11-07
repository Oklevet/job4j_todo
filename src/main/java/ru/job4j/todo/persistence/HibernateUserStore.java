package ru.job4j.todo.persistence;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.*;

@Repository
@AllArgsConstructor
public class HibernateUserStore implements UserStore {
    private final CrudStore crudStore;

    @Override
    public Collection<User> findAll() {
        return crudStore.query("from User", User.class);
    }

    @Override
    public Optional<User> save(User user) {
        return crudStore.run(session -> session.save(user)) ? Optional.of(user) : Optional.empty();
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudStore.optional("from User x where x.login = :login and x.password = :password", User.class,
                Map.of("login", login, "password", password));
    }

    @Override
    public boolean deleteByLoginAndPassword(String login, String password) {
        return crudStore.run("delete User x where x.login = :login and x.password = :password",
                    Map.of("login", login, "password", password));
    }
}