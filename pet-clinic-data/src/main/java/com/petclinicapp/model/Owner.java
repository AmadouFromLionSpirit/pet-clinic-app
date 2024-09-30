package com.petclinicapp.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Owner extends Person {
    private String address;
    private String city;
    private String telephone;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Pet> pets = new HashSet<>();
}
