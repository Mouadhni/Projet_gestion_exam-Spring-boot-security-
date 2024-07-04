package org.example.miniprojet.controller.Admin;

import org.example.miniprojet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("login/admin")
public class DepartementController {
    @Autowired
    private  final DepartemementService departemementService;

    public DepartementController(DepartemementService departemementService) {
        this.departemementService = departemementService;
    }


    @GetMapping("/getAllDepartements")
    public ResponseEntity<ResponseEntity<?>> getAllDepartements(){

        return ResponseEntity.ok(departemementService.getAllDepartements());
    }
    @PostMapping("/createDepartement")
    public ResponseEntity<ResponseEntity<?>> createDepatement(@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        String description = (String) requestBody.get("description");
        int enseignant_id=0;
        if(requestBody.get("enseignant_id")!="")
         enseignant_id = (int) requestBody.get("enseignant_id");

        return ResponseEntity.ok(departemementService.createDepartement(nom,description,enseignant_id));
    }
    @PutMapping("/updateDepartement/{id}")
    public ResponseEntity<ResponseEntity<?>> updateDepatement(@PathVariable int id,@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        String description = (String) requestBody.get("description");
        int enseignant_id=0;
        if(requestBody.get("enseignant_id")!="")
            enseignant_id = (int) requestBody.get("enseignant_id");

        return ResponseEntity.ok(departemementService.updateDepatement(id,nom,description,enseignant_id));
    }
    @DeleteMapping("/deleteDepartement/{id}")
    public ResponseEntity<ResponseEntity<?>> deleteDepatement(@PathVariable int id) {

        return ResponseEntity.ok(departemementService.deleteDepatement(id));
    }
}
