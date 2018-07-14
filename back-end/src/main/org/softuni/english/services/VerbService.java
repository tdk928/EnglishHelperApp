package org.softuni.english.services;

import org.softuni.english.entities.Verb;
import org.softuni.english.models.BindingModels.VerbCreateBindingModel;

import java.util.List;

public interface VerbService {
    boolean save(VerbCreateBindingModel verb);

    boolean deleteVerb(String id);

    List<Verb> getAllVerbs();

//    Verb save(Verb verb);
}
