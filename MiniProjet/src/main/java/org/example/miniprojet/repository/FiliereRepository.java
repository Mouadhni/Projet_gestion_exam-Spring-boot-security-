package org.example.miniprojet.repository;

import org.example.miniprojet.model.Enseignant;
import org.example.miniprojet.model.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FiliereRepository extends JpaRepository<Filiere,Integer> {
    Optional<Filiere> findFiliereById(Integer integer);

}
