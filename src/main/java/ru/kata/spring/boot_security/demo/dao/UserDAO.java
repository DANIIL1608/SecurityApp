package ru.kata.spring.boot_security.demo.dao;




import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAll();
    User findById(int id);
    void save(User user);
    void delete(int id);
    User findByUsername(String username);
}
