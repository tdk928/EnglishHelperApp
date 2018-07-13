package org.softuni.english.services;

import org.softuni.english.entities.User;
import org.softuni.english.models.BindingModels.UserRegisterBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;



public interface UserService extends UserDetailsService {
    boolean userExists(String username);

    boolean save(UserRegisterBindingModel userModel);

    boolean save(User user);

    User findByUsername(String username);

    User findById(String id);

}
