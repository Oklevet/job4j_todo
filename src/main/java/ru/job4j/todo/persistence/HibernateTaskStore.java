package ru.job4j.todo.persistence;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {

    private final SessionFactory sf;

    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            var q = session.createQuery("delete Task as t where t.id = :id")
                            .setParameter("id", id)
                            .executeUpdate();
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean getDone(Task task, boolean done) {
        Session session = sf.openSession();
        boolean result = false;
        task.setDone(!task.isDone());
        try {
            session.beginTransaction();
            session.createQuery("update Task set done = :done where id = :id")
                    .setParameter("id", task.getId())
                    .setParameter("done", task.isDone())
                    .executeUpdate();
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Optional<Task> result = Optional.empty();
        try {
            session.beginTransaction();
            result = Optional.ofNullable(session.get(Task.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        List<Task> result = List.of();
        try {
            session.beginTransaction();
            result = session.createQuery("from Task", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Collection<Task> findAllDoneOrNew(boolean done) {
        Session session = sf.openSession();
        List<Task> result = List.of();
        try {
            session.beginTransaction();
            result = session.createQuery("from Task x where x.done = :done", Task.class)
                    .setParameter("done", done)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }
}
