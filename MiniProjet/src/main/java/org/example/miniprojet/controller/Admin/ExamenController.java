package org.example.miniprojet.controller.Admin;

import org.example.miniprojet.model.Semestre;
import org.example.miniprojet.model.Session;
import org.example.miniprojet.model.Type_Examen;
import org.example.miniprojet.service.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("login/admin")
public class ExamenController {
    @Autowired
    private  final ExamenService examenService;


    public ExamenController(ExamenService examenService) {

        this.examenService = examenService;
    }
    @GetMapping("/getAllExamens")
    public ResponseEntity<ResponseEntity<?>> getAllExamens(){

        return ResponseEntity.ok(examenService.getAllExamens());
    }
    @DeleteMapping("/deleteExamen/{id}")
    public ResponseEntity<ResponseEntity<?>> deleteExamen(@PathVariable int id ){

        return ResponseEntity.ok(examenService.deleteExamen(id));
    }

    @PostMapping("/createExamen")
    public ResponseEntity<ResponseEntity<?>> createExamen(@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        String semestreStr = (String) requestBody.get("semestre");
        Semestre semestre = Semestre.valueOf(semestreStr);

        String sessionStr = (String) requestBody.get("sesion");
        Session sesion = Session.valueOf(sessionStr);

        String typeStr = (String) requestBody.get("type");
        Type_Examen type = Type_Examen.valueOf(typeStr);
        String dateString = (String) requestBody.get("date");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.ok(ResponseEntity.badRequest().body("Invalid date format"));
        }

        double heur = 0;
        double dure_reel = 0;
        double dure_prevu = 0;

        if (requestBody.get("heur") instanceof String) {
            heur = Double.parseDouble((String) requestBody.get("heur"));
        } else if (requestBody.get("heur") instanceof Number) {
            heur = ((Number) requestBody.get("heur")).doubleValue();
        }

        if (requestBody.get("dure_reel") instanceof String) {
            dure_reel = Double.parseDouble((String) requestBody.get("dure_reel"));
        } else if (requestBody.get("dure_reel") instanceof Number) {
            dure_reel = ((Number) requestBody.get("dure_reel")).doubleValue();
        }

        if (requestBody.get("dure_prevu") instanceof String) {
            dure_prevu = Double.parseDouble((String) requestBody.get("dure_prevu"));
        } else if (requestBody.get("dure_prevu") instanceof Number) {
            dure_prevu = ((Number) requestBody.get("dure_prevu")).doubleValue();
        }

        String anneUniversitaire = (String) requestBody.get("anneUniversitaire");
        int coordonnateur_id = 0;
        int module_id = 0;

        if (requestBody.get("coordonnateur_id") != null && !requestBody.get("coordonnateur_id").toString().isEmpty()) {
            coordonnateur_id = Integer.parseInt(requestBody.get("coordonnateur_id").toString());
        }
        if (requestBody.get("module_id") != null && !requestBody.get("module_id").toString().isEmpty()) {
            module_id = Integer.parseInt(requestBody.get("module_id").toString());
        }
//        System.out.println(requestBody.get("epreuve"));
//        String epreuveString=(String) requestBody.get("epreuve");

        byte[] epreuve = Base64.getDecoder().decode((String) requestBody.get("epreuve"));


        return ResponseEntity.ok(examenService.createExamen(nom,semestre,sesion,type,date,heur,dure_prevu,dure_reel,anneUniversitaire,coordonnateur_id,module_id,epreuve));
    }
    @PutMapping("/UpdateExamen/{id}")
    public ResponseEntity<ResponseEntity<?>> UpdateExamen(@PathVariable int id ,@RequestBody Map<String, Object> requestBody) {
        String nom = (String) requestBody.get("nom");
        String semestreStr = (String) requestBody.get("semestre");
        Semestre semestre = Semestre.valueOf(semestreStr);

        String sessionStr = (String) requestBody.get("sesion");
        Session sesion = Session.valueOf(sessionStr);

        String typeStr = (String) requestBody.get("type");
        Type_Examen type = Type_Examen.valueOf(typeStr);
        String dateString = (String) requestBody.get("date");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.ok(ResponseEntity.badRequest().body("Invalid date format"));
        }

        double heur = 0;
        double dure_reel = 0;
        double dure_prevu = 0;

        if (requestBody.get("heur") instanceof String) {
            heur = Double.parseDouble((String) requestBody.get("heur"));
        } else if (requestBody.get("heur") instanceof Number) {
            heur = ((Number) requestBody.get("heur")).doubleValue();
        }

        if (requestBody.get("dure_reel") instanceof String) {
            dure_reel = Double.parseDouble((String) requestBody.get("dure_reel"));
        } else if (requestBody.get("dure_reel") instanceof Number) {
            dure_reel = ((Number) requestBody.get("dure_reel")).doubleValue();
        }

        if (requestBody.get("dure_prevu") instanceof String) {
            dure_prevu = Double.parseDouble((String) requestBody.get("dure_prevu"));
        } else if (requestBody.get("dure_prevu") instanceof Number) {
            dure_prevu = ((Number) requestBody.get("dure_prevu")).doubleValue();
        }

        String anneUniversitaire = (String) requestBody.get("anneUniversitaire");
        int coordonnateur_id = 0;
        int module_id = 0;

        if (requestBody.get("coordonnateur_id") != null && !requestBody.get("coordonnateur_id").toString().isEmpty()) {
            coordonnateur_id = Integer.parseInt(requestBody.get("coordonnateur_id").toString());
        }
        if (requestBody.get("module_id") != null && !requestBody.get("module_id").toString().isEmpty()) {
            module_id = Integer.parseInt(requestBody.get("module_id").toString());
        }
//        System.out.println(requestBody.get("epreuve"));
//        String epreuveString=(String) requestBody.get("epreuve");

        byte[] epreuve = Base64.getDecoder().decode((String) requestBody.get("epreuve"));


        return ResponseEntity.ok(examenService.UpdateExamen(id,nom,semestre,sesion,type,date,heur,dure_prevu,dure_reel,anneUniversitaire,coordonnateur_id,module_id,epreuve));
    }
    @PutMapping("/FinishedExamen/{id}")
    public ResponseEntity<ResponseEntity<?>> FinishedExamen(@PathVariable int id ,@RequestBody Map<String, Object> requestBody) {
        String rapport = (String) requestBody.get("rapport");

        byte[] pv = Base64.getDecoder().decode((String) requestBody.get("pv"));


        return ResponseEntity.ok(examenService.FinishedExamen(id,rapport,pv));
    }

}
