package com.petclinicapp.services.springdatajpa;

import com.petclinicapp.model.Breed;
import com.petclinicapp.repositories.BreedRepository;
import com.petclinicapp.services.BreedService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
@Profile("springdatajpa")
public class BreedSDJpaService implements BreedService {

    private final BreedRepository breedRepository;

    public BreedSDJpaService(BreedRepository breedRepository) {
        this.breedRepository = breedRepository;
    }

    @Override
    public Set<Breed> findAll() {
        Set<Breed> breeds = new HashSet<>();
        breedRepository.findAll().forEach(breeds::add);
        return breeds;
    }

    @Override
    public Breed findById(Long id) {
        Optional<Breed> optionalBreed = breedRepository.findById(id);
        return optionalBreed.orElse(null);
    }

    @Override
    public Breed save(Breed breed) {

        return breedRepository.save(breed);
    }

    @Override
    public void deleteById(Long id) {
        breedRepository.deleteById(id);

    }
}
