package org.example.miniprojet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String nom;
    int capaciter;
    @JsonManagedReference
    @OneToMany(mappedBy = "salle")
    List<Etudiant> listEtudiant;
    @ManyToMany(mappedBy = "lieu", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Examen> examen;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "salle_cadreadmin",
            joinColumns = @JoinColumn(name = "salle_id"),
            inverseJoinColumns = @JoinColumn(name = "cadreadmin_id")
    )
    private List<Cadre_Admin> controll_absence;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "enseignant_salle",
            joinColumns = @JoinColumn(name = "salle_id"),
            inverseJoinColumns = @JoinColumn(name = "enseignant_id")
    )
    private List<Enseignant> surveillant;
    public Salle() {
    }
        public Salle(String nom, int capaciter, List<Examen>  examen, List<Cadre_Admin> cadre ) {
        this.nom = nom;
        this.capaciter = capaciter;
        this.examen = examen;
        this.controll_absence = cadre;
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

    public int getCapaciter() {
        return capaciter;
    }

    public void setCapaciter(int capaciter) {
        this.capaciter = capaciter;
    }

    public List<Examen>  getExamen() {
        return examen;
    }

    public void setExamen(List<Examen>  examen) {
        this.examen = examen;
    }

    public List<Etudiant> getListEtudiant() {
        return listEtudiant;
    }

    public void setListEtudiant(List<Etudiant> listEtudiant) {
        this.listEtudiant = listEtudiant;
    }

    public List<Enseignant> getSurveillant() {
        return surveillant;
    }

    public void setSurveillant(List<Enseignant> surveillant) {
        this.surveillant = surveillant;
    }

    public List<Cadre_Admin> getControll_absence() {
        return controll_absence;
    }

    public void setControll_absence(List<Cadre_Admin> controll_absence) {
        this.controll_absence = controll_absence;
    }
}
