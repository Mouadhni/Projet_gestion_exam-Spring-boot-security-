package org.example.miniprojet.service;

import org.example.miniprojet.Exeption.CustomException;
import org.example.miniprojet.model.Enseignant;
import org.example.miniprojet.repository.EnseignantRepository;
import org.example.miniprojet.repository.ExamenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EnseignantService {
    private  final EnseignantRepository enseignantRepository;

    public EnseignantService(EnseignantRepository enseignantRepository) {
        this.enseignantRepository = enseignantRepository;
    }
    public ResponseEntity<?> getAllEnseignants() {
        try {
            List<Enseignant> enseignantsList = enseignantRepository.findAll();
            if (!enseignantsList.isEmpty()) {

                return ResponseEntity.ok(enseignantsList);
            } else {
                throw new CustomException("list est vide il nya aucun user");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getFreeEnseignants(Date date) {
        try {
            List<Enseignant> enseignantsList = enseignantRepository.findSurveillantsNotAssignedToSalleOnDate(date);
            if (!enseignantsList.isEmpty()) {

                return ResponseEntity.ok(enseignantsList);
            } else {
                throw new CustomException("list est vide il nya aucun user");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
