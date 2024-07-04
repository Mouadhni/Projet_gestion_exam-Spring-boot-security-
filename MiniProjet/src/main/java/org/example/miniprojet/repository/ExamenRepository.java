package org.example.miniprojet.repository;

import org.example.miniprojet.model.Enseignant;
import org.example.miniprojet.model.Examen;
import org.example.miniprojet.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamenRepository  extends JpaRepository<Examen,Integer> {

}
