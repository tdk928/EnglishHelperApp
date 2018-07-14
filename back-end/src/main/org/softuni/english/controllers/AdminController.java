package org.softuni.english.controllers;

import com.google.gson.Gson;
import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.services.AdminService;
import org.softuni.english.services.VerbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;
import java.util.List;

@RestController
public class AdminController {
    private final AdminService adminService;
    private final VerbService verbService;

    public AdminController(AdminService adminService, VerbService verbService) {
        this.adminService = adminService;
        this.verbService = verbService;
    }

    @GetMapping(value = "/users/all", produces = "application/json")
    public ResponseEntity<?> allUsers(Principal principal) {
        List<User> allUsers = this.adminService.getAllUsers();
        String json = new Gson().toJson(allUsers);
        System.out.println();
        return new ResponseEntity<>(json, HttpStatus.OK);
    }



    @PostMapping( value =  "/user/delete/{id}" )
    public ResponseEntity<?> deleteUser(@PathVariable String id, HttpServletRequestWrapper httpRequestHandler) {
        String currentlyLoggedInId = httpRequestHandler.getHeader("id");

        if (this.adminService.deleteUser(id,currentlyLoggedInId)) {
            return new ResponseEntity<>("Successfully deleted.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong while processing your request...", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/verb/delete/{id}")
    public ResponseEntity<?> deleteVerb(@PathVariable String id) {
        System.out.println("vlqzah");
//        if (this.verbService.deleteVerb(id)) {
//            return new ResponseEntity<>("Successfully deleted verb.", HttpStatus.OK);
//        }

        return new ResponseEntity<>("Something went wrong while processing your request...", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
