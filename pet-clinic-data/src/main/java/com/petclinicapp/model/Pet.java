package com.petclinicapp.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class Pet extends com.petclinicapp.model.Person {

    private com.petclinicapp.model.PetType petType;
    private com.petclinicapp.model.Owner owner;
    private LocalDate birthDate;

}
