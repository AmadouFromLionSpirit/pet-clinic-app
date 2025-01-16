package com.petclinicapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Breed extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "geographical_origin_id")
    private GeographicalOrigin geographicalOrigin;

    @ManyToOne
    @JoinColumn(name = "pet_type_id")
    private PetType petType;


    @ManyToMany
    @JoinTable(
            name = "breed_breed_speciality",
            joinColumns = @JoinColumn(name = "breed_id"),
            inverseJoinColumns = @JoinColumn(name = "breed_speciality_id")
    )
    private List<BreedSpeciality> breedSpecialities = new ArrayList<>();
}
