package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAOImp;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserDAOImp userDAOImp;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    @Autowired

    public UserService(UserDAOImp userDAOImp, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDAOImp = userDAOImp;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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
        operationsWithRoles(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAOImp.save(user);
    }

    @Transactional
    public void update(int id, User user) {
        user.setId(id);
        operationsWithRoles(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAOImp.save(user);
    }

    @Transactional
    public void delete(int id) {
        userDAOImp.delete(id);
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userDAOImp.findByUsername(username));
        if (user.isEmpty()) throw new UsernameNotFoundException("User" + username + "not found");
        return user.get();
    }

    public void operationsWithRoles(User user) {
        Set<Role> roles = new HashSet<>();
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
            roles.add(roleService.findById(2));
        } else {
            roles.add(roleService.findById(1));
        }
        user.setRoles(roles);
    }
}
