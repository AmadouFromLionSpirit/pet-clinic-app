package com.petclinicapp.controllers;

import com.petclinicapp.model.Owner;
import com.petclinicapp.repositories.OwnerRepository;
import com.petclinicapp.services.OwnerService;
import com.petclinicapp.services.map.OwnerServiceMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
class OwnerController {
    private final OwnerServiceMap ownerServiceMap;

    public OwnerController(OwnerServiceMap ownerServiceMap) {
        this.ownerServiceMap = ownerServiceMap;
    }

    @GetMapping("/owners")
  public ResponseEntity<Set<Owner>> findOwners () {
        Set<Owner> owners = ownerServiceMap.findAll();
        return new ResponseEntity<>(owners, HttpStatus.OK);
  }
}
