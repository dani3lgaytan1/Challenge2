package com.challengeLiterAlura.chellengeLiterAlura.repository;

import com.challengeLiterAlura.chellengeLiterAlura.model.Autor;
import com.challengeLiterAlura.chellengeLiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByIdiomaContainsIgnoreCase(String idioma);

    //consulta para obtener los autores registrados en la base de datos:
    @Query( value = "SELECT * FROM autor ")
    List<Autor> autoresRegistrados();
}
