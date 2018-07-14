package org.softuni.english.services;

import org.modelmapper.ModelMapper;
import org.softuni.english.entities.Verb;
import org.softuni.english.models.BindingModels.VerbCreateBindingModel;
import org.softuni.english.repositories.VerbRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerbServiceImpl implements VerbService{
    private VerbRepository verbRepository;
    private final ModelMapper modelMapper;

    public VerbServiceImpl(VerbRepository verbRepository, ModelMapper modelMapper) {
        this.verbRepository = verbRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean save(VerbCreateBindingModel verbModel) {
        if(verbModel == null) {
            return false;
        }
        Verb verb = this.modelMapper.map(verbModel, Verb.class);
        this.verbRepository.save(verb);
        return true;
    }

    @Override
    public boolean deleteVerb(String firstForm) {
        Verb verb = this.verbRepository.findFirstByFirstForm(firstForm);
        if(verb == null) {
            return  false;
        }
        this.verbRepository.delete(verb);
        return true;
    }

    @Override
    public List<Verb> getAllVerbs() {
        return this.verbRepository.findAll();
    }
}
