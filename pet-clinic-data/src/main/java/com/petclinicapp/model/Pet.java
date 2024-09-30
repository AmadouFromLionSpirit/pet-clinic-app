package com.petclinicapp.model;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import java.time.LocalDate;
@Entity
@Getter
@Setter
public class Pet extends BaseEntity {

    private PetType petType;
    private Owner owner;
    private LocalDate birthDate;

}
