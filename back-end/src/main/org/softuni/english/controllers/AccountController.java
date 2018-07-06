package org.softuni.english.controllers;

import com.google.gson.Gson;
import org.softuni.english.models.RegisterUserBindingModel;
import org.softuni.english.models.UserViewModel;
import org.softuni.english.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class AccountController {

    private final Gson gson;

    private final UserService userService;

    public AccountController(Gson gson, UserService userService) {
        this.gson = gson;
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserBindingModel user) {
        if(this.userService.userExists(user.getUsername())) {
            return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
        }

        if(this.userService.save(user)) {
            return new ResponseEntity<>("Successfully registered user.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Something went wrong while processing your request...", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/profile")
    public @ResponseBody String profile(Principal principal) {
        UserViewModel result = this.userService.getUserByUsername(principal.getName());

        return
                this.gson.toJson(result);
    }
}
