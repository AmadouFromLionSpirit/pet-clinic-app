package com.petclinicapp.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class indexController {
    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @RequestMapping(path = {"","/","index","index.html"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }




}
