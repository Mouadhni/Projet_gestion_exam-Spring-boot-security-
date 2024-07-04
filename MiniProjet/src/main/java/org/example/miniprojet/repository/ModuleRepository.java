package org.example.miniprojet.repository;

import org.example.miniprojet.model.Cadre_Admin;
import org.example.miniprojet.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module,Integer> {

}
