package org.example.miniprojet.repository;

import org.example.miniprojet.model.Enseignant;
import org.example.miniprojet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EnseignantRepository extends JpaRepository<Enseignant,Integer> {
    Optional<Enseignant> findEnseignantByUsername(String username);

    @Query("SELECT e FROM Enseignant e WHERE e.id NOT IN (" +
            "SELECT ens.id FROM Salle s JOIN s.surveillant ens JOIN s.examen ex WHERE ex.date = :date" +
            ")")    List<Enseignant> findSurveillantsNotAssignedToSalleOnDate(@Param("date") Date date);
    Optional<Enseignant> findEnseignantById(Integer integer);
}
