package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoImp implements RoleDao {

    private final EntityManager entityManager;

    public RoleDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Role role) {
        if (!entityManager.contains(role)) {
            role = entityManager.merge(role);
        }
        entityManager.persist(role);
    }

    @Override
    public List<Role> getAll() {
        Query query = entityManager.createQuery("from Role");
        return query.getResultList();
    }

    @Override
    public Role findById(int id) {
        return entityManager.find(Role.class, id);
    }

}
