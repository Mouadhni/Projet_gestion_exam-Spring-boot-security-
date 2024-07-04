package org.example.miniprojet.repository;

import org.example.miniprojet.model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement,Integer> {

    Optional<Departement> findDepartementById(Integer integer);
}
