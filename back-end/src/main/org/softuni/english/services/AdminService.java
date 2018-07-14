package org.softuni.english.services;

import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;

import java.util.List;

public interface AdminService {
    boolean checkAdminExistOrCreate();

    boolean deleteUser(String id, String currentlyLoggedInId);

    User findById(String id);

    List<User> getAllUsers();

    boolean deleteVerbInUserList(Verb verb);
}
