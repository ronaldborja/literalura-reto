package com.proyectoalura.literalura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreAutor;

    @Column(nullable = false)
    private String añoNacimiento;

    @Column(nullable = false)
    private String añoFallecimiento;

    //Relación uno a muchos

    //mapped_by -> La propiedad autor de Libro es la responsable de la relación
    //cascade ->
    //fetchType ->

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    //Constructores
    public Autor() {}

    public Autor(String nombre, String fechaNacimiento, String fechaFallecimiento) {
        this.nombreAutor = nombre;
        this.añoNacimiento = fechaNacimiento;
        this.añoFallecimiento = fechaFallecimiento;

    }

    public Autor(DatosAutor datosAutor) {
        this.nombreAutor = datosAutor.nombre();
        this.añoNacimiento = datosAutor.fechaDeNacimiento();
        this.añoFallecimiento = datosAutor.fechaDeFallecimiento();
    }


    //Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getAñoNacimiento() {
        return añoNacimiento;
    }

    public void setAñoNacimiento(String añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

    public String getAñoFallecimiento() {
        return añoFallecimiento;
    }

    public void setAñoFallecimiento(String añoFallecimiento) {
        this.añoFallecimiento = añoFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autor" +
                "id=" + id +
                ", nombreAutor='" + nombreAutor + '\'' +
                ", añoNacimiento='" + añoNacimiento + '\'' +
                ", añoFallecimiento='" + añoFallecimiento + '\'';
    }
}
