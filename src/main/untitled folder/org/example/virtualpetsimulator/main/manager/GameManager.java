package org.example.virtualpetsimulator.main.manager;

import org.example.virtualpetsimulator.main.model.Owner;
import org.example.virtualpetsimulator.main.model.Pet;
import org.example.virtualpetsimulator.main.model.Dog;
import org.example.virtualpetsimulator.main.model.Cat;
import org.example.virtualpetsimulator.main.model.Dragon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.virtualpetsimulator.main.repository.OwnerRepository;
import org.example.virtualpetsimulator.main.repository.PetRepository;

import java.util.List;
import java.util.Scanner;

@Service
public class GameManager {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private Owner currentOwner;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public GameManager(OwnerRepository ownerRepository, PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    public void register(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (ownerRepository.findByNameAndPassword(name, password) != null) {
            System.out.println("An owner with this name already exists. Please try again.");
            return;
        }

        Owner newOwner = new Owner(name, password);
        ownerRepository.save(newOwner);
        System.out.println("Registration successful. You can now log in.");
    }

    public void switchOwner(Scanner scanner) {
        System.out.println("Logging out...");
        login(scanner); // Log in as a different owner
    }



    public void login(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Owner owner = ownerRepository.findByNameAndPassword(name, password);

        if (owner == null) {
            System.out.println("Invalid name or password. Please try again.");
            login(scanner); // Retry login
        } else {
            currentOwner = owner;
            System.out.println("Welcome back, " + currentOwner.getName() + "!");
        }
    }



    public void registerOwner(Scanner scanner) {
        System.out.println("=== Register New Owner ===");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Check if the owner already exists
        if (ownerRepository.findByNameAndPassword(name, password) != null) {
            System.out.println("An owner with this name already exists. Please try again.");
            return;
        }

        // Create and save the new owner
        currentOwner = new Owner(name, password);
        ownerRepository.save(currentOwner);

        System.out.println("Registration successful! Welcome, " + name + "!");
    }

    public void adoptPet(Scanner scanner) {
        System.out.println("Choose a pet to adopt:");
        System.out.println("1. Dog");
        System.out.println("2. Cat");
        System.out.println("3. Dragon");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        System.out.print("Enter a name for your pet: ");
        String petName = scanner.nextLine();

        Pet pet;
        switch (choice) {
            case "1":
                pet = new Dog(petName);
                break;
            case "2":
                pet = new Cat(petName);
                break;
            case "3":
                pet = new Dragon(petName);
                break;
            default:
                System.out.println("Invalid choice. Adoption canceled.");
                return;
        }

        pet.setOwner(currentOwner);
        petRepository.save(pet);

        System.out.println("Congratulations! You have adopted a new pet named " + petName + ".");
    }

    public void feedPet() {
        Pet pet = selectPet();
        if (pet != null) {
            pet.feed();
            petRepository.save(pet);
        }
    }

    public void playWithPet() {
        Pet pet = selectPet();
        if (pet != null) {
            pet.play();
            petRepository.save(pet);
        }
    }

    public void checkPetStatus() {
        Pet pet = selectPet();
        if (pet != null) {
            pet.checkStatus();
        }
    }

    public void listPets() {
        List<Pet> pets = petRepository.findByOwner(currentOwner);
        if (pets.isEmpty()) {
            System.out.println("You have no pets.");
        } else {
            System.out.println("Your pets:");
            for (Pet pet : pets) {
                System.out.println("- " + pet.getName() + " (" + pet.getClass().getSimpleName() + ")");
            }
        }
    }

    public void updatePetName(Scanner scanner) {
        Pet pet = selectPet();
        if (pet != null) {
            System.out.print("Enter new name for the pet: ");
            String newName = scanner.nextLine();
            pet.setName(newName);
            petRepository.save(pet);
            System.out.println("Pet name updated successfully.");
        }
    }

    public void deletePet(Scanner scanner) {
        List<Pet> pets = petRepository.findByOwner(currentOwner);

        if (pets.isEmpty()) {
            System.out.println("You have no pets to delete.");
            return;
        }

        System.out.println("Select a pet to delete:");
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + ". " + pets.get(i).getName());
        }
        System.out.print("Enter your choice: ");
        String input = scanner.nextLine();

        try {
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > pets.size()) {
                System.out.println("Invalid choice.");
                return;
            }

            Pet petToDelete = pets.get(choice - 1);
            petRepository.delete(petToDelete); // Delete from the database
            System.out.println("Pet " + petToDelete.getName() + " has been deleted.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }


    public void saveGame() {
        System.out.println("Saving game for owner: " + currentOwner.getName());
        ownerRepository.save(currentOwner); // Save the current owner and their pets
        System.out.println("Game saved successfully.");
    }


    public void loadGame() {
        System.out.println("Loading game for owner: " + currentOwner.getName());
        List<Pet> pets = petRepository.findByOwner(currentOwner);

        if (pets.isEmpty()) {
            System.out.println("No pets found for this owner.");
        } else {
            System.out.println("Game loaded successfully. Your pets:");
            for (Pet pet : pets) {
                System.out.println("- " + pet.getName());
            }
        }
    }

    private Pet selectPet() {
        List<Pet> pets = petRepository.findByOwner(currentOwner);
        if (pets.isEmpty()) {
            System.out.println("You have no pets.");
            return null;
        }

        System.out.println("Select a pet:");
        for (int i = 0; i < pets.size(); i++) {
            Pet pet = pets.get(i);
            System.out.println((i + 1) + ". " + pet.getName() + " (" + pet.getClass().getSimpleName() + ")");
        }
        System.out.print("Enter your choice: ");
        String input = scanner.nextLine();

        try {
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > pets.size()) {
                System.out.println("Invalid choice.");
                return null;
            }
            return pets.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return null;
        }
    }
}
