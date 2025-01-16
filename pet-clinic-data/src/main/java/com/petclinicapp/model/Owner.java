package com.petclinicapp.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.SuperBuilder;


@Entity
@AllArgsConstructor
@SuperBuilder
public class Owner extends Person {
    private String address;
    private String city;
    private String telephone;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default // Cela initialise pets à un HashSet vide par défaut
    private Set<Pet> pets = new HashSet<>();
    public Owner() {
        super();
        this.pets  = new HashSet<>();

    }
    public Owner(String firstName, String lastName) {
        super(firstName,lastName);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }


}
