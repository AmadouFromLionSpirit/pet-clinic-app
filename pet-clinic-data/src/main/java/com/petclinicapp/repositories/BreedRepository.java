package com.petclinicapp.repositories;

import com.petclinicapp.model.Breed;
import org.springframework.data.repository.CrudRepository;

public interface BreedRepository extends CrudRepository<Breed, Long> {
}
