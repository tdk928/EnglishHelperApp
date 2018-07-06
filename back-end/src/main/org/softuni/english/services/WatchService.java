package org.softuni.english.services;

import org.softuni.english.models.AddWatchBindingModel;
import org.softuni.english.models.WatchViewModel;

import java.util.List;

public interface WatchService {
    WatchViewModel findById(String id);

    List<WatchViewModel> getAllWatches();

    WatchViewModel addWatch(AddWatchBindingModel addWatchBindingModel);

    boolean clearAllPromotions();
}
