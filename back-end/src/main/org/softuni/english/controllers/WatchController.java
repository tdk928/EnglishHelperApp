package org.softuni.english.controllers;

import com.google.gson.Gson;
import org.softuni.english.models.AddWatchBindingModel;
import org.softuni.english.models.WatchViewModel;
import org.softuni.english.services.OrderService;
import org.softuni.english.services.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/watches")
public class WatchController {
    private final OrderService orderService;

    private final WatchService watchService;

    private final Gson gson;

    @Autowired
    public WatchController(OrderService orderService, WatchService watchService, Gson gson) {
        this.orderService = orderService;
        this.watchService = watchService;
        this.gson = gson;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public @ResponseBody String allWatches() {
        return this.gson.toJson(this.watchService.getAllWatches());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addWatch(@ModelAttribute AddWatchBindingModel addWatchBindingModel) {
        WatchViewModel watchViewModel = this.watchService.addWatch(addWatchBindingModel);

        if(watchViewModel == null) {
            return new ResponseEntity<>("Error: Failed to add Watch.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(watchViewModel, HttpStatus.CREATED);
    }

    @GetMapping(value = "/details", produces = "application/json")
    public @ResponseBody String details(@RequestParam String id) {
        return this.gson
                .toJson(this.watchService.findById(id));
    }

    @PostMapping(value = "/buy", produces = "application/json")
    public ResponseEntity<?> buyWatch(@RequestParam String id, Principal principal) {
        boolean orderResult = this.orderService.createOrder(id, principal.getName());

        if(!orderResult) {
            return new ResponseEntity<>("Something went wrong with your Order.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.watchService.findById(id), HttpStatus.OK);
    }
}
