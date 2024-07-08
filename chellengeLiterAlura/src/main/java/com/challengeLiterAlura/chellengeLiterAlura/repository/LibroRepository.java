package com.challengeLiterAlura.chellengeLiterAlura.repository;

import com.challengeLiterAlura.chellengeLiterAlura.model.Autor;
import com.challengeLiterAlura.chellengeLiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {


    //buqueda de serie por nombre o titulo :

    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);

    //consulta para obtener libros por idioma
    List<Libro> findByIdiomaContainsIgnoreCase(String idioma);

    //consulta para obtener los autores registrados en la base de datos:
    @Query( value = "SELECT DISTINCT a FROM  Libro l JOIN l.autores a ")
    List<Autor> autoresRegistrados();

    //consulta para obtener los autores mediante fechas:
    @Query( value = "SELECT a FROM  Libro l JOIN l.autores a WHERE  a.anioMuerte >= :fecha")
    List<Autor> autoresVivosAnio(int fecha);

}
