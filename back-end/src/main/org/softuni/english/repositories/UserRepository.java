package org.softuni.english.repositories;

import org.softuni.english.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsernameAndDeletedOnIsNull(String username);

    User findByIdAndDeletedOnIsNull(String id);
}