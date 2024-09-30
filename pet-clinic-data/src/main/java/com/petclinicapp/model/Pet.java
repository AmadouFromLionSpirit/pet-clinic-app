package com.petclinicapp.model;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Getter
@Setter
public class Pet extends BaseEntity {

   @ManyToOne
   @JoinColumn(name = "type_id")
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
    private LocalDate birthDate;

}
