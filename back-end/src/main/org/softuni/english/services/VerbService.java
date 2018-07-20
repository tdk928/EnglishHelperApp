package org.softuni.english.services;

import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.models.BindingModels.VerbCreateBindingModel;
import org.softuni.english.models.BindingModels.VerbEditBindingModel;

import java.util.List;

public interface VerbService {
    boolean save(VerbCreateBindingModel verb);

    List<Verb> getUserVerbs(User user);

    boolean createVerb(Verb verb);

    boolean deleteVerb(Verb verb);

    boolean editVerb(VerbEditBindingModel model);

    List<Verb> getAllVerbs();

    Verb findById(String id);

//    Verb save(Verb verb);
}
