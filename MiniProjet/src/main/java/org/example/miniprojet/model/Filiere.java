package org.example.miniprojet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    int id;
    String nom;
    @JsonManagedReference
    @OneToMany(mappedBy = "filiere")
    List<Module> listModule;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "coordonnateur_id")
    Enseignant coordonnateur;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "departement_id")
    Departement departement;

    public Filiere() {
    }

    public Filiere(String nom, List<Module> listModule, Enseignant coordonnateur, Departement departement) {
        this.nom = nom;
        this.listModule = listModule;
        this.coordonnateur = coordonnateur;
        this.departement = departement;
    }

    public Filiere(String nom, Enseignant enseignant, Departement departement) {
        this.nom = nom;
        this.coordonnateur = enseignant;
        this.departement = departement;
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

    public List<Module> getListModule() {
        return listModule;
    }

    public void setListModule(List<Module> listModule) {
        this.listModule = listModule;
    }

    public Enseignant getCoordonnateur() {
        return coordonnateur;
    }

    public void setCoordonnateur(Enseignant coordonnateur) {
        this.coordonnateur = coordonnateur;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
