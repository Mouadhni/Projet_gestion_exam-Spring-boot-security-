package org.example.miniprojet.service;

import org.example.miniprojet.Exeption.CustomException;
import org.example.miniprojet.model.Element;
import org.example.miniprojet.model.Enseignant;
import org.example.miniprojet.model.Module;
import org.example.miniprojet.repository.ElementRepository;
import org.example.miniprojet.repository.EnseignantRepository;
import org.example.miniprojet.repository.ModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElementService {
    private  final ElementRepository elementRepository;
    private  final EnseignantRepository enseignantRepository;
    private  final ModuleRepository moduleRepository;
    

    public ElementService( ElementRepository elementRepository, EnseignantRepository enseignantRepository, ModuleRepository moduleRepository) {
        this.elementRepository = elementRepository;
        this.enseignantRepository = enseignantRepository;
        this.moduleRepository = moduleRepository;
    }
    public ResponseEntity<?> createElement(String nom, int enseignantId, int moduleId) {
        try {
            Optional<Module> moduleOptional = moduleRepository.findById(moduleId);
            Optional<Enseignant> enseignantOptional = enseignantRepository.findEnseignantById(enseignantId);


            if (moduleOptional.isPresent() && enseignantOptional.isPresent()) {
                Element element=new Element(nom, enseignantOptional.get(), moduleOptional.get());
                elementRepository.save(element);
                return ResponseEntity.ok(element);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("module ou enseignant  not found with ID: " + moduleId+ " "+enseignantId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> updateElement(int id,String nom,int module_id,int enseignant_id) {
        try {
            Optional<Element> elementOptional = elementRepository.findById(id);
            if (elementOptional.isPresent()) {
                Element element = elementOptional.get();

                if (nom != null) {
                    element.setNom(nom);
                }
                if (enseignant_id > 0) {
                    Optional<Enseignant> enseignantOptional = enseignantRepository.findEnseignantById(enseignant_id);
                    enseignantOptional.ifPresent(element::setCoordonnateur);
                }
                if (module_id > 0) {
                    Optional<Module> moduleOptional = moduleRepository.findById(module_id);
                    moduleOptional.ifPresent(element::setModule);
                }
                element.setEnseignant(null);
                elementRepository.save(element);

                return ResponseEntity.ok(element);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Element not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> deleteElement(int id) {
        try {
            Optional<Element> elementOptional = elementRepository.findById(id);
            if (elementOptional.isPresent()) {
                Element element = elementOptional.get();
                elementRepository.delete(element);
                return ResponseEntity.ok(element.getNom()+" "+element.getModule()+ " has been successfully deleted");
            } else {
                throw new UsernameNotFoundException("Element not found with id: " + id);
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    public ResponseEntity<?> getAllElements() {
        try {
            List<Element> elementsList = elementRepository.findAll();
            if (!elementsList.isEmpty()) {

                return ResponseEntity.ok(elementsList);
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


    public ResponseEntity<?> affecterElement(int elementId, List<Integer> enseignantIds) {
        // Retrieve the Element by its ID
        Optional<Element> optionalElement = elementRepository.findById(elementId);
        if (!optionalElement.isPresent()) {
            return ResponseEntity.status(404).body("Element not found");
        }
        Element element = optionalElement.get();

        List<Enseignant> enseignants = enseignantRepository.findAllById(enseignantIds);
        if (enseignants.isEmpty()) {
            return ResponseEntity.status(404).body("Enseignants not found");
        }

        element.setEnseignant(enseignants);
        elementRepository.save(element);

        return ResponseEntity.ok("Enseignants assigned to Element successfully");
    }

}
