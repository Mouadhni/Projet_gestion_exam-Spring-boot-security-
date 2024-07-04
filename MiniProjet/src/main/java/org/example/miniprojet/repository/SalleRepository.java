package org.example.miniprojet.repository;

import org.example.miniprojet.model.Module;
import org.example.miniprojet.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SalleRepository extends JpaRepository<Salle,Integer> {
    @Query("SELECT s FROM Salle s WHERE s.id NOT IN (SELECT salle.id FROM Salle salle JOIN salle.examen e WHERE e.date = :date)")
    List<Salle> findEmptySallesByExamDate(@Param("date") Date date);

}
