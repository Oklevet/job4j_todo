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
        try {
            return crudStore.query("from User", User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }

    @Override
    public Optional<User> save(User user) {
        try {
            crudStore.run(session -> session.save(user));
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Map<String, Object> userAttrs = new HashMap<>();
        userAttrs.put("login", login);
        userAttrs.put("password", password);

        try {
            return crudStore.optional(
                    "from User x where x.login = :login and x.password = :password", User.class, userAttrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteByLoginAndPassword(String login, String password) {
        Map<String, Object> userAttrs = new HashMap<>();
        userAttrs.put("login", login);
        userAttrs.put("password", password);
        boolean result = true;

        try {
            crudStore.run(
                    "delete User x where x.login = :login and x.password = :password", userAttrs);

        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}