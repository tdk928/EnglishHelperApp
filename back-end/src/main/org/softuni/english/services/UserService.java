package org.softuni.english.services;

import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.models.BindingModels.UserEditBindingModel;
import org.softuni.english.models.BindingModels.UserRegisterBindingModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public interface UserService extends UserDetailsService {
    boolean userExists(String username);

    boolean save(UserRegisterBindingModel userModel);

    List<User> getAllUsers();

    User findByUsername(String username);

    User findById(String id);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
