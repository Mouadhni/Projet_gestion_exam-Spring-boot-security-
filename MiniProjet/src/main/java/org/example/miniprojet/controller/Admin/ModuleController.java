package org.example.miniprojet.controller.Admin;

import org.example.miniprojet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("login/admin")
public class ModuleController {
    @Autowired
        private  final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }


    @GetMapping("/getModule/{id}")
    public ResponseEntity<ResponseEntity<?>> getModule(@PathVariable int id){

        return ResponseEntity.ok(moduleService.getModule(id));
    }
    @GetMapping("/getAllModules")
    public ResponseEntity<ResponseEntity<?>> getAllModules(){

        return ResponseEntity.ok(moduleService.getAllModules());
    }

    @PostMapping("/createModule")
    public ResponseEntity<ResponseEntity<?>> createModule(@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        String description = (String) requestBody.get("description");
        int filiere_id = (int) requestBody.get("filiere_id");

        return ResponseEntity.ok(moduleService.createModule(nom,description,filiere_id));


    }
    @PutMapping("/updateModule/{id}")
    public ResponseEntity<ResponseEntity<?>> updateModule(@PathVariable int id,@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        String description = (String) requestBody.get("description");
        int filiere_id = (int) requestBody.get("filiere_id");

        return ResponseEntity.ok(moduleService.updateModule(id,nom,description,filiere_id));
    }
    @DeleteMapping("/deleteModule/{id}")
    public ResponseEntity<ResponseEntity<?>> deleteModule(@PathVariable int id) {

        return ResponseEntity.ok(moduleService.deleteModule(id));
    }

}
