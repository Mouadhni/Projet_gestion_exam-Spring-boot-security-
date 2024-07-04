package org.example.miniprojet.controller;

import org.example.miniprojet.model.User;
import org.example.miniprojet.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class AuthenticationController {
    @Autowired
    private  final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseEntity<?>> registrer(@RequestBody User request){

        return ResponseEntity.ok(authenticationService.registrer(request));
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseEntity<?>> login(@RequestBody User request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
