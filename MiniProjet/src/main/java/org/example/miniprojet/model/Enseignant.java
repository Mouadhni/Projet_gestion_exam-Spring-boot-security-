package org.example.miniprojet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Enseignant extends User{

    @JsonIgnore
    @OneToMany(mappedBy = "coordonnateur")
    List<Filiere> listFiliere;
    @JsonIgnore
    @OneToMany(mappedBy = "coordonnateur")
    List<Examen> listExam;
    @ManyToMany
    List<GrpEnseignant> listGrpEnseignant;

    @ManyToMany
    @JoinTable(
            name= "enseignant_element",
            joinColumns = @JoinColumn(name="enseignant_id"),
            inverseJoinColumns = @JoinColumn(name="element_id")
    )
    List<Element> listElement;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "enseignant_salle",
            joinColumns = @JoinColumn(name = "enseignant_id"),
            inverseJoinColumns = @JoinColumn(name = "salle_id")
    )
    List<Salle> salle;

    public Enseignant() {
    }

    public Enseignant(String username, String lastname, String email, String password, Type type) {
        super(username, lastname, email, password, type);
    }

    public List<Filiere> getListFiliere() {
        return listFiliere;
    }

    public void setListFiliere(List<Filiere> listFiliere) {
        this.listFiliere = listFiliere;
    }

    public List<Examen> getListExam() {
        return listExam;
    }

    public void setListExam(List<Examen> listExam) {
        this.listExam = listExam;
    }


    public List<GrpEnseignant> getListGrpEnseignant() {
        return listGrpEnseignant;
    }
    public void setListGrpEnseignant(List<GrpEnseignant> listGrpEnseignant) {
        this.listGrpEnseignant = listGrpEnseignant;
    }

    public List<Element> getListElement() {
        return listElement;
    }

    public void setListElement(List<Element> listElement) {
        this.listElement = listElement;
    }

    public List<Salle> getSalle() {
        return salle;
    }
    @Override
    public Type getType() {
        return this.type;
    }
    public void setSalle(List<Salle> salle) {
        this.salle = salle;
    }
}
