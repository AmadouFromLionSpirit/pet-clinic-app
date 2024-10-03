package com.petclinicapp.repositories;

import com.petclinicapp.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
    PetType findByName(String name);
}
