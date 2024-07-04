package org.example.miniprojet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    int id;
    String nom;
    
    @OneToOne
    @JoinColumn(name = "enseignant_id")
    Enseignant coordonnateur;

    @ManyToMany
    List<Enseignant> enseignant;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "module_id")
    Module module;

    public Element() {
    }

    public Element(String nom, Enseignant coordonnateur, List<Enseignant> enseignant, Module module) {
        this.nom = nom;
        this.coordonnateur = coordonnateur;
        this.enseignant = enseignant;
        this.module = module;
    }

    public Element(String nom, Enseignant enseignant, Module module) {
        this.nom = nom;
        this.coordonnateur = enseignant;
        this.module = module;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Enseignant getCoordonnateur() {
        return coordonnateur;
    }

    public void setCoordonnateur(Enseignant coordonnateur) {
        this.coordonnateur = coordonnateur;
    }

    public List<Enseignant> getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(List<Enseignant> enseignant) {
        this.enseignant = enseignant;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
