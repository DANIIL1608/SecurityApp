package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface RoleDao {
    void save(Role role);
    List<Role> getAll();
    Role findById(int id);
}
