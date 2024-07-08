package com.challengeLiterAlura.chellengeLiterAlura.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Autor")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrementable
    private Long Id;
    private String nombre;
    private Integer anioNacimiento;
    private  Integer anioMuerte;


    //un episodio tiene yna serie:

    @ManyToOne
    private Libro libro;

    public Autor(){}

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Autor {\n")
                .append("  Nombre: '").append(nombre).append("',\n")
                .append("  Año de Nacimiento: ").append(anioNacimiento != null ? anioNacimiento : "N/A").append(",\n")
                .append("  Año de Muerte: ").append(anioMuerte != null ? anioMuerte : "N/A").append("\n")
                .append("}");
        return sb.toString();
    }

    public  Autor(String nombre, Integer anoNacimiento, Integer anoMuerte){

        this.nombre = nombre;
        this.anioNacimiento = anoNacimiento;
        this.anioMuerte = anoMuerte;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioMuerte() {
        return anioMuerte;
    }

    public void setAnioMuerte(Integer anioMuerte) {
        this.anioMuerte = anioMuerte;
    }
}

