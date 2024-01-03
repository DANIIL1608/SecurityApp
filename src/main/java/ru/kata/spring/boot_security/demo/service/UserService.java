package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAOImp;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserDAOImp userDAOImp;

    @Autowired

    public UserService(UserDAOImp userDAOImp) {
        this.userDAOImp = userDAOImp;
    }

    public List<User> getAll() {
        return userDAOImp.getAll();
    }

    public User findById(int id) {
        Optional<User> user = Optional.ofNullable(userDAOImp.findById(id));
        return user.orElse(null);
    }

    @Transactional
    public void save(User user) {
        userDAOImp.save(user);
    }

    @Transactional
    public void update(int id, User user) {
        user.setId(id);
        userDAOImp.save(user);
    }

    @Transactional
    public void delete(int id) {
        userDAOImp.delete(id);
    }
}
