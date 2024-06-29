package com.proyectoalura.literalura.repository;

import com.proyectoalura.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a from Autor a WHERE a.añoNacimiento <= :fecha AND a.añoFallecimiento >= :fecha")
    List<Autor> buscarAutoresVivosPorAño(String fecha);


}
