package org.softuni.english.controllers;

import com.google.gson.Gson;
import org.softuni.english.entities.User;
import org.softuni.english.models.BindingModels.UserRegisterBindingModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;
import java.util.List;

@RestController
public class HomeController {

    @RequestMapping(value = "/t3st")
    public @ResponseBody
    String doSomething(Principal principal) {
        System.out.println();
        return "dff";
    }

}
