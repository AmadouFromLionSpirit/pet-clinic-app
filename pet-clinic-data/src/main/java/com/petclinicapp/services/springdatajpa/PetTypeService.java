package com.petclinicapp.services.springdatajpa;

import com.petclinicapp.model.PetType;
import com.petclinicapp.services.CrudService;

public interface PetTypeService extends CrudService<PetType, Long> {
    PetType findByName(String name);
}
