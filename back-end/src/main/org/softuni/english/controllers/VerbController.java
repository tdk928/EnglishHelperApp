package org.softuni.english.controllers;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.softuni.english.entities.User;
import org.softuni.english.entities.Verb;
import org.softuni.english.models.BindingModels.VerbCreateBindingModel;
import org.softuni.english.services.UserService;
import org.softuni.english.services.VerbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
//@PreAuthorize("hasRole('ADMIN')")
public class VerbController {
    private static final String SUCCESSFULLY_CREATED_VERB = "SUCCESSFULLY CREATED VERB";
    private static final String DB_DONT_HAVE_THIS_USERNAME = "We don't have user with this username...";

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

    @PostMapping(value = "/admin/create",produces = "application/json")
    public ResponseEntity<?> createVerb(@RequestBody VerbCreateBindingModel verb, HttpServletRequestWrapper httpRequestHandler) {
        if(verb == null) {
            return new ResponseEntity<>(DB_DONT_HAVE_THIS_USERNAME, HttpStatus.BAD_REQUEST);
        }

        String currentlyLoggedInUserId = httpRequestHandler.getHeader("id");
        User user = this.userService.findById(currentlyLoggedInUserId);

        if(user == null) {
            return new ResponseEntity<>(DB_DONT_HAVE_THIS_USERNAME, HttpStatus.BAD_REQUEST);
        }

        Verb currentlyVerb = this.modelMapper.map(verb, Verb.class);

        if(this.verbService.createVerb(currentlyVerb)) {
            return new ResponseEntity<>(SUCCESSFULLY_CREATED_VERB, HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong while processing your request...", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/verbs/all",produces = "application/json")
    public ResponseEntity<?> allVerb() {
        List<Verb> allVerbs = this.verbService.getAllVerbs();
//        String json = new Gson().toJson(allVerbs);
        return new ResponseEntity<>(allVerbs, HttpStatus.OK);
    }

    @PostMapping(value = "/user/verbs",produces = "application/json")
    public ResponseEntity<?> userVerb(HttpServletRequestWrapper httpRequestHandler) {
        String currentlyLoggedInUserId = httpRequestHandler.getHeader("id");
        User user = this.userService.findById(currentlyLoggedInUserId);

        if(user == null) {
            return new ResponseEntity<>(DB_DONT_HAVE_THIS_USERNAME, HttpStatus.BAD_REQUEST);
        }

        List<Verb> userVerbs = this.verbService.getUserVerbs(user);

        Gson gson = new Gson();
        String json = gson.toJson(userVerbs);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
