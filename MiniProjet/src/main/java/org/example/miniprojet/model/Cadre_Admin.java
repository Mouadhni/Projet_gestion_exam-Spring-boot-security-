package org.example.miniprojet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.List;
@Entity
public class Cadre_Admin extends  User{
@JsonIgnore
    @ManyToMany(mappedBy = "controll_absence")
    List<Salle> listSalle;    public Cadre_Admin() {
    }

    public Cadre_Admin(String username, String lastname, String email, String password, Type type) {
        super(username, lastname, email, password, type);
    }

    public List<Salle> getListSalle() {
        return listSalle;
    }
    @Override
    public Type getType() {
        return this.type;
    }

    public void setListSalle(List<Salle> listSalle) {
        this.listSalle = listSalle;
    }
}
