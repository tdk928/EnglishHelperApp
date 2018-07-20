package org.softuni.english.services;

import org.modelmapper.ModelMapper;
import org.softuni.english.entities.Role;
import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.models.BindingModels.UserEditBindingModel;
import org.softuni.english.models.BindingModels.UserRegisterBindingModel;
import org.softuni.english.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final long USER_ID = 1L;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleService roleService;

    private Validator validator;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

    }

    User findUserEntityByUsername(String username) {
        return this.userRepository.findByUsernameAndDeletedOnIsNull(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findFirstByUsernameAndDeletedOnIsNull(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username was not found.");
        }

        return user;
    }

    @Override
    public boolean userExists(String username) {
        return this.userRepository.findByUsernameAndDeletedOnIsNull(username) != null;
    }

    @Override
    public boolean save(UserRegisterBindingModel userModel) {
        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(userModel);
        if (violations.size() > 0) {
            return false;
        }

        if (!userModel.getPassword().equals(userModel.getConfirmPassword())) {
            return false;
        }
        User user = this.modelMapper.map(userModel, User.class);


        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setExperience("Beginner");
        user.setPhoneNumber("+359" + user.getPhoneNumber());
        this.makeUserAdmin(user);
        this.userRepository.save(user);

        return true;
    }




    private void makeUserAdmin(User user) {
        HashSet<Role> role = new HashSet<>();
        role.add(this.roleService.findById(USER_ID));
        user.setAuthorities(role);
    }





    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User findByUsername(String id) {
        return this.userRepository.findByUsernameAndDeletedOnIsNull(id);
    }

    @Override
    public User findById(String id) {
        return this.userRepository.findByIdAndDeletedOnIsNull(id);
    }


}
