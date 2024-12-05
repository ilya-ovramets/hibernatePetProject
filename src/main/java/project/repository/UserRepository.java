package project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import project.entity.Task;
import project.entity.User;

import java.util.List;

public class UserRepository implements IRepository<User> {

    private final Logger log = LogManager.getLogger(UserRepository.class);

    @Override
    public User findById(long id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()){

            return session.get(User.class,id);

        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }

    }

    @Override
    public List<User> findAll() {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            return session.createQuery("from Users", User.class).list();

        }catch (Exception e){
            log.error("Error finding all Tags: " + e.getMessage());
            return null;
        }

    }

    @Override
    public void save(User user) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error saving User: " + e.getMessage(), e);
        }

    }

    @Override
    public void update(User user) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error updating User: " + e.getMessage(), e);
        }

    }

    @Override
    public void delete(long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error deleting User: " + e.getMessage(), e);
        }

    }
}