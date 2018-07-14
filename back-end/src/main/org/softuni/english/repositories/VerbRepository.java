package org.softuni.english.repositories;

import org.softuni.english.entities.Verb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerbRepository extends JpaRepository<Verb,String> {
     Verb findFirstByFirstForm(String id);

     Verb findFirstById(String id);
}
