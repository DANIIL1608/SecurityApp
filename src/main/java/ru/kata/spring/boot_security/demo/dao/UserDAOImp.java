package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDAOImp implements UserDAO{
    private final EntityManager entityManager;


    public UserDAOImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> getAll() {
        Query query = entityManager.createQuery("from User");
        return query.getResultList();
    }

    @Override
    public void save(User user) {
        if (!entityManager.contains(user)) {
            user = entityManager.merge(user);
        }
        entityManager.persist(user);
    }
    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }
    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username=:username", User.class);
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        if (users.isEmpty()) return null;
        return users.get(0);
    }

}
