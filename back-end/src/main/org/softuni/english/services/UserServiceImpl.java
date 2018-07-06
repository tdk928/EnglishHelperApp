package org.softuni.english.services;

import org.modelmapper.ModelMapper;
import org.softuni.english.entities.User;
import org.softuni.english.models.RegisterUserBindingModel;
import org.softuni.english.models.UserViewModel;
import org.softuni.english.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    User findUserEntityByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Username was not found.");
        }

        return user;
    }

    @Override
    public boolean userExists(String username) {
        return this.userRepository.findByUsername(username) != null;
    }

    @Override
    public boolean save(RegisterUserBindingModel userModel) {
        User user = this.modelMapper.map(userModel, User.class);
        System.out.println(user.getUsername());
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        this.userRepository.save(user);

        return true;
    }

    @Override
    public UserViewModel getUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username);

        return
                this.modelMapper.map(user, UserViewModel.class);
    }
}
