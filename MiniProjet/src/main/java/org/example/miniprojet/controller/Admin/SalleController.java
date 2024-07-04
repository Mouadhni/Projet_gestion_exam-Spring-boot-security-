package org.example.miniprojet.controller.Admin;

import org.example.miniprojet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("login/admin")
public class SalleController {
    @Autowired
    private  final SalleService salleService;


    public SalleController(SalleService salleService) {

        this.salleService = salleService;

    }
    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    @GetMapping("/getAllSalles")
    public ResponseEntity<ResponseEntity<?>> getAllSalles(){

        return ResponseEntity.ok(salleService.getAllSalles());
    }
    @GetMapping("/getAllExamsWithDetails")
    public ResponseEntity<ResponseEntity<?>> getAllExamsWithDetails(){

        return ResponseEntity.ok(salleService.getAllExamsWithDetails());
    }

    @GetMapping("/getEmptySalles/{date}")
    public ResponseEntity<ResponseEntity<?>> getEmptySalles(@PathVariable String date){

        Date datee = convertStringToDate(date);
        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"+datee);
        return ResponseEntity.ok(salleService.getEmptySalles(datee));
    }
    @PostMapping("/createSalle")
    public ResponseEntity<ResponseEntity<?>> createSalle(@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        int examen_id= 0;
        if(requestBody.get("examen_id")!=""){
            examen_id = (int) requestBody.get("examen_id");
        }

        int controll_absence_id = 0;
        if (requestBody.get("controll_absence_id")!=""){
            controll_absence_id = (int) requestBody.get("controll_absence_id");
        }
        int capaciter = (int) requestBody.get("capaciter");

        return ResponseEntity.ok(salleService.createSalle(nom,capaciter,examen_id,controll_absence_id));


    }
    @PutMapping("/updateSalle/{id}")
    public ResponseEntity<ResponseEntity<?>> updateSalle(@PathVariable int id,@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        int examen_id= 0;
        if(requestBody.get("examen_id")!=""){
           examen_id = (int) requestBody.get("examen_id");
        }

        int controll_absence_id = 0;
        if (requestBody.get("controll_absence_id")!=""){
            controll_absence_id = (int) requestBody.get("controll_absence_id");
        }
        int capaciter = (int) requestBody.get("capaciter");


        return ResponseEntity.ok(salleService.updateSalle(id,nom,capaciter,examen_id,controll_absence_id));
    }
    @DeleteMapping("/deleteSalle/{id}")
    public ResponseEntity<ResponseEntity<?>> deleteSalle(@PathVariable int id) {

        return ResponseEntity.ok(salleService.deleteSalle(id));
    }
    @PostMapping("/affecterSalle")
    public ResponseEntity<ResponseEntity<?>> Affectation(@RequestBody Map<String, Object> requestBody) {
        Integer examen_id = (Integer) requestBody.get("examen_id");
        Integer salle_id = (Integer) requestBody.get("salle_id");
        List<Integer> enseignant_ids = (List<Integer>) requestBody.get("enseignant_ids");
        Integer controll_absence_id = (Integer) requestBody.get("controll_absence_id");

        if (examen_id == null || salle_id == null || enseignant_ids.isEmpty()  || controll_absence_id == null) {
            return ResponseEntity.badRequest().body(ResponseEntity.badRequest().body("Examen, Salle, or Cadre Admin not found"));
        }

        System.out.println("ppppppppppppppppppppppppppppppppppppppppppppppppppp"+examen_id+","+salle_id+","+enseignant_ids+","+controll_absence_id);
        return ResponseEntity.ok(salleService.Affectation(examen_id,salle_id,enseignant_ids,controll_absence_id));


    }
    @DeleteMapping("/deleteAffectation/{examenId}/{salleId}")
    public ResponseEntity<ResponseEntity<?>> deleteAffectation(@PathVariable int examenId, @PathVariable int salleId) {

        return ResponseEntity.ok(salleService.deleteAffectation(examenId,salleId));
    }
}
