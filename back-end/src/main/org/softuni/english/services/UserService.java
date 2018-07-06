package org.softuni.english.services;

import org.softuni.english.models.RegisterUserBindingModel;
import org.softuni.english.models.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean userExists(String username);

    boolean save(RegisterUserBindingModel user);

    UserViewModel getUserByUsername(String username);
}
