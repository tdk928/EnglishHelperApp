package org.softuni.english.controllers;

import com.google.gson.Gson;
import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.services.AdminService;
import org.softuni.english.services.VerbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
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
        Verb verbForDeleted = this.verbService.findById(id);
        if(this.adminService.deleteVerbInUserList(verbForDeleted)) {
            this.verbService.deleteVerb(verbForDeleted);
            return new ResponseEntity<>("ok iztrih verb", HttpStatus.OK);
        }

        return new ResponseEntity<>("Something went wrong while processing your request...", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/check")
    public ResponseEntity<?> checkForAdmin(HttpServletRequestWrapper httpRequestHandler) {

        String currentlyLoggedInId = httpRequestHandler.getHeader("id");
        User user = this.adminService.findById(currentlyLoggedInId);
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


}
