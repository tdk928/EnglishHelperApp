package org.softuni.english.terminal;

import org.softuni.english.services.AdminService;
import org.softuni.english.services.RoleService;
import org.softuni.english.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUp implements CommandLineRunner {
    private final RoleService roleService;
    private final AdminService adminService;

    public StartUp(RoleService roleService, UserService userService, AdminService adminService) {
        this.roleService = roleService;
        this.adminService = adminService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.roleService.checkRoleExistOrCreate();
        this.adminService.checkAdminExistOrCreate();

    }
}

