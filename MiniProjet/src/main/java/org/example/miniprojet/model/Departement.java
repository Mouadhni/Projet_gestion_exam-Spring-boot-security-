package org.example.miniprojet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String nom;
    String description;
    @OneToOne
    @JoinColumn(name = "enseignant_id")
    Enseignant chefDepartement;
    @JsonManagedReference
    @OneToMany(mappedBy = "departement")
    List<Filiere> listFiliere;

    public Departement() {
    }

    public Departement(String nom, String description, Enseignant chefDepartement, List<Filiere> listFiliere) {
        this.nom = nom;
        this.description = description;
        this.chefDepartement = chefDepartement;
        this.listFiliere = listFiliere;
    }

    public Departement(String nom, String description, Enseignant chefDepartement) {
        this.nom = nom;
        this.description = description;
        this.chefDepartement = chefDepartement;
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

    public Enseignant getChefDepartement() {
        return chefDepartement;
    }

    public void setChefDepartement(Enseignant chefDepartement) {
        this.chefDepartement = chefDepartement;
    }

    public List<Filiere> getListFiliere() {
        return listFiliere;
    }

    public void setListFiliere(List<Filiere> listFiliere) {
        this.listFiliere = listFiliere;
    }
}
