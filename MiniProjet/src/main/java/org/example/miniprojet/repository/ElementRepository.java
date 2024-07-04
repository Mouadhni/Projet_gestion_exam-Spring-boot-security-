package org.example.miniprojet.repository;

import org.example.miniprojet.model.Cadre_Admin;
import org.example.miniprojet.model.Element;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementRepository extends JpaRepository<Element,Integer> {
}
