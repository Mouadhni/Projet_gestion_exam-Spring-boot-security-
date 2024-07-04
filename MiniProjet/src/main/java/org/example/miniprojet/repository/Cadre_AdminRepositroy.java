package org.example.miniprojet.repository;

import org.example.miniprojet.model.Cadre_Admin;
import org.example.miniprojet.model.Enseignant;
import org.example.miniprojet.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Cadre_AdminRepositroy extends JpaRepository<Cadre_Admin,Integer> {
    Optional<Cadre_Admin> findCadre_AdminByUsername(String username);

    List<Cadre_Admin> findByListSalle(Salle salle);

}
