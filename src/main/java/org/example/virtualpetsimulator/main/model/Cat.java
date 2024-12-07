package org.example.virtualpetsimulator.main.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cats")
@DiscriminatorValue("CAT")
public class Cat extends Pet {

    // Default constructor
    public Cat() {
        super();
    }

    public Cat(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Meow!");
    }
}
