package org.softuni.english.services;

import org.softuni.english.entities.User;

import java.util.List;

public interface AdminService {
    boolean checkAdminExistOrCreate();

    boolean deleteUser(String id, String currentlyLoggedInId);

    List<User> getAllUsers();
}
