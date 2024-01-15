package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImp;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
@Service
public class RoleService {
    private final RoleDaoImp roleDaoImp;

    public RoleService(RoleDaoImp roleDaoImp) {
        this.roleDaoImp = roleDaoImp;
    }
    @Transactional
    public void save(Role role) {
        roleDaoImp.save(role);
    }
    @Transactional
    public List<Role> getAll() {
        return roleDaoImp.getAll();
    }
    @Transactional
    public Role findById(int id) {
        return roleDaoImp.findById(id);
    }

}
