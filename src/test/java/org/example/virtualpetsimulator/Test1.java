package org.example.virtualpetsimulator;

import dao.GenericDAO;
import org.example.virtualpetsimulator.main.model.Owner;
import org.example.virtualpetsimulator.main.model.Dog;

public class Test1 {
    public static void main(String[] args) {
        GenericDAO<Owner> ownerDAO = new GenericDAO<>(Owner.class);
        GenericDAO<Dog> dogDAO = new GenericDAO<>(Dog.class);

        Owner owner = new Owner("Alice");
        Dog dog = new Dog("Buddy");
        owner.addPet(dog);

        ownerDAO.save(owner);

        System.out.println("Owner and pet saved successfully!");
    }
}
