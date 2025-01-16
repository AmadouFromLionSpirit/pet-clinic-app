package com.petclinicapp.services.springdatajpa;

import com.petclinicapp.model.BreedSpeciality;
import com.petclinicapp.repositories.BreedSpecialityRepository;
import com.petclinicapp.services.BreedSpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
@Profile("springdatajpa")
public class BreedSpecialitySDJpaService implements BreedSpecialityService {

    private final BreedSpecialityRepository breedSpecialityRepository;

    public BreedSpecialitySDJpaService(BreedSpecialityRepository breedSpecialityRepository) {
        this.breedSpecialityRepository = breedSpecialityRepository;
    }

    @Override
    public Set<BreedSpeciality> findAll() {
        Set<BreedSpeciality> breedSpecialities = new HashSet<>();
        breedSpecialityRepository.findAll().forEach(breedSpecialities::add);
        return breedSpecialities;
    }

    @Override
    public BreedSpeciality findById(Long id) {
        Optional<BreedSpeciality> optionalBreedSpeciality = breedSpecialityRepository.findById(id);

        return optionalBreedSpeciality.orElse(null);
    }

    @Override
    public BreedSpeciality save(BreedSpeciality breedSpeciality) {

        return breedSpecialityRepository.save(breedSpeciality);
    }

    @Override
    public void deleteById(Long id) {
        breedSpecialityRepository.deleteById(id);

    }
}
