package org.softuni.english.services;

import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.models.BindingModels.UserEditBindingModel;

import java.util.List;

public interface AdminService {
    boolean checkAdminExistOrCreate();

    boolean deleteUser(String id);

    User findByName(String name);

    User findById(String id);

    List<User> getAllUsers();

    boolean deleteVerbInUserList(Verb verb);

    boolean editUser(UserEditBindingModel userModel);

    boolean save(User user);

    int checkPoint(Verb verb);
}
