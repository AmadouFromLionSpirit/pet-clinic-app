package com.petclinicapp.model;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {
    public static void main(String[] args) {
        // Créer une instance d'Owner
        Owner owner = new Owner("Amadou", "Sarr");

        // Tester l'accès aux méthodes de Person
        System.out.println(owner.getFirstName()); // Devrait imprimer "Amadou"
        System.out.println(owner.getLastName());  // Devrait imprimer "Sarr"
    }

}