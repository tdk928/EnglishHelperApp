package org.softuni.english.services;


import org.modelmapper.ModelMapper;
import org.softuni.english.entities.Role;
import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.models.BindingModels.UserEditBindingModel;
import org.softuni.english.repositories.AdminRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private static final long ROLE_ADMIN_ID = 2L;
    private static final long ROLE_USER_ID = 1L;
    public static final String BG_CODE = "+359";

    private AdminRepository adminRepository;
    private RoleService roleService;
    private Validator validator;
    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, RoleService roleService, Validator validator, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminRepository = adminRepository;
        this.roleService = roleService;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean checkAdminExistOrCreate() {
        long existingUsers = this.adminRepository.count();
        if (existingUsers == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(this.bCryptPasswordEncoder.encode("admin"));
            admin.setEmail("admin@gmail.com");
            admin.setPhoneNumber("+3591234567");
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
    public boolean deleteUser(String id) {
        User user = this.adminRepository.findByIdAndDeletedOnIsNull(id);
        if(user == null) {
            return false;
        }

        this.adminRepository.delete(user);
        return true;
    }

    @Override
    public User findByName(String name) {
        return this.adminRepository.findByUsernameAndDeletedOnIsNull(name);
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
            user.getVerbs().stream().filter(e -> e.getId().equals(verb.getId())).forEach(el -> {
                user.getVerbs().remove(el);
                this.adminRepository.save(user);
            });
        }

        return true;
    }

    @Override
    public boolean editUser(UserEditBindingModel userModel) {
        Set<ConstraintViolation<UserEditBindingModel>> violations = validator.validate(userModel);
        if (violations.size() > 0) {
            return false;
        }

        User oldUser = this.adminRepository.findByIdAndDeletedOnIsNull(userModel.getId());
        User newUser = this.modelMapper.map(userModel,User.class);

        if (!oldUser.getPassword().equals(newUser.getPassword())) {
            newUser.setPassword(this.bCryptPasswordEncoder.encode(userModel.getPassword()));
        }
        newUser.setPhoneNumber(BG_CODE +userModel.getPhoneNumber());
        newUser.setEmail(userModel.getEmail());
        newUser.setExperience(oldUser.getExperience());
        if (userModel.getIsAdmin()) {
            this.makeUserAdmin(newUser);
        } else {
            this.makeAdminUser(newUser);
        }

        this.adminRepository.save(newUser);

        return true;
    }

    @Override
    public boolean save(User user) {
        this.adminRepository.save(user);
        return true;
    }

    @Override
    public int checkPoint(Verb verb) {
        return verb.getFirstForm().length() + verb.getSecondForm().length() + verb.getThirdForm().length();

    }

    private void makeUserAdmin(User user) {
        HashSet<Role> role = new HashSet<>();
        role.add(this.roleService.findById(ROLE_ADMIN_ID));
        user.setAuthorities(role);
    }

    private void makeAdminUser(User user) {
        HashSet<Role> role = new HashSet<>();
        role.add(this.roleService.findById(ROLE_USER_ID));
        user.setAuthorities(role);
    }

}
