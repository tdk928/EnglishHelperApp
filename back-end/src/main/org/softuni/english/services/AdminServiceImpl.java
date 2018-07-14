package org.softuni.english.services;


import org.softuni.english.entities.Role;
import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.repositories.AdminRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{
    private static final long ROLE_ADMIN_ID = 2L;

    private AdminRepository adminRepository;
    private RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminRepository = adminRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean checkAdminExistOrCreate() {
        long existingUsers = this.adminRepository.count();
        if(existingUsers == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(this.bCryptPasswordEncoder.encode("admin"));
            admin.setEmail("admin@gmail.com");
            admin.setPhoneNumber("1234567");
            admin.setExperience("Expert");

            HashSet<Role> role = new HashSet<>();
            role.add(this.roleService.findById(ROLE_ADMIN_ID));
            admin.setAuthorities(role);
            this.adminRepository.save(admin);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(String id, String currentlyLoggedInId) {
        User user = this.adminRepository.findByIdAndDeletedOnIsNull(id);
        User currentlyLoggedIn = this.adminRepository.findByIdAndDeletedOnIsNull(currentlyLoggedInId);

        if(currentlyLoggedIn == null || user == null ||  user.getId().equals(currentlyLoggedIn.getId())) {
            System.out.println();
            return false;
        }

        this.adminRepository.delete(user);
        return true;
    }

    @Override
    public User findById(String id) {
        return this.adminRepository.findByIdAndDeletedOnIsNull(id);
    }

    @Override
    public List<User> getAllUsers() {
        return this.adminRepository.findAll();
    }

    @Override
    public boolean deleteVerbInUserList(Verb verb) {
        List<User> allUsers = this.getAllUsers();
        for (User user : allUsers) {
            if(user.getVerbs().contains(verb)) {
                user.removeVerb(verb);
                this.adminRepository.save(user);
            }
        }

        return true;

    }
}
