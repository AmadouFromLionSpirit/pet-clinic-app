package com.petclinicapp.bootstrap;

import com.petclinicapp.model.*;
import com.petclinicapp.services.*;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final OwnerService ownerService;
    private final VetService vetService;
    private final VisitService visitService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, VisitService visitService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.visitService = visitService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }
    @Override
    public void run(String... args) throws Exception {
        logger.info("L'application a démarré");
        PetType dog = new PetType();
        dog.setName("Dog");
        logger.info("Sauvegarde du type d'animal dans la base de données");
        try{
            petTypeService.save(dog);
            logger.info("L'animal du type {} a été sauvegardé dans la base de données", dog.getName());
        } catch (DataIntegrityViolationException e) {
            logger.error("Violation d'intégrité des données lors de la sauvegarde du type d'animal: {}", e.getMessage(), e);
        } catch (ConstraintViolationException e) {
            logger.error("Violation de contrainte lors de la validation de l'entité PetType: {}", e.getMessage(), e);
        } catch (OptimisticLockingFailureException e) {
            logger.error("Erreur de verrouillage optimiste lors de la sauvegarde du type d'animal: {}", e.getMessage(), e);
        } catch (TransactionSystemException e) {
            logger.error("Problème de transaction lors de la sauvegarde du type d'animal: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Une erreur inattendue s'est produite lors de la sauvegarde du type d'animal: {}", e.getMessage(), e);
        }


        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Owner owner1 = new Owner();
       // owner1.setId(1L);
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        ownerService.save(owner1);


        Owner owner2 = new Owner();
      //  owner2.setId(2L);
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");

        Pet fionasCat = new Pet();
        fionasCat.setName("Just Cat");
        fionasCat.setPetType(savedCatPetType);
        fionasCat.setOwner(owner2);
        fionasCat.setBirthDate(LocalDate.now());

        owner2.getPets().add(fionasCat);
        ownerService.save(owner2);
        System.out.println("Loaded Owners ...");

        petService.save(fionasCat);

        Visit catVisit = new Visit();
        catVisit.setPet(fionasCat);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");
        visitService.save(catVisit);

        Vet vet1 = new Vet();
       // vet1.setId(1L);
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vetService.save(vet1);

        Vet vet2 = new Vet();
       // vet2.setId(2L);
        vet2.setFirstName("Sam");
        vet2.setLastName("Sarr");
        vetService.save(vet2);

        System.out.println("Loaded Vets...");

    }
}
