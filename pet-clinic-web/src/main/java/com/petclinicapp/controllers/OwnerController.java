package com.petclinicapp.controllers;

import com.petclinicapp.model.Owner;
import com.petclinicapp.services.OwnerService;
import com.petclinicapp.services.map.OwnerServiceMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
class OwnerController {
    private final OwnerService ownerService;

    public OwnerController (OwnerService ownerService) {
        this.ownerService = ownerService;

    }

    @GetMapping("/owners")
  public ResponseEntity<Set<Owner>> findOwners () {
        Set<Owner> owners = ownerService.findAll();
        return new ResponseEntity<>(owners, HttpStatus.OK);
  }
}
