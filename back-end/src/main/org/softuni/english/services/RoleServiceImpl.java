package org.softuni.english.services;

import org.softuni.english.entities.Role;
import org.softuni.english.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean save(Role role){
        this.roleRepository.save(role);
        return true;

    }

    @Override
    public boolean findByAuthority(String authority) {
        Role role = this.roleRepository.findByAuthority(authority);
        return role != null;
    }

    @Override
    public Role findById(Long id) {
        return this.roleRepository.findById(id);
    }

    @Override
    public boolean checkRoleExistOrCreate() {
        long existingRoles = this.roleRepository.count();
        if(existingRoles == 0) {
            Role userRole = new Role();
            userRole.setId(1);
            userRole.setAuthority("ROLE_USER");
            this.roleRepository.save(userRole);

            Role adminRole = new Role();
            adminRole.setId(2);
            adminRole.setAuthority("ROLE_ADMIN");
            this.roleRepository.save(adminRole);
            return true;
        }
        return false;
    }
}
