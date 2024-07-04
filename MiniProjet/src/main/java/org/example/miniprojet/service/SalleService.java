package org.example.miniprojet.service;

import org.example.miniprojet.Exeption.CustomException;
import org.example.miniprojet.model.Cadre_Admin;
import org.example.miniprojet.model.Enseignant;
import org.example.miniprojet.model.Examen;
import org.example.miniprojet.model.Salle;
import org.example.miniprojet.repository.Cadre_AdminRepositroy;
import org.example.miniprojet.repository.EnseignantRepository;
import org.example.miniprojet.repository.ExamenRepository;
import org.example.miniprojet.repository.SalleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SalleService {
    private  final SalleRepository salleRepository;
    private  final Cadre_AdminRepositroy cadreAdminRepositroy;
    private  final EnseignantRepository enseignantRepository;


    private  final ExamenRepository examenRepository;


    public SalleService(SalleRepository salleRepository, Cadre_AdminRepositroy cadreAdminRepositroy, EnseignantRepository enseignantRepository, ExamenRepository examenRepository) {
        this.salleRepository = salleRepository;
        this.cadreAdminRepositroy = cadreAdminRepositroy;
        this.enseignantRepository = enseignantRepository;
        this.examenRepository = examenRepository;
    }
    public ResponseEntity<?> getAllSalles() {
        try {
            List<Salle> salleList = salleRepository.findAll();
            if (!salleList.isEmpty()) {

                return ResponseEntity.ok(salleList);
            } else {
                throw new CustomException("list est vide il nya aucun salle");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> createSalle(String nom, int capaciter, int examenId, int controllAbsenceId) {
        try {
            List<Examen> examen = new ArrayList<>();
            List<Cadre_Admin> controllAbsence = new ArrayList<>();

            if (examenId != 0) {
                Optional<Examen> examenOptional = examenRepository.findById(examenId);
                if (!examenOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Examen not found with ID: " + examenId);
                }
                examen.add( examenOptional.get());
            }

            if (controllAbsenceId != 0) {
                Optional<Cadre_Admin> controllAbsenceOptional = cadreAdminRepositroy.findById(controllAbsenceId);
                if (!controllAbsenceOptional.isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("ControllAbsence not found with ID: " + controllAbsenceId);
                }
                controllAbsence.add(controllAbsenceOptional.get())  ;
            }

            Salle salle = new Salle(nom, capaciter, examen, controllAbsence);
            salleRepository.save(salle);

            return ResponseEntity.ok(salle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> deleteSalle(int id) {
        try {
            Optional<Salle> salleOptional = salleRepository.findById(id);
            if (salleOptional.isPresent()) {
                salleRepository.deleteById(id);
                return ResponseEntity.ok("Salle deleted successfully with ID: " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Salle not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> updateSalle(int id, String nom, int capaciter, int examenId, int controleAbsenceId) {
        try {
            Optional<Salle> salleOptional = salleRepository.findById(id);
            if (salleOptional.isPresent()) {
                Salle salle = salleOptional.get();
                if (nom != null) {
                    salle.setNom(nom);
                }
                if (capaciter > 0) {
                    salle.setCapaciter(capaciter);
                }
                if (examenId > 0) {
                    Optional<Examen> examenOptional = examenRepository.findById(examenId);
                    if (examenOptional.isPresent()){
                        salle.getExamen().add(examenOptional.get());
                        salle.setExamen(salle.getExamen());
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Examen not found with ID: " + examenId);
                    }
                }else{
                    salle.setExamen(null);
                }
                if (controleAbsenceId > 0) {
                    Optional<Cadre_Admin> cadreAdminOptional = cadreAdminRepositroy.findById(controleAbsenceId);
                    if (cadreAdminOptional.isPresent()) {
                       salle.getControll_absence().add(cadreAdminOptional.get()) ;
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("ControleAbsence not found with ID: " + controleAbsenceId);
                    }
                }else{
                    salle.setExamen(null);
                }
                salleRepository.save(salle);
                return ResponseEntity.ok(salle);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Salle not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getEmptySalles(Date date) {
        try {
            List<Salle> salleList = salleRepository.findEmptySallesByExamDate(date);

            System.out.println(salleList);

            if (!salleList.isEmpty()) {
                return ResponseEntity.ok(salleList);
            } else {
                throw new CustomException("list est vide il nya aucune salle");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> Affectation(int examenId, int salleId, List<Integer> enseignantIds, int controllAbsenceId) {
        Optional<Examen> examenOptional = examenRepository.findById(examenId);
        Optional<Salle> salleOptional = salleRepository.findById(salleId);
        Optional<Cadre_Admin> cadreAdminOptional = cadreAdminRepositroy.findById(controllAbsenceId);

        if (examenOptional.isEmpty() || salleOptional.isEmpty() || cadreAdminOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Examen, Salle, or Cadre Admin not found");
        }

        Examen examen = examenOptional.get();
        Salle salle = salleOptional.get();
        Cadre_Admin cadreAdmin = cadreAdminOptional.get();

        List<Enseignant> enseignants = enseignantRepository.findAllById(enseignantIds);
        if (enseignants.isEmpty() || enseignants.size() != enseignantIds.size()) {
            return ResponseEntity.badRequest().body("Some enseignants not found");
        }

        if (!salle.getExamen().contains(examen)) {
            salle.getExamen().add(examen);
            examen.getLieu().add(salle);
        }

        salle.getSurveillant().clear();
        for (Enseignant enseignant : enseignants) {
            if (!enseignant.getSalle().contains(salle)) {
                enseignant.getSalle().add(salle);
            }
            salle.getSurveillant().add(enseignant);
        }

        if (!salle.getControll_absence().contains(cadreAdmin)) {
            salle.getControll_absence().add(cadreAdmin);
        }

        salleRepository.save(salle);

        return ResponseEntity.ok("Affectation completed successfully");
    }
    public ResponseEntity<List<Map<String, Object>>> getAllExamsWithDetails() {
        List<Examen> examens = examenRepository.findAll();
        List<Map<String, Object>> examsList = new ArrayList<>();

        for (Examen examen : examens) {
            Map<String, Object> examDetails = new HashMap<>();
            examDetails.put("examen_id", examen.getId());
            examDetails.put("examen_name", examen.getNom());
            examDetails.put("epreuve", examen.getEpreuve());


            List<Map<String, Object>> sallesList = new ArrayList<>();
            for (Salle salle : examen.getLieu()) {
                Map<String, Object> salleDetails = new HashMap<>();
                salleDetails.put("salle_id", salle.getId());
                salleDetails.put("salle_name", salle.getNom());

                List<Map<String, Object>> enseignantsList = new ArrayList<>();
                for (Enseignant enseignant : salle.getSurveillant()) {
                    Map<String, Object> enseignantDetails = new HashMap<>();
                    enseignantDetails.put("enseignant_id", enseignant.getId());
                    enseignantDetails.put("enseignant_name", enseignant.getUsername());
                    enseignantsList.add(enseignantDetails);
                }
                salleDetails.put("surveillants", enseignantsList);

                List<Map<String, Object>> cadreAdminsList = new ArrayList<>();
                for (Cadre_Admin cadreAdmin : salle.getControll_absence()) {
                    Map<String, Object> cadreAdminDetails = new HashMap<>();
                    cadreAdminDetails.put("cadre_admin_id", cadreAdmin.getId());
                    cadreAdminDetails.put("cadre_admin_name", cadreAdmin.getUsername());
                    cadreAdminsList.add(cadreAdminDetails);
                }
                salleDetails.put("cadreAdmins", cadreAdminsList);

                Map<String, Object> examWithSalle = new HashMap<>(examDetails);
                examWithSalle.put("salle", salleDetails);

                examsList.add(examWithSalle);
            }

            if (examen.getLieu().isEmpty()) {
                examsList.add(examDetails);
            }
        }

        return ResponseEntity.ok(examsList);
    }
    public ResponseEntity<?> deleteAffectation(Integer examen_id,  Integer salle_id) {
        if (examen_id == null || salle_id == null) {
            return ResponseEntity.badRequest().body("Examen ID and Salle ID are required");
        }

        Optional<Examen> examenOptional = examenRepository.findById(examen_id);
        Optional<Salle> salleOptional = salleRepository.findById(salle_id);

        if (examenOptional.isEmpty() || salleOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Examen or Salle not found");
        }

        Examen examen = examenOptional.get();
        Salle salle = salleOptional.get();

        examen.getLieu().remove(salle);
        salle.getExamen().remove(examen);

        for (Enseignant enseignant : salle.getSurveillant()) {
            enseignant.getSalle().remove(salle);
        }
        salle.getSurveillant().clear();

        for (Cadre_Admin cadreAdmin : salle.getControll_absence()) {
            cadreAdmin.getListSalle().remove(salle);
        }
        salle.getControll_absence().clear();

        examenRepository.save(examen);
        salleRepository.save(salle);

        return ResponseEntity.ok("Affectation deleted successfully");
    }

}
