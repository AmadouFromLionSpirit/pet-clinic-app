package com.petclinicapp.services;

import com.petclinicapp.model.Owner;

public interface OwnerService extends CrudService<Owner,Long>{
   Owner findByLastName(String lastName);
}
