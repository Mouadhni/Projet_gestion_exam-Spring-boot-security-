package org.example.miniprojet.controller.Admin;

import org.example.miniprojet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("login/admin")
public class GrpEnseignantController {
    @Autowired
    private  final GrpEnseignantService grpEnseignantService;

    public GrpEnseignantController(GrpEnseignantService grpEnseignantService) {
        this.grpEnseignantService = grpEnseignantService;
    }


    @GetMapping("/getAllGrpEnseignants")
    public ResponseEntity<ResponseEntity<?>> getAllGrpEnseignants(){

        return ResponseEntity.ok(grpEnseignantService.getAllGrpEnseignants());
    }

    @PostMapping("/createGrpEnseignant")
    public ResponseEntity<ResponseEntity<?>> createGrp(@RequestBody Map<String, Object> requestBody) {
        List<Integer> ids = (List<Integer>) requestBody.get("ids");
        String nom = (String) requestBody.get("nom");
        return ResponseEntity.ok(grpEnseignantService.createGrp(ids,nom));
    }
    @PutMapping("/updateGrpEnseignant/{id}")
    public ResponseEntity<ResponseEntity<?>> updateGrpEnseignant(@PathVariable int id,@RequestBody Map<String, Object> requestBody) {
        List<Integer> ids = (List<Integer>) requestBody.get("ids");
        String nom = (String) requestBody.get("nom");


        return ResponseEntity.ok(grpEnseignantService.updateGrpEnseignant(id,ids,nom));
    }
    @DeleteMapping("/deleteGrpEnseignant/{id}")
    public ResponseEntity<ResponseEntity<?>> deleteGrpEnseignant(@PathVariable int id) {



        return ResponseEntity.ok(grpEnseignantService.deleteGrpEnseignant(id));
    }

}
