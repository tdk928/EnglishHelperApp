package org.softuni.english.controllers;

import com.google.gson.Gson;
import org.softuni.english.entities.User;
import org.softuni.english.models.BindingModels.UserRegisterBindingModel;
import org.softuni.english.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountController {

    private static final String EXIST_USER_MESSAGE = "User already exists.";
    private static final String SUCCESSFULLY_REGISTERED_USER = "Successfully registered user.";
    private static final String REGISTER_ROUTE = "/register";
    private static final String SERVER_ERROR = "Something went wrong while processing your request...";
    private static final String STATS_ROUTE = "/stats";
    private static final String APPLICATION_JSON = "application/json";
    private static final String DETAILS_ROUTE = "/details/{id}";
    private static final String USER_NOT_EXISTS = "We don't have this user in db";
    private static final int CODE_NUMBER = 4;
    private static final String CHECK_ROUTE = "/check";
    private static final String IS_ADMIN = "yes";
    private static final String NOT_ADMIN = "no";
    private static final String ALL_USERS_ROUTE = "/users/all";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";


    private final UserService userService;
    private final Gson gson;

    public AccountController(UserService userService, Gson gson) {
        this.userService = userService;
        this.gson = gson;
    }

    @PostMapping(value = REGISTER_ROUTE)
    public ResponseEntity<?> register(@RequestBody UserRegisterBindingModel user) {
        if (this.userService.userExists(user.getUsername())) {
            return new ResponseEntity<>(this.gson.toJson(EXIST_USER_MESSAGE), HttpStatus.BAD_REQUEST);
        }

        if (this.userService.save(user)) {
            return new ResponseEntity<>(SUCCESSFULLY_REGISTERED_USER, HttpStatus.OK);
        }

        return new ResponseEntity<>(this.gson.toJson(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = STATS_ROUTE, produces = APPLICATION_JSON)
    public ResponseEntity<?> allSoretedUsers() {
        List<User> sortedUsers = this.userService.getAllUsers().stream().sorted((a, b) -> b.compareTo(a)).collect(Collectors.toList());
        Gson gson = new Gson();
        String json = gson.toJson(sortedUsers);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping(DETAILS_ROUTE)
    public ResponseEntity<?> userDetails(@PathVariable String id) {
        User user = this.userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(this.gson.toJson(USER_NOT_EXISTS), HttpStatus.FORBIDDEN);
        }
        user.setPhoneNumber(user.getPhoneNumber().substring(CODE_NUMBER));
        List<User> list = new ArrayList<>();
        list.add(user);
        return new ResponseEntity<>(this.gson.toJson(list), HttpStatus.OK);
    }

    @PostMapping(value = CHECK_ROUTE)
    public ResponseEntity<?> checkForAdmin(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(this.gson.toJson(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = this.userService.findByUsername(principal.getName());
        if (user == null) {
            return new ResponseEntity<>(this.gson.toJson(USER_NOT_EXISTS), HttpStatus.FORBIDDEN);
        }

        String json = "";
        if (this.isAdmin(user)) {
            json = this.gson.toJson(IS_ADMIN);
        } else {
           json =  this.gson.toJson(NOT_ADMIN);
        }

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping(value = ALL_USERS_ROUTE, produces = APPLICATION_JSON)
    public ResponseEntity<?> allUsers() {
        List<User> allUsers = this.userService.getAllUsers();
        return new ResponseEntity<>(this.gson.toJson(allUsers), HttpStatus.OK);
    }


    private boolean isAdmin(User user) {
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            return grantedAuthority.getAuthority().equals(ROLE_ADMIN);
        }
        return true;
    }


}
