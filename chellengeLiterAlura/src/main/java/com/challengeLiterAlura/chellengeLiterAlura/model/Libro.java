package com.challengeLiterAlura.chellengeLiterAlura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "Libro")

public class Libro {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrementable
    @Column(unique = true)
    private Long Id;
    private  String  titulo;
    private Integer numeroDescargas;

    private String idioma;

    //un libro tiene muchos autores , bueno puede :
    @OneToMany(mappedBy = "libro",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Autor> autores ;

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Libro(){}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Libro {\n")
                .append("  Título: '").append(titulo).append("',\n")
                .append("  Número de Descargas: ").append(numeroDescargas).append(",\n")
                .append("  Idioma: '").append(idioma).append("'\n")

                .append('}');


        return sb.toString();
    }

    public Libro(DatosLibro datosLibro){

       this.titulo = datosLibro.titulo();
       this.numeroDescargas = datosLibro.numeroDescargas();
        this.idioma = datosLibro.idioma().get(0);
//convertir de datos autores a autores
       this.autores =  datosLibro.autores().stream()
               .map(a -> new Autor(a.nombreAutor(),a.anioNacimiento(),a.anioMuerte()))
               .collect(Collectors.toList());

       setAutores(this.autores);
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        autores.forEach(a->a.setLibro(this));
        this.autores = autores;
    }
}
