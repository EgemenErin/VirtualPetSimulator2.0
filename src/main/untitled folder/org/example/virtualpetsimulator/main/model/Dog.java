package org.example.virtualpetsimulator.main.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dogs")
@DiscriminatorValue("DOG")
public class Dog extends Pet {

    // Default constructor
    public Dog() {
        super();
    }

    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Woof!");
    }
}
