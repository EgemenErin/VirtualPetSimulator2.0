package org.example.virtualpetsimulator.main.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dragons")
@DiscriminatorValue("DRAGON")
public class Dragon extends Pet {

    // Default constructor
    public Dragon() {
        super();
    }

    public Dragon(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Rawr!");
    }
}
