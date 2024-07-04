package org.example.miniprojet.service;

import org.example.miniprojet.Exeption.CustomException;
import org.example.miniprojet.model.Departement;
import org.example.miniprojet.model.Enseignant;
import org.example.miniprojet.repository.DepartementRepository;
import org.example.miniprojet.repository.EnseignantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartemementService {
    private  final DepartementRepository departementRepository;
    private  final EnseignantRepository enseignantRepository;


    public DepartemementService(DepartementRepository departementRepository, EnseignantRepository enseignantRepository) {
        this.departementRepository = departementRepository;
        this.enseignantRepository = enseignantRepository;
    }


    public ResponseEntity<?> createDepartement(String nom, String description, int enseignantId) {
        try {
            Enseignant enseignant = null;

            if (enseignantId != 0) {
                Optional<Enseignant> enseignantOptional = enseignantRepository.findById(enseignantId);
                if (!enseignantOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Enseignant not found with ID: " + enseignantId);
                }
                enseignant = enseignantOptional.get();
            }

            // Create and save the new Departement entity
            Departement departement = new Departement(nom, description, enseignant);
            departementRepository.save(departement);

            return ResponseEntity.ok(departement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> deleteDepatement(int id) {
        try {
            Optional<Departement> departementOptional = departementRepository.findById(id);
            if (!departementOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Departement not found with ID: " + id);
            }

            departementRepository.deleteById(id);

            return ResponseEntity.ok("Departement deleted successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> updateDepatement(int id, String nom, String description, int enseignantId) {
        try {
            Optional<Departement> departementOptional = departementRepository.findById(id);
            if (!departementOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Departement not found with ID: " + id);
            }

            Departement departement = departementOptional.get();
            if (nom != null) {
                departement.setNom(nom);
            }
            if (description != null) {
                departement.setDescription(description);
            }

            if (enseignantId > 0) {
                Optional<Enseignant> enseignantOptional = enseignantRepository.findById(enseignantId);
                if (enseignantOptional.isPresent()) {
                    departement.setChefDepartement(enseignantOptional.get());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Enseignant not found with ID: " + enseignantId);
                }
            } else {
                departement.setChefDepartement(null);
            }

            // Save the updated department
            departementRepository.save(departement);
            return ResponseEntity.ok(departement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> getAllDepartements() {
        try {
            List<Departement> departementsList = departementRepository.findAll();
            if (!departementsList.isEmpty()) {

                return ResponseEntity.ok(departementsList);
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
