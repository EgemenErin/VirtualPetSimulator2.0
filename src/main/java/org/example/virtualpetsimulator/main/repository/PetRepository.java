package org.example.virtualpetsimulator.main.repository;

import org.example.virtualpetsimulator.main.model.Pet;
import org.example.virtualpetsimulator.main.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    // Retrieve all pets belonging to a specific owner
    @Query("SELECT p FROM Pet p WHERE p.owner = :owner")
    List<Pet> findByOwner(@Param("owner") Owner owner);
}
