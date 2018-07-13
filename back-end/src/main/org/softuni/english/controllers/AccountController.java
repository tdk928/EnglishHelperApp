package org.softuni.english.controllers;

import com.google.gson.Gson;
import org.softuni.english.common.JwtAuthorizationFilter;
import org.softuni.english.entities.User;
import org.softuni.english.models.BindingModels.UserRegisterBindingModel;
import org.softuni.english.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

@RestController

public class AccountController {

    private static final String EXIST_USER_MESSAGE = "User already exists.";
    private static final String SUCCESSFULLY_REGISTERED_USER = "Successfully registered user.";
    private final Gson gson;

    private final UserService userService;

    public AccountController(Gson gson, UserService userService) {
        this.gson = gson;
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

    @PostMapping(value = "/check")
    public ResponseEntity<?> checkForAdmin(HttpServletRequestWrapper httpRequestHandler) {
//        JwtAuthorizationFilter jw  = new JwtAuthorizationFilter(authenticationManager(), this.userService),null);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentlyLoggedInId = httpRequestHandler.getHeader("id");
        User user = this.userService.findById(currentlyLoggedInId);
        if (user == null) {
            return new ResponseEntity<>("Something went wrong while processing your request...", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String json = "";
        if (this.isAdmin(user)) {
            json = new Gson().toJson("yes");
        } else {
            json = new Gson().toJson("no");
        }

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    private boolean isAdmin(User user) {
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            return grantedAuthority.getAuthority().equals("ROLE_ADMIN");
        }

        return true;
    }

//    @PostMapping(value = "/login")
//    public ResponseEntity<?> login() {
//       System.out.println("login4i");
//
//        return new ResponseEntity<>("Something went wrong while processing your request...", HttpStatus.INTERNAL_SERVER_ERROR);
//    }


}
