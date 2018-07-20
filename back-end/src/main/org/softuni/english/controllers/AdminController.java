package org.softuni.english.controllers;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.models.BindingModels.UserEditBindingModel;
import org.softuni.english.models.BindingModels.VerbCreateBindingModel;
import org.softuni.english.models.BindingModels.VerbEditBindingModel;
import org.softuni.english.services.AdminService;
import org.softuni.english.services.VerbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class AdminController {
    private static final String USER_NOT_EXISTS = "We don't have this user in db";
    private static final String SUCCESSFULLY_DELETED = "Successfully deleted.";
    private static final String SERVER_ERROR = "Something went wrong while processing your request...";
    private static final String USER_DELETE_ROUTE = "/user/delete/{id}";
    private static final String VERB_DELETE_ROUTE = "/verb/delete/{id}";
    private static final String SUCCESSFULLY_DELETED_VERB = "Successfully deleted verb.";
    private static final String EDIT_USER_ROUTE = "/edit";
    private static final String SUCCESSFULLY_EDITED_USER = "Successfully edited user.";
    private static final String SUCCESSFULLY_EDITED_VERB = "Successfully edited verb.";
    private static final String EDIT_VERB_ROUTE = "/edit/verb";
    private static final String VERB_DOESNT_EXISTS = "Verb doesn't exists in db.";
    private static final String ADD_VERB_ROUTE = "/user/add";
    private static final String APPLICATION_JSON = "application/json";
    private static final String SUCCESSFULLY_CREATED_VERB = "Successfully created verb.";
    private static final String CREATE_VERB_ROUTE = "/admin/create";


    private final AdminService adminService;
    private final VerbService verbService;
    private final ModelMapper modelMapper;
    private final Gson gson;


    public AdminController(AdminService adminService, VerbService verbService, ModelMapper modelMapper, Gson gson) {
        this.adminService = adminService;
        this.verbService = verbService;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @PostMapping(value = USER_DELETE_ROUTE)
    public ResponseEntity<?> deleteUser(@PathVariable String id,Principal principal) {
        User user = this.adminService.findByName(principal.getName());
        if(user == null) {
            return new ResponseEntity<>(this.gson.toJson(USER_NOT_EXISTS), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (this.adminService.deleteUser(id)) {
            return new ResponseEntity<>(this.gson.toJson(SUCCESSFULLY_DELETED), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.gson.toJson(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(VERB_DELETE_ROUTE)
    public ResponseEntity<?> deleteVerb(@PathVariable String id) {
        Verb verbForDeleted = this.verbService.findById(id);
        if (this.adminService.deleteVerbInUserList(verbForDeleted)) {
            this.verbService.deleteVerb(verbForDeleted);
            return new ResponseEntity<>(this.gson.toJson(SUCCESSFULLY_DELETED_VERB), HttpStatus.OK);
        }

        return new ResponseEntity<>(this.gson.toJson(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(EDIT_USER_ROUTE)
    public ResponseEntity<?> editUser(@RequestBody UserEditBindingModel model,Principal principal) {
        User user = this.adminService.findByName(principal.getName());

        if (user == null) {
            return new ResponseEntity<>(this.gson.toJson(USER_NOT_EXISTS), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (this.adminService.editUser(model)) {
            return new ResponseEntity<>(this.gson.toJson(SUCCESSFULLY_EDITED_USER), HttpStatus.OK);
        }

        return new ResponseEntity<>(this.gson.toJson(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(EDIT_VERB_ROUTE)
    public ResponseEntity<?> editVerb(@RequestBody VerbEditBindingModel model, Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(this.gson.toJson(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (this.verbService.editVerb(model)) {
            Verb oldVerb = this.modelMapper.map(model, Verb.class);
            this.adminService.deleteVerbInUserList(oldVerb);
            return new ResponseEntity<>(this.gson.toJson(SUCCESSFULLY_EDITED_VERB), HttpStatus.OK);
        }

        return new ResponseEntity<>(this.gson.toJson(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = ADD_VERB_ROUTE, produces = APPLICATION_JSON)
    public ResponseEntity<?> addVerb(@RequestBody VerbCreateBindingModel verb, Principal principal) {
        if (verb == null) {
            return new ResponseEntity<>(this.gson.toJson(VERB_DOESNT_EXISTS), HttpStatus.BAD_REQUEST);
        }

        User user = this.adminService.findByName(principal.getName());

        if (user == null) {
            return new ResponseEntity<>(this.gson.toJson(USER_NOT_EXISTS), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Verb currentlyVerb = this.modelMapper.map(verb, Verb.class);
        user.createVerb(currentlyVerb);
        user.addPoints(this.adminService.checkPoint(currentlyVerb));

        if (this.adminService.save(user)) {
            return new ResponseEntity<>(this.gson.toJson(SUCCESSFULLY_CREATED_VERB), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.gson.toJson(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = CREATE_VERB_ROUTE,produces = APPLICATION_JSON)
    public ResponseEntity<?> createVerb(@RequestBody VerbCreateBindingModel verb,Principal principal) {
        if(verb == null) {
            return new ResponseEntity<>(this.gson.toJson(SUCCESSFULLY_CREATED_VERB), HttpStatus.BAD_REQUEST);
        }

        User user = this.adminService.findByName(principal.getName());

        if(user == null) {
            return new ResponseEntity<>(this.gson.toJson(USER_NOT_EXISTS), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Verb currentlyVerb = this.modelMapper.map(verb, Verb.class);

        if(this.verbService.createVerb(currentlyVerb)) {
            return new ResponseEntity<>(SUCCESSFULLY_CREATED_VERB, HttpStatus.OK);
        }
        return new ResponseEntity<>(this.gson.toJson(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
