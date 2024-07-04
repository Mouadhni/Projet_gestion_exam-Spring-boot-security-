package org.example.miniprojet.service;

import org.example.miniprojet.Exeption.CustomException;
import org.example.miniprojet.model.Enseignant;
import org.example.miniprojet.model.GrpEnseignant;
import org.example.miniprojet.repository.EnseignantRepository;
import org.example.miniprojet.repository.GrpEnseignantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GrpEnseignantService {
    private  final GrpEnseignantRepository grpEnseignantRepository;
    private  final EnseignantRepository enseignantRepository;
    public GrpEnseignantService(GrpEnseignantRepository grpEnseignantRepository, EnseignantRepository enseignantRepository) {
        this.grpEnseignantRepository = grpEnseignantRepository;
        this.enseignantRepository = enseignantRepository;
    }
    public ResponseEntity<?> updateGrpEnseignant(int id, List<Integer> userIds, String nom) {
        try {
            Optional<GrpEnseignant> grpOptional = grpEnseignantRepository.findById(id);
            if (grpOptional.isPresent()) {
                GrpEnseignant grp = grpOptional.get();

                if (nom != null) {
                    grp.setNom(nom);
                }

                List<Enseignant> enseignants = new ArrayList<>();
                for (Integer userId : userIds) {
                    Optional<Enseignant> enseignantOptional = enseignantRepository.findEnseignantById(userId);
                    enseignantOptional.ifPresent(enseignants::add);
                }
                grp.setListEnseignant(enseignants);

                GrpEnseignant updatedGrp = grpEnseignantRepository.save(grp);
                return ResponseEntity.ok(updatedGrp);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GrpEnseignant not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> getAllGrpEnseignants() {
        try {
            List<GrpEnseignant> grpEnseignantsList = grpEnseignantRepository.findAll();
            if (!grpEnseignantsList.isEmpty()) {

                return ResponseEntity.ok(grpEnseignantsList);
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
    public ResponseEntity<?> deleteGrpEnseignant(int id) {
        try {
            Optional<GrpEnseignant> grpOptional = grpEnseignantRepository.findById(id);
            if (grpOptional.isPresent()) {
                grpEnseignantRepository.deleteById(id);
                return ResponseEntity.ok("GrpEnseignant with ID " + id + " has been successfully deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("GrpEnseignant not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> createGrp(List<Integer> userIds, String name) {
        try{
            List<Enseignant> enseignants = new ArrayList<>();
            for (Integer userId : userIds) {
                Optional<Enseignant> enseignantOptional = enseignantRepository.findEnseignantById(userId);
                enseignantOptional.ifPresent(enseignants::add);
            }
            GrpEnseignant grp=new GrpEnseignant(name, enseignants);
            grpEnseignantRepository.save(grp);

            return ResponseEntity.ok(grp);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }

    }

}
