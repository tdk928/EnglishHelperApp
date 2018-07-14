package org.softuni.english.controllers;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.models.BindingModels.UserRegisterBindingModel;
import org.softuni.english.models.BindingModels.VerbCreateBindingModel;
import org.softuni.english.services.UserService;
import org.softuni.english.services.VerbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;
import java.util.stream.Collectors;

@RestController

public class AccountController {

    private static final String EXIST_USER_MESSAGE = "User already exists.";
    private static final String SUCCESSFULLY_REGISTERED_USER = "Successfully registered user.";
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final VerbService verbService;

    private final UserService userService;

    public AccountController(Gson gson, ModelMapper modelMapper, VerbService verbService, UserService userService) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.verbService = verbService;
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterBindingModel user) {
        if (this.userService.userExists(user.getUsername())) {
            return new ResponseEntity<>(EXIST_USER_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (this.userService.save(user)) {
            return new ResponseEntity<>(SUCCESSFULLY_REGISTERED_USER, HttpStatus.OK);
        }

        return new ResponseEntity<>("Something went wrong while processing your request...", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping(value = "/user/add", produces = "application/json")
    public ResponseEntity<?> addVerb(@RequestBody VerbCreateBindingModel verb, HttpServletRequestWrapper httpRequestHandler) {
        if (verb == null) {
            return new ResponseEntity<>("nqma takuv verb", HttpStatus.BAD_REQUEST);
        }

        String currentlyLoggedInUserId = httpRequestHandler.getHeader("id");
        User user = this.userService.findById(currentlyLoggedInUserId);

        if (user == null) {
            return new ResponseEntity<>("nqma takuv user", HttpStatus.BAD_REQUEST);
        }

        Verb currentlyVerb = this.modelMapper.map(verb, Verb.class);
        user.createVerb(currentlyVerb);
        user.addPoints(this.userService.checkPoint(currentlyVerb));

        if (this.userService.save(user)) {
            return new ResponseEntity<>("uspq da creatnish", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong while processing your request...", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/stats", produces = "application/json")
    public ResponseEntity<?> allUsers() {
        List<User> sortedUsers = this.userService.getAllUsers().stream().sorted((a, b) -> b.compareTo(a)).collect(Collectors.toList());
        Gson gson = new Gson();
        String json = gson.toJson(sortedUsers);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }




}
