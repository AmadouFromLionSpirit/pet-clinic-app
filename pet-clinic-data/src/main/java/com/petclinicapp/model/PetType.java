package com.petclinicapp.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;


@Entity
@Getter
@Setter
public class PetType extends BaseEntity {
    private String name;
}
