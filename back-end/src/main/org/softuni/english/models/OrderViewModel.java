package org.softuni.english.models;

import java.time.LocalDate;

public class OrderViewModel {
    private LocalDate creationDate;

    private WatchViewModel watch;

    public OrderViewModel() {
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public WatchViewModel getWatch() {
        return watch;
    }

    public void setWatch(WatchViewModel watch) {
        this.watch = watch;
    }
}
