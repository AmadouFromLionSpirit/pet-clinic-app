package com.petclinicapp.services.map;

import com.petclinicapp.model.Pet;
import com.petclinicapp.services.CrudService;

import java.util.Set;

public class PetServiceMap extends AbstractMapService<Pet,Long> implements CrudService<Pet,Long> {
    @Override
    public Set<Pet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);

    }

    @Override
    public Pet save(Pet pet) {
        return super.save(pet.getId(),pet);
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }
}
