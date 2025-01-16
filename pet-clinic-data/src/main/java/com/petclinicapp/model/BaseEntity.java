package com.petclinicapp.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
@MappedSuperclass
@Getter
@Setter
@SuperBuilder
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public BaseEntity() {
        // Constructeur par défaut nécessaire pour JPA
    }

}
