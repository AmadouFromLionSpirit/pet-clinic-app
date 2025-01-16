package com.petclinicapp.model;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@Getter
@Setter
@SuperBuilder
public class Person extends BaseEntity {

    private String firstName;
    private String lastName;

    public Person() {

    }
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
