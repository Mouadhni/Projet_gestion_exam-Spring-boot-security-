package org.example.miniprojet.controller.Admin;

import org.example.miniprojet.model.*;
import org.example.miniprojet.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("login/admin")
public class WelcomeAdmin {
    @Autowired
    private  final AuthenticationService authenticationService;


    public WelcomeAdmin(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

    }
    @GetMapping("/getAllUsers")
    public ResponseEntity<ResponseEntity<?>> getAllUsers(){

        return ResponseEntity.ok(authenticationService.getAllUsers());
    }

    @PostMapping("/ajouter")
    public ResponseEntity<ResponseEntity<?>> registrer(@RequestBody User request){

        return ResponseEntity.ok(authenticationService.registrer(request));
    }
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<ResponseEntity<?>> supprimer(@PathVariable int id){

        return ResponseEntity.ok(authenticationService.delete(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseEntity<?>> update(@RequestBody User newUser,@PathVariable int id){


        return ResponseEntity.ok(authenticationService.update(newUser,id));
    }



}
