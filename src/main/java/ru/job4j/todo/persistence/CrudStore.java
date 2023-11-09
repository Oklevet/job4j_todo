package ru.job4j.todo.persistence;

import lombok.AllArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
@AllArgsConstructor
public class CrudStore {
    private final SessionFactory sf;

    public boolean run(Consumer<Session> command) {
        boolean result = true;
        try {
            tx(session -> {
                        command.accept(session);
                        return null;
                    }
            );
        } catch (HibernateException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean run(String query, Map<String, Object> args) {
        boolean result = true;
        try {
            Consumer<Session> command = session -> {
                var sq = session
                        .createQuery(query);
                for (Map.Entry<String, Object> arg : args.entrySet()) {
                    sq.setParameter(arg.getKey(), arg.getValue());
                }
                sq.executeUpdate();
            };
            run(command);
        } catch (HibernateException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.uniqueResultOptional();
        };
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, cl)
                .list();
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl, Map<String, Object> args) {
        System.out.println(".CrudS find all");
        Function<Session, List<T>> command = session -> {
            var sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
                System.out.println(".CrudS in loop obj = " + arg.getValue());
            }
            System.out.println(".CrudS aft loop");
            var s = sq.getResultList();

            System.out.println(".CrudS print made list");
            s.forEach(System.out::println);
            return s;
        };
        return tx(command);
    }

    public <T> T tx(Function<Session, T> command) {
        System.out.println(".CrudS start tx");
        Session session = sf.openSession();
        System.out.println(".CrudS open session");
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            System.out.println(".CrudS began trans");
            T rsl = command.apply(session);
            System.out.println(".CrudS applyed");
            transaction.commit();
            System.out.println(".CrudS commited");
            return rsl;
        } catch (Exception e) {
            System.out.println(".CrudS tx exception");
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}