package com.petclinicapp.model;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;


@Entity
@Getter
@Setter
public class Person extends BaseEntity {

    private Long id;
    private String firstName;
    private String lastName;




}
