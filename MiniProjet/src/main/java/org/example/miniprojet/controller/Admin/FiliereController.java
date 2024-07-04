package org.example.miniprojet.controller.Admin;

import org.example.miniprojet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("login/admin")
public class FiliereController {
    @Autowired
    private  final FiliereService filiereService;

    public FiliereController(FiliereService filiereService) {
        this.filiereService = filiereService;
    }


    @GetMapping("/getAllFilieres")
    public ResponseEntity<ResponseEntity<?>> getAllFilieres(){

        return ResponseEntity.ok(filiereService.getAllFilieres());
    }
    @PostMapping("/createFiliere")
    public ResponseEntity<ResponseEntity<?>> createFiliere(@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        int coordonnateur_id = (int) requestBody.get("coordonnateur_id");
        int departement_id = (int) requestBody.get("departement_id");



        return ResponseEntity.ok(filiereService.createFiliere(nom,coordonnateur_id,departement_id));
    }
    @PutMapping("/updateFiliere/{id}")
    public ResponseEntity<ResponseEntity<?>> updateFiliere(@PathVariable int id,@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        int coordonnateur_id = (int) requestBody.get("coordonnateur_id");
        int departement_id = (int) requestBody.get("departement_id");



        return ResponseEntity.ok(filiereService.updateFiliere(id,nom,coordonnateur_id,departement_id));
    }
    @DeleteMapping("/deleteFiliere/{id}")
    public ResponseEntity<ResponseEntity<?>> deleteFiliere(@PathVariable int id) {

        return ResponseEntity.ok(filiereService.deleteFiliere(id));

    }
}
