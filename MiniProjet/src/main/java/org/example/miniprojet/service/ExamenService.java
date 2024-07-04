package org.example.miniprojet.service;

import org.example.miniprojet.Exeption.CustomException;
import org.example.miniprojet.model.*;
import org.example.miniprojet.model.Module;
import org.example.miniprojet.repository.EnseignantRepository;
import org.example.miniprojet.repository.ExamenRepository;
import org.example.miniprojet.repository.ModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExamenService {
    private  final ExamenRepository examenRepository;
    private  final ModuleRepository moduleRepository;
    private  final EnseignantRepository enseignantRepository;


    public ExamenService(ExamenRepository examenRepository, ModuleRepository moduleRepository, EnseignantRepository enseignantRepository) {
        this.examenRepository = examenRepository;
        this.moduleRepository = moduleRepository;
        this.enseignantRepository = enseignantRepository;
    }
    public ResponseEntity<?> getAllExamens() {
        try {
            List<Examen> examenList = examenRepository.findAll();
            if (!examenList.isEmpty()) {

                return ResponseEntity.ok(examenList);
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
    public ResponseEntity<?> createExamen(String nom, Semestre semestre, Session sesion, Type_Examen type, Date date, double heur, double durePrevu, double dureReel, String anneUniversitaire, int coordonnateurId, int moduleId, byte[] epreuve) {
        try {
            Enseignant coordonnateur = null;
            org.example.miniprojet.model.Module module = null;

            if (coordonnateurId != 0) {
                Optional<Enseignant> coordonnateurOptional = enseignantRepository.findById(coordonnateurId);
                if (!coordonnateurOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Coordonnateur not found with ID: " + coordonnateurId);
                }
                coordonnateur = coordonnateurOptional.get();
            }


            if (moduleId != 0) {
                Optional<Module> moduleOptional = moduleRepository.findById(moduleId);
                if (!moduleOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Module not found with ID: " + moduleId);
                }
                module = moduleOptional.get();
            }

            Examen examen = new Examen(nom, semestre, sesion, type, date, heur, durePrevu, dureReel, anneUniversitaire, coordonnateur, module);
            examen.setEpreuve(epreuve);
            examenRepository.save(examen);

            return ResponseEntity.ok(examen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> UpdateExamen(int id, String nom, Semestre semestre, Session sesion, Type_Examen type, Date date, double heur, double durePrevu, double dureReel, String anneUniversitaire, int coordonnateurId, int moduleId, byte[] epreuve) {
        try {
            Optional<Examen> examenOptional = examenRepository.findById(id);
            if (!examenOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Examen not found with ID: " + id);
            }
            Examen examen = examenOptional.get();

            examen.setNom(nom);
            examen.setSemestre(semestre);
            examen.setSesion(sesion);
            examen.setType(type);
            examen.setDate(date);
            examen.setHeur(heur);
            examen.setDure_prevu(durePrevu);
            examen.setDure_reel(dureReel);
            examen.setAnneUniversitaire(anneUniversitaire);
            if (coordonnateurId != 0) {
                Optional<Enseignant> coordonnateurOptional = enseignantRepository.findById(coordonnateurId);
                if (!coordonnateurOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Coordonnateur not found with ID: " + coordonnateurId);
                }
                examen.setCoordonnateur(coordonnateurOptional.get());
            } else {
                examen.setCoordonnateur(null);
            }

            if (moduleId != 0) {
                Optional<Module> moduleOptional = moduleRepository.findById(moduleId);
                if (!moduleOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Module not found with ID: " + moduleId);
                }
                examen.setModule(moduleOptional.get());
            } else {
                examen.setModule(null);
            }

            if (epreuve != null && epreuve.length > 0) {
                examen.setEpreuve(epreuve);
            }

            examenRepository.save(examen);

            return ResponseEntity.ok(examen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteExamen(int id) {
        try {
            Optional<Examen> examenOptional = examenRepository.findById(id);
            if (!examenOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Examen not found with ID: " + id);
            }
            examenRepository.delete(examenOptional.get());

            return ResponseEntity.ok("Examen deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> FinishedExamen(int id, String rapport, byte[] pv) {
        try {
            Optional<Examen> examenOptional = examenRepository.findById(id);
            if (!examenOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Examen not found with ID: " + id);
            }

            Examen examen = examenOptional.get();

            examen.setRapport(rapport);
            examen.setPV(pv);

            examenRepository.save(examen);


            return ResponseEntity.ok(examen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

}
