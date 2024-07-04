package org.example.miniprojet.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Entity
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;

    @Enumerated(EnumType.STRING)
    private Semestre semestre;

    @Enumerated(EnumType.STRING)
    private Session sesion;

    @Enumerated(EnumType.STRING)
    private Type_Examen type;

    private Date date;
    private double heur;
    private double dure_prevu;
    private double dure_reel;
    private String anneUniversitaire;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "coordonnateur_id")
    private Enseignant coordonnateur;
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "examen_salle",
            joinColumns = @JoinColumn(name = "examen_id"),
            inverseJoinColumns = @JoinColumn(name = "salle_id")
    )
    private List<Salle> lieu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "module_id")
    private Module module;

    private byte[] Epreuve;
    private byte[] PV;
    private String rapport;
    public Examen() {
    }

    public Examen(String nom, Semestre semestre, Session sesion, Type_Examen type, Date date, double heur, double dure_prevu, double dure_reel, String anneUniversitaire, Enseignant coordonnateur,Module module) {
        this.nom = nom;
        this.semestre = semestre;
        this.sesion = sesion;
        this.type = type;
        this.date = date;
        this.heur = heur;
        this.dure_prevu = dure_prevu;
        this.dure_reel = dure_reel;
        this.anneUniversitaire = anneUniversitaire;
        this.coordonnateur = coordonnateur;
        this.module=module;
    }

    public Examen(String nom, Semestre semestre, Session sesion, Type_Examen type, String date, double heur, double durePrevu, String anneUniversitaire, Enseignant enseignant) {
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

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public Session getSesion() {
        return sesion;
    }

    public void setSesion(Session sesion) {
        this.sesion = sesion;
    }

    public Type_Examen getType() {
        return type;
    }

    public void setType(Type_Examen type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getHeur() {
        return heur;
    }

    public void setHeur(double heur) {
        this.heur = heur;
    }

    public double getDure_prevu() {
        return dure_prevu;
    }

    public void setDure_prevu(double dure_prevu) {
        this.dure_prevu = dure_prevu;
    }

    public double getDure_reel() {
        return dure_reel;
    }

    public void setDure_reel(double dure_reel) {
        this.dure_reel = dure_reel;
    }

    public String getAnneUniversitaire() {
        return anneUniversitaire;
    }

    public void setAnneUniversitaire(String anneUniversitaire) {
        this.anneUniversitaire = anneUniversitaire;
    }

    public Enseignant getCoordonnateur() {
        return coordonnateur;
    }

    public void setCoordonnateur(Enseignant coordonnateur) {
        this.coordonnateur = coordonnateur;
    }

    public List<Salle> getLieu() {
        return lieu;
    }

    public void setLieu(List<Salle> lieu) {
        this.lieu = lieu;
    }

    public byte[] getEpreuve() {
        return Epreuve;
    }

    public void setEpreuve(byte[] epreuve) {
        Epreuve = epreuve;
    }

    public byte[] getPV() {
        return PV;
    }

    public void setPV(byte[] PV) {
        this.PV = PV;
    }

    public String getRapport() {
        return rapport;
    }

    public void setRapport(String rapport) {
        this.rapport = rapport;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
