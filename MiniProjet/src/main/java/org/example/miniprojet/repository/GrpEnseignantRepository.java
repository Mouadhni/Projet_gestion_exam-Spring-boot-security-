package org.example.miniprojet.repository;

import org.example.miniprojet.model.Enseignant;
import org.example.miniprojet.model.GrpEnseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrpEnseignantRepository  extends JpaRepository<GrpEnseignant,Integer> {
}
