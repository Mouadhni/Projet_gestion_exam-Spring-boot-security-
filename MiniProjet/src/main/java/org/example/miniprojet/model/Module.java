package org.example.miniprojet.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.List;
@Entity
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    @OneToMany(mappedBy = "module")
    private List<Element> listElement;

    @JsonIgnore // Add this annotation
    @OneToMany(mappedBy = "module")
    private List<Examen> listExamen;
    public Module() {
    }

    public Module(String nom, String description, Filiere filiere, List<Element> listElement) {
        this.nom = nom;
        this.description = description;
        this.filiere = filiere;
        this.listElement = listElement;
    }

    public Module(String nom, String description, Filiere filiere) {
        this.nom = nom;
        this.description = description;
        this.filiere = filiere;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public List<Element> getListElement() {
        return listElement;
    }

    public void setListElement(List<Element> listElement) {
        this.listElement = listElement;
    }

    public List<Examen> getListExamen() {
        return listExamen;
    }

    public void setListExamen(List<Examen> listExamen) {
        this.listExamen = listExamen;
    }
}
