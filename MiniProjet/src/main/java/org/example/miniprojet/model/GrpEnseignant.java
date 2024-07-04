package org.example.miniprojet.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class GrpEnseignant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    int id ;
    String nom;
    @JsonManagedReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name= "GrpEnseignat_enseignant",
            joinColumns = @JoinColumn(name="grpEnseignant_id"),
            inverseJoinColumns = @JoinColumn(name="enseignant_id")
    )
    List<Enseignant> listEnseignant;
    public GrpEnseignant() {
    }

    public GrpEnseignant(String nom, List<Enseignant> listEnseignant) {
        this.nom = nom;
        this.listEnseignant = listEnseignant;
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

    public List<Enseignant> getListEnseignant() {
        return listEnseignant;
    }

    public void setListEnseignant(List<Enseignant> listEnseignant) {
        this.listEnseignant = listEnseignant;
    }
}
