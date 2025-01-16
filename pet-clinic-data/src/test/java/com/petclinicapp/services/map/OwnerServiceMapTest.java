package com.petclinicapp.services.map;

import com.petclinicapp.model.Owner;
import com.petclinicapp.model.Pet;
import com.petclinicapp.model.PetType;
import com.petclinicapp.services.PetService;
import com.petclinicapp.services.PetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("default")
@Slf4j
class OwnerServiceMapTest {



    OwnerServiceMap classUnderTest;


    @Mock
    PetTypeService petTypeService;

    @Mock
    PetService petService;

    @BeforeEach
    public void init() {
        classUnderTest = new OwnerServiceMap(petTypeService,petService);

    }

    @Test
    void findAll_Owners_returnsFirstNameAndLastNameOfEachOwner() throws NoSuchFieldException, IllegalAccessException {
        //Arrange
        Set<Owner> testOwners = Set.of(
                new Owner("Amadou", "Sarr"),
                new Owner("Mariame", "Diouf"),
                new Owner("Emmanuel", "Macron")
        );

        Map<Long, Owner> ownersMap = new HashMap<>();
        long id = 1L;
        for(Owner owner : testOwners) {
            owner.setId(id++);
            ownersMap.put(owner.getId(), owner);
        }

        Field ownersField = AbstractMapService.class.getDeclaredField("map");
        ownersField.setAccessible(true);
        ownersField.set(classUnderTest, ownersMap);

       //Act
        Set<Owner> result = classUnderTest.findAll();

        List<String> fullNames = result.stream()
                .map(owner -> owner.getFirstName() + " " + owner.getLastName())
                .collect(Collectors.toList());

        // Assert
        assertThat(fullNames)
                .containsExactlyInAnyOrder("Amadou Sarr", "Mariame Diouf", "Emmanuel Macron");
    }



    @Test
    void deleteById() {
        //ARRANGE
        PetType horse = PetType.builder()
                .id(1L)
                .name("horse")
                .build();
        Pet ragnar = Pet.builder()
                .id(1L)
                .name("Ragnar")
                .petType(horse)
                .build();

        Owner owner = Owner.builder()
                .firstName("Catherine")
                .lastName("Briand")
                .pets(Set.of(ragnar))
                .build();

        Set<Owner> testOwners = Set.of(
                new Owner("Amadou", "Sarr"),
                new Owner("Mariame", "Diouf"),
                new Owner("Emmanuel", "Macron"),
                owner
        );

        Map<Long, Owner> ownersMap = new HashMap<>();
        long id = 1L;
        for(Owner owner1 : testOwners) {
            Owner ownerCopy = new Owner(owner1.getFirstName(), owner1.getLastName());  // Crée une nouvelle instance
            ownerCopy.setId(id++);
            ownersMap.put(ownerCopy.getId(), ownerCopy);  // Ajoute la nouvelle instance
        }


        Field ownersField = null;
        try {
            ownersField = AbstractMapService.class.getDeclaredField("map");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        ownersField.setAccessible(true);
        try {
            ownersField.set(classUnderTest, ownersMap);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        log.info("Avant la suppression de l'owner ");
        for (Map.Entry<Long, Owner> entry : ownersMap.entrySet()) {
            log.info("Key: " + entry.getKey() + ", First name: " + entry.getValue().getFirstName() + ", Last name: "+ entry.getValue().getLastName());
        }

        //ACT
         classUnderTest.deleteById(4L);
        log.info("Après la suppression de l'owner ");
        for (Map.Entry<Long, Owner> entry : ownersMap.entrySet()) {
            log.info("Key: " + entry.getKey() + ", First name: " + entry.getValue().getFirstName() + ", Last name: "+ entry.getValue().getLastName());
        }

        //ASSERT
        assertThat(ownersMap).hasSize(3);
    }

    @Test
    void givenAPet_whenCreateOwner_thenReturnOwnerDetailsWithPetOwned() {
        //ARRANGE

        PetType dog = PetType.builder()
                .id(1L)
                .name("dog")
                .build();
       Pet pet1 = Pet.builder()
               .id(1L)
               .name("Lowe")
               .petType(dog)
               .build();

       Owner owner1 = Owner.builder()
               .firstName("Amadou")
               .lastName("Sarr")
               .build();

       owner1.getPets().add(pet1);

        //ACT
        Owner result = classUnderTest.save(owner1);

        //ASSERT
     //   assertThat(result).isEqualTo(owner1);
        assertThat(result)
                .extracting(Owner::getFirstName,Owner::getLastName,
                        owner -> owner.getPets().iterator().next().getName(),
                        owner -> owner.getPets().iterator().next().getPetType().getName()
                )
                .containsExactly("Amadou","Sarr","Lowe","dog");


    }
    @Test
    void givenManyDifferentPetTypes_whenCreateOwner_thenReturnOwnerDetailsWithPetsOwned() {
        //ARRANGE
        PetType horse = PetType.builder()
                .id(1L)
                .name("horse")
                .build();


        PetType cat = PetType.builder()
                .id(2L)
                .name("cat")
                .build();

        Pet phoenix = Pet.builder()
                .id(1L)
                .name("Phoenix")
                .petType(horse)
                .build();
        Pet phenomene = Pet.builder()
                .id(2L)
                .name("Phénomène")
                .petType(cat)
                .build();


        Owner owner = Owner.builder()
                .firstName("Daisy")
                .lastName("Cavaleri")
                .pets(Set.of(phoenix,phenomene))
                .build();

        //ACT
        Owner result = classUnderTest.save(owner);

        //ASSERT
        assertThat(result).isNotNull()
                .extracting("firstName", "lastName", "pets")
                .containsExactly("Daisy", "Cavaleri", Set.of(phoenix, phenomene));

        // Assert individual pet details
        assertThat(result.getPets())
                .extracting(Pet::getName, pet -> pet.getPetType().getName())
                .contains(
                        tuple("Phoenix", "horse"),
                        tuple("Phénomène", "cat")
                );

    }


    @Test
    @Disabled
    void givenManySamePetTypesButDifferentBreed_whenCreateOwner_thenReturnOwnerDetailsWithPetsOwned() {
      /*  // Création des origines géographiques
        GeographicalOrigin netherlands = new GeographicalOrigin("Pays-Bas");
        GeographicalOrigin arabianPeninsula = new GeographicalOrigin("Péninsule Arabique");
        GeographicalOrigin spain = new GeographicalOrigin("Espagne");

        // Création des spécialisations
        BreedSpeciality dressage = new BreedSpeciality("Dressage");
        BreedSpeciality attelage = new BreedSpeciality("Attelage");
        BreedSpeciality endurance = new BreedSpeciality("Endurance");
        BreedSpeciality course = new BreedSpeciality("Course");
        BreedSpeciality parade = new BreedSpeciality("Parade");
        BreedSpeciality spectacle = new BreedSpeciality("Spectacle");
        BreedSpeciality dressageClassique = new BreedSpeciality("Dressage classique");


        // Création du type d'animal "horse"
        PetType horseType = PetType.builder()
                .id(1L)
                .name("horse")
                .breeds(List.of(frison, arabe, andalous))  // Assurez-vous que les races sont correctement liées ici
                .build();


        // Création des races associées au type "horse" avec leurs spécialisations
        Breed frison = Breed.builder().id(1L).name("Frison").petType(horseType)
                .breedSpecialities(List.of(dressage, spectacle, attelage))
                .build();
        Breed arabe = Breed.builder()
                .id(2L) // Remplacer par un ID unique
                .name("Arabe")
                .petType(horseType)
                .breedSpecialities(List.of(endurance, course, parade))
                .build();

        Breed andalous = Breed.builder()
                .id(3L) // Remplacer par un ID unique
                .name("Andalous")
                .petType(horseType)
                .breedSpecialities(List.of(dressageClassique))
                .build();


        // Ajout des races au type d'animal
        horseType.setBreeds(List.of(frison, arabe, andalous));

        // Simuler la lecture de données et créer les animaux
        Set<Pet> pets = Stream.of(
                Pet.builder().id(1L).name("Zeus").petType(horseType).build(),
                Pet.builder().id(2L).name("Athena").petType(horseType).build(),
                Pet.builder().id(3L).name("Hera").petType(horseType).build(),
                Pet.builder().id(4L).name("Achilles").petType(horseType).build(),
                Pet.builder().id(5L).name("Cairo").petType(horseType).build(),
                Pet.builder().id(6L).name("Sultan").petType(horseType).build(),
                Pet.builder().id(7L).name("Jasmine").petType(horseType).build(),
                Pet.builder().id(8L).name("Zara").petType(horseType).build(),
                Pet.builder().id(9L).name("Nadir").petType(horseType).build(),
                Pet.builder().id(10L).name("Balthazar").petType(horseType).build()
        ).collect(Collectors.toSet());

        // Création du propriétaire Catherine Briand et ajout des animaux
        Owner owner = Owner.builder()
                .firstName("Catherine")
                .lastName("Briand")
                .pets(pets)
                .build();

        Owner result = classUnderTest.save(owner);

        // Vérification des détails du propriétaire et des chevaux avec assertThat
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Catherine");
        assertThat(result.getLastName()).isEqualTo("Briand");
        assertThat(result.getPets()).hasSize(10);  // Vérifier qu'il y a bien 10 chevaux

        // Vérifier que tous les chevaux sont bien associés au propriétaire et aux bonnes spécialités
        result.getPets().forEach(pet -> {
            assertThat(pet.getName()).isNotNull();
            assertThat(pet.getPetType().getName()).isEqualTo("horse");  // Tous les chevaux doivent être du type "horse"

            // Vérification des spécialisations via la race associée à chaque cheval
            PetType petType = pet.getPetType();
            assertThat(petType).isNotNull();


            // On vérifie que les races sont bien associées au bon PetType
            if (petType.getBreeds().stream().anyMatch(breed -> breed.getName().equals("Frison"))) {
                assertThat(petType.getBreeds().stream()
                        .filter(b -> b.getName().equals("Frison"))
                        .findFirst().get().getBreedSpecialities())
                        .extracting(BreedSpeciality::getName)
                        .containsExactlyInAnyOrder("Dressage", "Spectacle", "Attelage");
            } else if (petType.getBreeds().stream().anyMatch(breed -> breed.getName().equals("Arabe"))) {
                assertThat(petType.getBreeds().stream()
                        .filter(b -> b.getName().equals("Arabe"))
                        .findFirst().get().getBreedSpecialities())
                        .extracting(BreedSpeciality::getName)
                        .containsExactlyInAnyOrder("Endurance", "Course", "Parade");
            } else if (petType.getBreeds().stream().anyMatch(breed -> breed.getName().equals("Andalous"))) {
                assertThat(petType.getBreeds().stream()
                        .filter(b -> b.getName().equals("Andalous"))
                        .findFirst().get().getBreedSpecialities())
                        .extracting(BreedSpeciality::getName)
                        .containsExactly("Dressage classique");
            }
        });*/
    }



    @Test
    void findById() throws NoSuchFieldException, IllegalAccessException {
        // ARRANGE
        PetType horse = PetType.builder()
                .id(1L)
                .name("horse")
                .build();
        Pet ragnar = Pet.builder()
                .id(1L)
                .name("Ragnar")
                .petType(horse)
                .build();

        Owner owner = Owner.builder()
                .firstName("Catherine")
                .lastName("Briand")
                .pets(Set.of(ragnar))
                .build();

        LinkedHashSet<Owner> testOwners = new LinkedHashSet<>();  // Utilise LinkedHashSet pour préserver l'ordre d'insertion
        testOwners.add(new Owner("Amadou", "Sarr"));
        testOwners.add(new Owner("Mariame", "Diouf"));
        testOwners.add(new Owner("Emmanuel", "Macron"));
        testOwners.add(owner);

        Map<Long, Owner> ownersMap = new HashMap<>();
        long id = 1L;
        for (Owner owner1 : testOwners) {
            Owner ownerCopy = new Owner(owner1.getFirstName(), owner1.getLastName());  // Crée une nouvelle instance
            ownerCopy.setId(id++);
            ownersMap.put(ownerCopy.getId(), ownerCopy);  // Ajoute la nouvelle instance
        }

// Log l'état initial de la Map
        Field ownersField = AbstractMapService.class.getDeclaredField("map");
        ownersField.setAccessible(true);
        ownersField.set(classUnderTest, ownersMap);
        for (Map.Entry<Long, Owner> entry : ownersMap.entrySet()) {
            log.info("Key: " + entry.getKey() + ", First name: " + entry.getValue().getFirstName() + ", Last name: " + entry.getValue().getLastName());
        }

// ACT
        Owner result = classUnderTest.findById(4L);
        // ASSERT
        assertThat(result.getFirstName()).isEqualTo("Catherine");
        assertThat(result.getLastName()).isEqualTo("Briand");
    }


   @Test
    void findByLastName() throws NoSuchFieldException, IllegalAccessException {
        //ARRANGE
        PetType horse = PetType.builder()
                .id(1L)
                .name("horse")
                .build();
        Pet ragnar = Pet.builder()
                .id(1L)
                .name("Ragnar")
                .petType(horse)
                .build();

        Owner owner = Owner.builder()
                .firstName("Catherine")
                .lastName("Briand")
                .pets(Set.of(ragnar))
                .build();

        Set<Owner> testOwners = Set.of(
                new Owner("Amadou", "Sarr"),
                new Owner("Mariame", "Diouf"),
                new Owner("Emmanuel", "Macron"),
                owner
        );

        Map<Long, Owner> ownersMap = new HashMap<>();
        long id = 1L;
        for(Owner owner1 : testOwners) {
            Owner ownerCopy = new Owner(owner1.getFirstName(), owner1.getLastName());  // Crée une nouvelle instance
            ownerCopy.setId(id++);
            ownersMap.put(ownerCopy.getId(), ownerCopy);  // Ajoute la nouvelle instance
        }


        Field ownersField = AbstractMapService.class.getDeclaredField("map");
        ownersField.setAccessible(true);
        ownersField.set(classUnderTest, ownersMap);

        //ACT
      Owner result =  classUnderTest.findByLastName(owner.getLastName());

        //ASSERT
        assertThat(result)
                .extracting(Owner::getFirstName, Owner::getLastName)
                .containsExactly("Catherine", "Briand");



    }
}