package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(Long id, User updatedUser, List<Long> roleIds);
    void deleteUser(Long id);
    List<User> getAllUsers();
    User getUserByUsername(String username);
    User getUserById(Long id);
}
