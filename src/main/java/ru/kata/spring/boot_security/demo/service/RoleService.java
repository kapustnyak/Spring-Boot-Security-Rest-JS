package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Set<Role> getRoleByUserId(Long id);
    List<Role> getAllRoles();
    Set<Role> getRolesById(List<Long> ids);
}
