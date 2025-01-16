package com.petclinicapp.services.springdatajpa;

import com.petclinicapp.model.GeographicalOrigin;
import com.petclinicapp.repositories.GeographicalOriginRepository;
import com.petclinicapp.services.GeographicalOriginService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
@Profile("springdatajpa")
public class GeographicalOriginSDJpaService implements GeographicalOriginService {

    private final GeographicalOriginRepository geographicalOriginRepository;

    public GeographicalOriginSDJpaService(GeographicalOriginRepository geographicalOriginRepository) {
        this.geographicalOriginRepository = geographicalOriginRepository;
    }

    @Override
    public Set<GeographicalOrigin> findAll() {
        Set<GeographicalOrigin> geographicalOrigins = new HashSet<>();
        geographicalOriginRepository.findAll().forEach(geographicalOrigins::add);
        return geographicalOrigins;
    }

    @Override
    public GeographicalOrigin findById(Long id) {
        Optional<GeographicalOrigin> optionalGeographicalOrigin = geographicalOriginRepository.findById(id);
        return optionalGeographicalOrigin.orElse(null);
    }

    @Override
    public GeographicalOrigin save(GeographicalOrigin geographicalOrigin) {
        return geographicalOriginRepository.save(geographicalOrigin);
    }

    @Override
    public void deleteById(Long id) {
        geographicalOriginRepository.deleteById(id);

    }
}
