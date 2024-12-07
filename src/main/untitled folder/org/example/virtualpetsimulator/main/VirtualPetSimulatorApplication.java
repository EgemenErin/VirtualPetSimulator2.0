package org.example.virtualpetsimulator.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.Scanner;
import org.example.virtualpetsimulator.main.manager.GameManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.example.virtualpetsimulator.main.model")
@EnableJpaRepositories(basePackages = "org.example.virtualpetsimulator.main.repository")
public class VirtualPetSimulatorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VirtualPetSimulatorApplication.class, args);

        GameManager gameManager = context.getBean(GameManager.class);
        Scanner scanner = new Scanner(System.in);

        boolean authenticated = false;

        while (!authenticated) {
            System.out.println("=== Virtual Pet Simulator ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    gameManager.registerOwner(scanner);
                    authenticated = true;
                    break;
                case "2":
                    gameManager.login(scanner);
                    authenticated = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Virtual Pet Simulator ===");
            System.out.println("1. Adopt a Pet");
            System.out.println("2. Feed Pet");
            System.out.println("3. Play with Pet");
            System.out.println("4. Check Pet Status");
            System.out.println("5. Save Game");
            System.out.println("6. Load Game");
            System.out.println("7. List Pets");
            System.out.println("8. Update Pet Name");
            System.out.println("9. Delete Pet");
            System.out.println("10. Switch Owner");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    gameManager.adoptPet(scanner);
                    break;
                case "2":
                    gameManager.feedPet();
                    break;
                case "3":
                    gameManager.playWithPet();
                    break;
                case "4":
                    gameManager.checkPetStatus();
                    break;
                case "5":
                    gameManager.saveGame();
                    break;
                case "6":
                    gameManager.loadGame();
                    break;
                case "7":
                    gameManager.listPets();
                    break;
                case "8":
                    gameManager.updatePetName(scanner);
                    break;
                case "9":
                    gameManager.deletePet(scanner);
                    break;
                case "10":
                    gameManager.login(scanner); // Log in as a different owner
                    break;
                case "11":
                    exit = true;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
        context.close();
    }
}