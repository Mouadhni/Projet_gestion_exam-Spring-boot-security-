package org.example.miniprojet.service;

import org.example.miniprojet.Exeption.CustomException;
import org.example.miniprojet.model.Filiere;
import org.example.miniprojet.model.Module;
import org.example.miniprojet.repository.FiliereRepository;
import org.example.miniprojet.repository.ModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {
    private  final ModuleRepository moduleRepository;
    private  final FiliereRepository filiereRepository;

    public ModuleService(ModuleRepository moduleRepository, FiliereRepository filiereRepository) {
        this.moduleRepository = moduleRepository;
        this.filiereRepository = filiereRepository;
    }

    public ResponseEntity<?> createModule(String nom, String description, int filiereId) {
        try {
            Optional<Filiere> filiereOptional = filiereRepository.findFiliereById(filiereId);

            if (filiereOptional.isPresent() ) {
                Module module=new Module(nom, description, filiereOptional.get());
                moduleRepository.save(module);
                return ResponseEntity.ok(module);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("filiere not found with ID: " + filiereId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> deleteModule(int id) {
        try {
            Optional<Module> moduleOptional = moduleRepository.findById(id);

            if (moduleOptional.isPresent()) {
                Module module = moduleOptional.get();

                // Disassociate the module from any filiere
                module.setFiliere(null);

                // Delete the module
                moduleRepository.delete(module);

                return ResponseEntity.ok("Module with ID " + id + " has been successfully deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Module not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> updateModule(int id, String nom, String description, int filiereId) {
        try {
            Optional<Module> moduleOptional = moduleRepository.findById(id);

            if (moduleOptional.isPresent()) {
                Module module = moduleOptional.get();

                // Update module attributes
                module.setNom(nom);
                module.setDescription(description);

                // Update filiere association if filiereId is provided
                if (filiereId > 0) {
                    Optional<Filiere> filiereOptional = filiereRepository.findFiliereById(filiereId);
                    if (filiereOptional.isPresent()) {
                        module.setFiliere(filiereOptional.get());
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Filiere not found with ID: " + filiereId);
                    }
                }

                // Save the updated module
                moduleRepository.save(module);
                return ResponseEntity.ok(module);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Module not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> getAllModules() {
        try {
            List<Module> modulesList = moduleRepository.findAll();
            if (!modulesList.isEmpty()) {

                return ResponseEntity.ok(modulesList);
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



    public ResponseEntity<?> getModule(int id) {
        try {
            Optional<Module> moduleOptional = moduleRepository.findById(id);
            if (moduleOptional.isPresent()) {
                return ResponseEntity.ok(moduleOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
