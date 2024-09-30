package com.petclinicapp.model;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@Getter
@Setter
public class Person extends BaseEntity {

    private String firstName;
    private String lastName;




}
