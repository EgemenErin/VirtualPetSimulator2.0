package org.example.virtualpetsimulator.main.repository;

import org.example.virtualpetsimulator.main.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByNameAndPassword(String name, String password);
}
