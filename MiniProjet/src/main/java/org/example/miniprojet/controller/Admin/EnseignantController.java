package org.example.miniprojet.controller.Admin;

import org.example.miniprojet.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("login/admin")
public class EnseignantController {
    @Autowired
    private  final EnseignantService enseignantService;

    public EnseignantController(EnseignantService enseignantService) {

        this.enseignantService = enseignantService;
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
    @GetMapping("/getAllEnseignants")
    public ResponseEntity<ResponseEntity<?>> getAllEnseignants(){

        return ResponseEntity.ok(enseignantService.getAllEnseignants());
    }
    @GetMapping("/getFreeEnseignants/{date}")
    public ResponseEntity<ResponseEntity<?>> getFreeEnseignants(@PathVariable String date){
        Date datee = convertStringToDate(date);
        return ResponseEntity.ok(enseignantService.getFreeEnseignants(datee));
    }
}
