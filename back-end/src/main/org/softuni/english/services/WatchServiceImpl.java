package org.softuni.english.services;


import org.modelmapper.ModelMapper;
import org.softuni.english.entities.Watch;
import org.softuni.english.models.AddWatchBindingModel;
import org.softuni.english.models.WatchViewModel;
import org.softuni.english.repositories.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchServiceImpl implements WatchService {
    private final WatchRepository watchRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public WatchServiceImpl(WatchRepository watchRepository, ModelMapper modelMapper) {
        this.watchRepository = watchRepository;
        this.modelMapper = modelMapper;
    }

    Watch findWatchEntityById(String id) {
        return this.watchRepository.findById(id)
                .orElse(null);
    }

    @Override
    public WatchViewModel findById(String id) {
        Watch foundWatch = this.watchRepository.findById(id)
                .orElse(null);

        return this.modelMapper.map(foundWatch, WatchViewModel.class);
    }

    @Override
    public List<WatchViewModel> getAllWatches() {
        return this.watchRepository
                .findAll()
                .stream()
                .map(x -> this.modelMapper.map(x, WatchViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public WatchViewModel addWatch(AddWatchBindingModel addWatchBindingModel) {
        Watch watch = this.modelMapper.map(addWatchBindingModel, Watch.class);

        Watch savedEntity = this.watchRepository.save(watch);

        return this.modelMapper
                .map(savedEntity, WatchViewModel.class);
    }

    @Override
    public boolean clearAllPromotions() {
        for (Watch watch : this.watchRepository.findAll()) {
            watch.setDiscountPercentage(0);
            this.watchRepository.save(watch);
        }
        
        return true;
    }
}
