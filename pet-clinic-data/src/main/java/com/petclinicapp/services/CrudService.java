package com.petclinicapp.services;

import com.petclinicapp.model.PetType;

import java.util.Set;

public interface CrudService<T,ID> {
    Set<T> findAll();
    T findById(ID id);
    T save(T object);
    void deleteById(ID id);

    interface PetTypeService extends CrudService<PetType, Long> {

    }
}
