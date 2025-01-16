package com.petclinicapp.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PetType extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "petType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Breed> breeds = new ArrayList<>();
    public PetType(String name) {
        this.name = name;
    }


}
