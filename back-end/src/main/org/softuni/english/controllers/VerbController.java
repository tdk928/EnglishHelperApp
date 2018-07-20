package org.softuni.english.controllers;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.services.UserService;
import org.softuni.english.services.VerbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
public class VerbController {

    private static final String DB_DONT_HAVE_THIS_USERNAME = "We don't have user with this username...";
    private static final String ALL_VERBS_ROUTE = "/verbs/all";
    private static final String USER_VERBS = "/user/verbs";
    private static final String APPLICATION_JSON = "application/json";
    private static final String USER_MY_VERBS = "/user/myVerbs";
    private static final String VERB_DETAILS_ROUTE = "/verb/details/{id}";
    private static final String VERB_DOESN_T_EXISTS_IN_DB = "Verb doesn't exists in db.";

    private final VerbService verbService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public VerbController(VerbService verbService, UserService userService, ModelMapper modelMapper, Gson gson) {
        this.verbService = verbService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @GetMapping(value = ALL_VERBS_ROUTE,produces = APPLICATION_JSON)
    public ResponseEntity<?> allVerb() {
        List<Verb> allVerbs = this.verbService.getAllVerbs();
        return new ResponseEntity<>(this.gson.toJson(allVerbs), HttpStatus.OK);
    }

    @PostMapping(value = USER_VERBS,produces = APPLICATION_JSON)
    public ResponseEntity<?> userVerb(Principal principal) {
        User user = this.userService.findByUsername(principal.getName());

        if(user == null) {
            return new ResponseEntity<>(this.gson.toJson(DB_DONT_HAVE_THIS_USERNAME), HttpStatus.FORBIDDEN);
        }

        List<Verb> userVerbs = this.verbService.getUserVerbs(user);

        Gson gson = new Gson();
        String json = gson.toJson(userVerbs);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping(value = USER_MY_VERBS,produces = APPLICATION_JSON)
    public ResponseEntity<?> myVerbs(Principal principal) {
        User user = this.userService.findByUsername(principal.getName());

        if(user == null) {
            return new ResponseEntity<>(DB_DONT_HAVE_THIS_USERNAME, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(this.gson.toJson(user.getVerbs()), HttpStatus.OK);
    }

    @GetMapping(VERB_DETAILS_ROUTE)
    public ResponseEntity<?> verbDetails(@PathVariable String id) {
        Verb verb = this.verbService.findById(id);
        if (verb == null) {
            return new ResponseEntity<>(this.gson.toJson(VERB_DOESN_T_EXISTS_IN_DB), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<Verb> verbList = new ArrayList<>();
        verbList.add(verb);
        return new ResponseEntity<>(this.gson.toJson(verbList), HttpStatus.OK);
    }


}
