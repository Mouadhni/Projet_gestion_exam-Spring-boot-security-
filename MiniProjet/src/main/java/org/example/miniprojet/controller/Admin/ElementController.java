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
public class ElementController {
    @Autowired
    private  final ElementService elementService;

    public ElementController(ElementService elementService) {
        this.elementService = elementService;
    }


    @GetMapping("/getAllElements")
    public ResponseEntity<ResponseEntity<?>> getAllElements(){

        return ResponseEntity.ok(elementService.getAllElements());
    }

    @PostMapping("/createElement")
    public ResponseEntity<ResponseEntity<?>> createElement(@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        int enseignant_id = (int) requestBody.get("enseignant_id");
        int module_id = (int) requestBody.get("module_id");



        return ResponseEntity.ok(elementService.createElement(nom,enseignant_id,module_id));
    }
    @PostMapping("/affecterElement")
    public ResponseEntity<ResponseEntity<?>> affecterElement(@RequestBody Map<String, Object> requestBody) {
        List<Integer> enseignant_ids = (List<Integer>) requestBody.get("enseignant_ids");

        int element_id = (int) requestBody.get("element_id");



        return ResponseEntity.ok(elementService.affecterElement(element_id,enseignant_ids));
    }
    @PutMapping("/updateElement/{id}")
    public ResponseEntity<?> updateElement(@PathVariable int id, @RequestBody Map<String, Object> requestBody) {
            String nom = (String) requestBody.get("nom");
            int enseignantId = (int) requestBody.get("enseignant_id");
            int moduleId = (int) requestBody.get("module_id");


        return ResponseEntity.ok(elementService.updateElement(id,nom,moduleId,enseignantId));
    }
    @DeleteMapping("/deleteElement/{id}")
    public ResponseEntity<?> deleteElement(@PathVariable int id) {

        return ResponseEntity.ok(elementService.deleteElement(id));
    }

}
