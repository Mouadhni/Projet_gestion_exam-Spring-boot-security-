package org.example.miniprojet.service;

import org.example.miniprojet.Exeption.CustomException;
import org.example.miniprojet.model.Departement;
import org.example.miniprojet.model.Enseignant;
import org.example.miniprojet.model.Filiere;
import org.example.miniprojet.model.Module;
import org.example.miniprojet.repository.DepartementRepository;
import org.example.miniprojet.repository.EnseignantRepository;
import org.example.miniprojet.repository.FiliereRepository;
import org.example.miniprojet.repository.ModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FiliereService {
    private  final FiliereRepository filiereRepository;
    private  final ModuleRepository moduleRepository;
    private  final DepartementRepository departementRepository;
    private  final EnseignantRepository enseignantRepository;

    public FiliereService(FiliereRepository filiereRepository, ModuleRepository moduleRepository, DepartementRepository departementRepository, EnseignantRepository enseignantRepository) {
        this.filiereRepository = filiereRepository;
        this.moduleRepository = moduleRepository;
        this.departementRepository = departementRepository;
        this.enseignantRepository = enseignantRepository;
    }
    public ResponseEntity<?> createFiliere(String nom, int coordonnateurId, int departementId) {
        try {
            Optional<Enseignant> enseignantOptional = enseignantRepository.findEnseignantById(coordonnateurId);
            Optional<Departement> departementOptional = departementRepository.findDepartementById(departementId);

            if (enseignantOptional.isPresent() && departementOptional.isPresent()) {
                Filiere filiere = new Filiere(nom, enseignantOptional.get(), departementOptional.get());
                filiereRepository.save(filiere);
                return ResponseEntity.ok(filiere);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Enseignant or departement not found with ID: " + coordonnateurId +"   "+departementId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> deleteFiliere(int id) {
        try {
            Optional<Filiere> filiereOptional = filiereRepository.findById(id);
            if (filiereOptional.isPresent()) {
                Filiere filiere = filiereOptional.get();

                for (Module module : filiere.getListModule()) {
                    module.setFiliere(null);
                    moduleRepository.save(module);
                }

                // Now delete the filiere
                filiereRepository.delete(filiere);

                return ResponseEntity.ok("Filiere with ID " + filiere.getId() + " and name '" + filiere.getNom() + "' has been successfully deleted");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Filiere not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> updateFiliere(int id,String nom, int coordonnateurId, int departementId) {
        try {
            Optional<Filiere> filiereOptional = filiereRepository.findById(id);
            if (filiereOptional.isPresent()) {
                Filiere filiere = filiereOptional.get();

                if (nom != null) {
                    filiere.setNom(nom);
                }
                if (coordonnateurId > 0) {
                    Optional<Enseignant> enseignantOptional = enseignantRepository.findEnseignantById(coordonnateurId);
                    enseignantOptional.ifPresent(filiere::setCoordonnateur);
                }

                if (departementId > 0) {
                    Optional<Departement> departementOptional = departementRepository.findById(departementId);
                    departementOptional.ifPresent(filiere::setDepartement);
                }

                filiere.setListModule(null);
                filiereRepository.save(filiere);

                return ResponseEntity.ok(filiere);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("filiere not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> getAllFilieres() {
        try {
            List<Filiere> filieresList = filiereRepository.findAll();
            if (!filieresList.isEmpty()) {

                return ResponseEntity.ok(filieresList);
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
