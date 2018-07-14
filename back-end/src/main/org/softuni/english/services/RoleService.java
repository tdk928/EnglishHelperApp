package org.softuni.english.services;

import org.softuni.english.entities.Role;

public interface RoleService {
    boolean save(Role role);

    boolean findByAuthority(String name);

    Role findById(Long id);

    boolean checkRoleExistOrCreate();
}
