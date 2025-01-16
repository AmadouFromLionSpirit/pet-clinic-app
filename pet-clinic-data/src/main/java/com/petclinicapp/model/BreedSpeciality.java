package com.petclinicapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BreedSpeciality extends BaseEntity {
    private String name;

    @ManyToMany(mappedBy = "breedSpecialities")
    private List<Breed> breeds = new ArrayList<>();

    public BreedSpeciality(String name) {

    }
}
