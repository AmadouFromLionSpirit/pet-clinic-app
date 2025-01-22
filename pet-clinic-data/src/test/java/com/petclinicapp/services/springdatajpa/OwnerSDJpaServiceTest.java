package com.petclinicapp.services.springdatajpa;

import com.petclinicapp.model.Owner;
import com.petclinicapp.repositories.OwnerRepository;
import com.petclinicapp.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("springdatajpa")
class OwnerSDJpaServiceTest {
    OwnerService classUnderTest;
    @Mock
    OwnerRepository ownerRepository;
    @BeforeEach
    public void init() {
        classUnderTest = new OwnerSDJpaService(ownerRepository);

    }

    @Test
    void findAll() {
        //ARRANGE
        Set<Owner> mockedOwners = Set.of(
                new Owner("Amadou", "Sarr"),
                new Owner("Mariame", "Diouf"),
                new Owner("Emmanuel", "Macron")
        );

        when(ownerRepository.findAll()).thenReturn(mockedOwners);

        //ACT
        Set<Owner> results = classUnderTest.findAll();

        //ASSERT
        assertThat(results).extracting(Owner::getFirstName,Owner::getLastName)
                .contains(
                        tuple("Amadou","Sarr"),
                        tuple("Mariame","Diouf"),
                        tuple("Emmanuel","Macron")
                );

    }

    @Test
    void findById() {
        // ARRANGE
        Long ownerId = 4L;
        Owner owner = Owner.builder()
                .id(ownerId)
                .firstName("Amadou")
                .lastName("Sarr")
                .build();

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));

        // ACT
        Owner result = classUnderTest.findById(ownerId);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ownerId);
        assertThat(result.getFirstName()).isEqualTo("Amadou");
        assertThat(result.getLastName()).isEqualTo("Sarr");

        // Vérifie que le repository a bien été appelé une seule fois avec l'ID correct
        verify(ownerRepository, times(1)).findById(ownerId);
    }

    @Test
    void save() {
        //Owner

        Owner owner = Owner.builder()
                .firstName("Amadou")
                .lastName("Sarr")
                .build();
        when(ownerRepository.save(owner)).thenReturn(owner);

        //ACT
        Owner result = classUnderTest.save(owner);

        //Assert
        assertThat(result).extracting("firstName","lastName")
                .containsExactly("Amadou","Sarr");
    }

    @Test
    void deleteById() {
    }

    @Test
    void findByLastName() {
    }

    @Test
    void delete() {
        //ARRANGE
        Owner owner = Owner.builder()
                .id(1L)
                .firstName("Amadou")
                .lastName("Sarr")
                .build();

        // Simulation du comportement des mocks
        when(ownerRepository.existsById(owner.getId())).thenReturn(true);
        doNothing().when(ownerRepository).deleteById(owner.getId());

        // ACT
        classUnderTest.delete(owner.getId());

        // ASSERT
        // Vérifiez que deleteById a été appelé
        verify(ownerRepository, times(1)).deleteById(owner.getId());

        // Vérifiez que existsById retourne le comportement attendu
        // Ici, comme il s'agit d'un mock, l'état ne change pas dynamiquement, donc vous pouvez :
        // a) Ajouter une nouvelle simulation
        when(ownerRepository.existsById(owner.getId())).thenReturn(false);
        assertFalse(ownerRepository.existsById(owner.getId()));
    }
}