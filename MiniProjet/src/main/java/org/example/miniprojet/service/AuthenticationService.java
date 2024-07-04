package org.example.miniprojet.service;

import org.example.miniprojet.Exeption.CustomException;
import org.example.miniprojet.model.*;
//import org.example.miniprojet.repository.EnseignantRepository;
import org.example.miniprojet.repository.Cadre_AdminRepositroy;
import org.example.miniprojet.repository.EnseignantRepository;
import org.example.miniprojet.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {



    private  final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationService( UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> userList = userRepository.findAll();
            System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr"+userList.toString());

            if (!userList.isEmpty()) {

                return ResponseEntity.ok(userList);
            } else {
                throw new CustomException("list est vide il nya aucun user");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }

    }
    public ResponseEntity<?> registrer(User request) {
        try {
            Optional<User> existingUser = userRepository.findUserByUsername(request.getUsername());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Username already exists: " + request.getUsername());
            }

            User user;
            if (request.getType() == Type.ENSEIGNANT) {
                user = new Enseignant();
            } else if (request.getType() == Type.ADMIN) {
                user = new Cadre_Admin();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid user type: " + request.getType());
            }

            // Populate user fields
            user.setUsername(request.getUsername());
            user.setLastname(request.getLastname());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setType(request.getType());

            user = userRepository.save(user);
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(new AuthenticationResponse(token,request.getType()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> authenticate(User request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            User user = userRepository.findUserByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(new AuthenticationResponse(token,user.getType(),user));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> delete(int id) {
        try {
            Optional<User> userOptional = userRepository.findUserById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                userRepository.delete(user);
                return ResponseEntity.ok(user.getUsername()+" "+user.getLastname()+ " has been successfully deleted");
            } else {
                throw new UsernameNotFoundException("User not found with id: " + id);
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> update(User user, int id) {

        try {
            System.out.println("use r  :"+user+id);
            if (user == null) {
                return ResponseEntity.badRequest().body("User object is null.");
            }
            Optional<User> oldUserOptional = userRepository.findUserById(id);
            if (oldUserOptional.isPresent()) {
                System.out.println(oldUserOptional.get());
                User oldUser = oldUserOptional.get();
                if (oldUser.getType() == Type.ENSEIGNANT) {
                    System.out.println("ensegnant");


                        User enseignant = oldUserOptional.get();
                        enseignant.setUsername(user.getUsername());
                        enseignant.setLastname(user.getLastname());
                        enseignant.setEmail(user.getEmail());
                        enseignant.setType(user.getType());
                        System.out.println(enseignant);

                        System.out.println(enseignant+"sdfghjklm");

                        enseignant=userRepository.save(enseignant);
                        System.out.println(enseignant+"sdfghjklm");
                        System.out.println("mok ya mok ");

                        return ResponseEntity.ok(enseignant.getUsername() + " " + enseignant.getLastname() + " has been successfully updated");

                } else if (oldUser.getType() == Type.ADMIN) {



                        User cadreAdmin = oldUserOptional.get();
                        cadreAdmin.setUsername(user.getUsername());
                        cadreAdmin.setLastname(user.getLastname());
                        cadreAdmin.setEmail(user.getEmail());
                        cadreAdmin.setType(user.getType());
                        cadreAdmin=userRepository.save(cadreAdmin);
                        return ResponseEntity.ok(cadreAdmin.getUsername() + " " + cadreAdmin.getLastname() + " has been successfully updated");

                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Invalid user type: " + user.getType());
                }
            } else {
                throw new UsernameNotFoundException("User not found with id: " + id);
            }
            // Add a return statement in case none of the conditions are met

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

}
