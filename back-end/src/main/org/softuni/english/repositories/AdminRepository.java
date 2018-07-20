package org.softuni.english.repositories;

import org.softuni.english.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<User,String> {
    User findByIdAndDeletedOnIsNull(String id);

    User findByUsernameAndDeletedOnIsNull(String username);
}
